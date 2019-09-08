package com.daimler.mbmobilesdk.profile.edit

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.UserLocale
import com.daimler.mbmobilesdk.picker.SearchAndPickActivity
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileRecyclerViewable
import com.daimler.mbmobilesdk.profile.layout.ProfileFieldRecyclerView
import com.daimler.mbmobilesdk.profile.layout.ProfileItemsLayoutCreator
import com.daimler.mbmobilesdk.profile.layout.sideBySide
import com.daimler.mbmobilesdk.profile.locale.UserLocaleChangeActivity
import com.daimler.mbmobilesdk.profile.searchAndPickResultReceived
import com.daimler.mbmobilesdk.utils.checkParameterNotNull
import com.daimler.mbmobilesdk.utils.extensions.simpleTextObserver
import com.daimler.mbmobilesdk.utils.extensions.withPositiveAction
import com.daimler.mbingresskit.common.User
import com.daimler.mbuikit.components.activities.MBBaseToolbarActivity
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.utils.extensions.hideKeyboard
import kotlinx.android.synthetic.main.activity_profile_dynamic_edit.*

internal class ProfileDynamicEditActivity : MBBaseToolbarActivity<ProfileDynamicEditViewModel>() {

    override val buttonMode = BUTTON_CLOSE

    override val toolbarTitle: String by lazy { getString(R.string.profile_edit_title) }

    override fun getContentLayoutRes(): Int = R.layout.activity_profile_dynamic_edit

    override fun getContentModelId(): Int = BR.model

    override fun createViewModel(): ProfileDynamicEditViewModel {
        val user: User? = intent.getParcelableExtra(ARG_USER)
        val editMode: ProfileEditMode? = intent.getSerializableExtra(ARG_EDIT_MODE) as? ProfileEditMode
        checkParameterNotNull("user", user)
        checkParameterNotNull("editMode", editMode)
        val factory = ProfileDynamicEditViewModelFactory(application, user!!, editMode!!)
        return ViewModelProviders.of(this, factory).get(ProfileDynamicEditViewModel::class.java)
    }

    override fun onContentBindingCreated(binding: ViewDataBinding) {
        super.onContentBindingCreated(binding)

        overridePendingTransition(R.anim.slide_up, R.anim.stay)

        viewModel.itemsCreatedEvent.observe(this, onItemsCreated())
        viewModel.updatedEvent.observe(this, onUpdateEvent())
        viewModel.errorEvent.observe(this, onErrorEvent())
        viewModel.inputErrorEvent.observe(this, onInputErrorEvent())
        viewModel.showPickerEvent.observe(this, onShowPicker())
        viewModel.showCountrySelectionEvent.observe(this, onShowCountrySelection())
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) overridePendingTransition(R.anim.stay, R.anim.slide_down)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_CODE_PICKER_LANGUAGE ->
                searchAndPickResultReceived(resultCode, data)?.let {
                    viewModel.languageCodeSet(it.first)
                }
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
            REQ_CODE_ADDRESS_COUNTRY ->
                if (resultCode == Activity.RESULT_OK) {
                    val user: User? = data?.getParcelableExtra(UserLocaleChangeActivity.ARG_UPDATED_USER)
                    user?.let { finishWithUser(it) }
                }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun onItemsCreated() = LiveEventObserver<List<ProfileRecyclerViewable>> {
        val layoutCreator = ProfileItemsLayoutCreator.Builder(this).apply {
            rule(sideBySide<ProfileField.Salutation, ProfileField.Title>())
        }.build()
        val recyclerView = layoutCreator.createLayoutStructure(it)
        recyclerView.isNestedScrollingEnabled = false
        items_root.addView(recyclerView)
    }

    private fun onUpdateEvent() = LiveEventObserver<User> {
        hideKeyboard()
        finishWithUser(it)
    }

    private fun onErrorEvent() = simpleTextObserver()

    private fun onInputErrorEvent() = simpleTextObserver()

    private fun onShowPicker() =
        LiveEventObserver<ProfilePickerOptions> {
            val reqCode = when (it.event) {
                PickerEvent.LANGUAGE -> REQ_CODE_PICKER_LANGUAGE
                PickerEvent.ADDRESS_COUNTRY -> REQ_CODE_ADDRESS_COUNTRY
                PickerEvent.ADDRESS_STATE -> REQ_CODE_PICKER_ADDRESS_STATE
                PickerEvent.ADDRESS_PROVINCE -> REQ_CODE_PICKER_ADDRESS_PROVINCE
                PickerEvent.ADDRESS_CITY -> REQ_CODE_PICKER_ADDRESS_CITY
            }
            startActivityForResult(
                SearchAndPickActivity.getStartIntent(
                    this,
                    it.title,
                    it.values,
                    it.initialValue
                ),
                reqCode
            )
        }

    private fun onShowCountrySelection() = LiveEventObserver<Pair<User, UserLocale>> {
        MBDialogFragment.Builder(0).apply {
            withTitle(getString(R.string.country_selection_dialog_title))
            withMessage(getString(R.string.country_selection_dialog_message))
            withPositiveButtonText(getString(R.string.general_ok))
            withPositiveAction {
                startActivityForResult(
                    UserLocaleChangeActivity.getStartIntent(this@ProfileDynamicEditActivity, it.first, it.second),
                    REQ_CODE_ADDRESS_COUNTRY
                )
            }
            withNegativeButtonText(getString(R.string.general_cancel))
        }.build().show(supportFragmentManager, null)
    }

    private fun finishWithUser(user: User) {
        val intent = Intent()
        intent.putExtra(ARG_UPDATED_USER, user)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

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

    companion object {
        private const val ARG_EDIT_MODE = "arg.profile.dynamic.edit.mode"
        private const val ARG_USER = "arg.profile.dynamic.edit.user"

        private const val REQ_CODE_PICKER_LANGUAGE = 21
        private const val REQ_CODE_ADDRESS_COUNTRY = 22
        private const val REQ_CODE_PICKER_ADDRESS_STATE = 23
        private const val REQ_CODE_PICKER_ADDRESS_PROVINCE = 24
        private const val REQ_CODE_PICKER_ADDRESS_CITY = 25

        const val ARG_UPDATED_USER = "arg.profile.edit.user.updated"

        fun getStartIntent(context: Context, user: User, editMode: ProfileEditMode): Intent {
            val intent = Intent(context, ProfileDynamicEditActivity::class.java)
            intent.putExtra(ARG_USER, user)
            intent.putExtra(ARG_EDIT_MODE, editMode)
            return intent
        }
    }
}