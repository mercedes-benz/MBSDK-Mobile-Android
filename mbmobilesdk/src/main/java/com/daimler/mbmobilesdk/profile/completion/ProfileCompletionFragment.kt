package com.daimler.mbmobilesdk.profile.completion

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.picker.SearchAndPickActivity
import com.daimler.mbmobilesdk.profile.edit.PickerEvent
import com.daimler.mbmobilesdk.profile.edit.ProfilePickerOptions
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileRecyclerViewable
import com.daimler.mbmobilesdk.profile.layout.ProfileFieldRecyclerView
import com.daimler.mbmobilesdk.profile.layout.ProfileItemsLayoutCreator
import com.daimler.mbmobilesdk.profile.layout.forcePosition
import com.daimler.mbmobilesdk.profile.layout.sideBySide
import com.daimler.mbmobilesdk.profile.searchAndPickResultReceived
import com.daimler.mbmobilesdk.utils.UserValuePolicy
import com.daimler.mbmobilesdk.utils.checkParameterNotNull
import com.daimler.mbmobilesdk.utils.extensions.application
import com.daimler.mbmobilesdk.utils.extensions.simpleTextObserver
import com.daimler.mbmobilesdk.utils.extensions.toCalendar
import com.daimler.mbmobilesdk.utils.ifNotNull
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.common.User
import com.daimler.mbuikit.components.fragments.MBBaseViewModelFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import kotlinx.android.synthetic.main.fragment_profile_completion.*
import java.util.*

internal class ProfileCompletionFragment : MBBaseViewModelFragment<ProfileCompletionViewModel>() {

    private var callback: ProfileCompletionCallback? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? ProfileCompletionCallback
        if (callback == null) {
            MBLoggerKit.w("Host does not implement ProfileCompletionCallback!")
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    override fun createViewModel(): ProfileCompletionViewModel {
        val user: User? = arguments?.getParcelable(EXTRA_USER)
        checkParameterNotNull("user", user)
        val factory = ProfileCompletionViewModelFactory(application, user!!)
        return ViewModelProviders.of(this, factory).get(ProfileCompletionViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.fragment_profile_completion

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        notifyUpdateTitle(getString(R.string.profile_edit_title))

        viewModel.apply {
            viewablesCreatedEvent.observe(this@ProfileCompletionFragment, onViewablesCreated())
            userUpdatedEvent.observe(this@ProfileCompletionFragment, onUserUpdated())
            birthdayPickerEvent.observe(this@ProfileCompletionFragment, onShowBirthdayPicker())
            showPickerEvent.observe(this@ProfileCompletionFragment, onShowPicker())
            inputErrorEvent.observe(this@ProfileCompletionFragment, onInputErrorEvent())
            generalErrorEvent.observe(this@ProfileCompletionFragment, onGeneralErrorEvent())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_CODE_PICKER_ADDRESS_STATE ->
                searchAndPickResultReceived(resultCode, data)?.let {
                    viewModel.addressStateSet(it.first, it.second)
                    adjustProfileItem<ProfileField.AddressState>(it.second)
                }
            REQ_CODE_PICKER_ADDRESS_PROVINCE ->
                searchAndPickResultReceived(resultCode, data)?.let {
                    viewModel.addressProvinceSet(it.first, it.second)
                    adjustProfileItem<ProfileField.AddressProvince>(it.second)
                }
            REQ_CODE_PICKER_ADDRESS_CITY -> {
                searchAndPickResultReceived(resultCode, data)?.let {
                    viewModel.addressCitySet(it.first, it.second)
                    adjustProfileItem<ProfileField.AddressCity>(it.second)
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun onViewablesCreated() = LiveEventObserver<List<ProfileRecyclerViewable>> { items ->
        context?.let {
            val layoutCreator = ProfileItemsLayoutCreator.Builder(it).apply {
                rule(sideBySide<ProfileField.Salutation, ProfileField.Title>())
                rule(forcePosition<ProfileField.AddressCountryCode>(0))
                rule(forcePosition<ProfileField.LanguageCode>(1))
            }.build()
            val recyclerView = layoutCreator.createLayoutStructure(items)
            recyclerView.isNestedScrollingEnabled = false
            items_root.addView(recyclerView)
        }
    }

    private fun onUserUpdated() = LiveEventObserver<User> {
        notifyUserUpdated(it)
    }

    private fun onShowBirthdayPicker() = LiveEventObserver<Long> {
        context?.let { ctx ->
            val calendar = Date(it).toCalendar()

            val maxDate = Calendar.getInstance().apply {
                add(Calendar.YEAR, -UserValuePolicy.MIN_AGE)
            }.timeInMillis

            DatePickerDialog(ctx,
                R.style.MBDatePickerDialogTheme,
                DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    viewModel.adjustBirthday(calendar.timeInMillis)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).apply {
                datePicker.maxDate = maxDate
            }.show()
        }
    }

    private fun onShowPicker() =
        LiveEventObserver<ProfilePickerOptions> { options ->
            val reqCode = when (options.event) {
                PickerEvent.LANGUAGE -> null
                PickerEvent.ADDRESS_COUNTRY -> null
                PickerEvent.ADDRESS_STATE -> REQ_CODE_PICKER_ADDRESS_STATE
                PickerEvent.ADDRESS_PROVINCE -> REQ_CODE_PICKER_ADDRESS_PROVINCE
                PickerEvent.ADDRESS_CITY -> REQ_CODE_PICKER_ADDRESS_CITY
            }
            ifNotNull(context, reqCode) { ctx, code ->
                startActivityForResult(
                    SearchAndPickActivity.getStartIntent(
                        ctx,
                        options.title,
                        options.values,
                        options.initialValue
                    ),
                    code
                )
            }
        }

    private fun onInputErrorEvent() = simpleTextObserver()

    private fun onGeneralErrorEvent() = simpleTextObserver()

    private fun getProfileView(): ProfileFieldRecyclerView? {
        if (items_root.childCount != 0) {
            val child = items_root.getChildAt(0)
            if (child is ProfileFieldRecyclerView) {
                return child
            }
        }
        return null
    }

    private inline fun <reified T : ProfileField> adjustProfileItem(value: String?) {
        getProfileView()?.adapter?.adjustItem<T>(value)
    }

    private fun notifyUserUpdated(user: User) {
        callback?.onUserUpdated(user)
    }

    private fun notifyUpdateTitle(title: String) {
        callback?.onUpdateProfileTitle(title)
    }

    companion object {

        private const val EXTRA_USER = "extra.profile.completion.user"

        private const val REQ_CODE_PICKER_ADDRESS_STATE = 33
        private const val REQ_CODE_PICKER_ADDRESS_PROVINCE = 34
        private const val REQ_CODE_PICKER_ADDRESS_CITY = 35

        fun newInstance(user: User): ProfileCompletionFragment {
            val fragment = ProfileCompletionFragment()
            val args = Bundle().apply {
                putParcelable(EXTRA_USER, user)
            }
            fragment.arguments = args
            return fragment
        }
    }
}