package com.daimler.mbmobilesdk.profile

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.DatePicker
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.languageselection.LanguageChooserActivity
import com.daimler.mbmobilesdk.login.ProfileLogoutState
import com.daimler.mbmobilesdk.pin.UserPinActivity
import com.daimler.mbmobilesdk.profile.edit.ProfileDynamicEditActivity
import com.daimler.mbmobilesdk.profile.edit.ProfileEditMode
import com.daimler.mbmobilesdk.profile.fields.ProfileView
import com.daimler.mbmobilesdk.profile.layout.ProfileSectionsViewCreator
import com.daimler.mbmobilesdk.utils.createImageChooserIntent
import com.daimler.mbmobilesdk.utils.extensions.createSimpleDialogObserver
import com.daimler.mbmobilesdk.utils.extensions.simpleToastObserver
import com.daimler.mbmobilesdk.utils.extensions.toDate
import com.daimler.mbmobilesdk.utils.extensions.withPositiveAction
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.common.User
import com.daimler.mbingresskit.common.UserPinStatus
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.components.fragments.MBBaseMenuFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.utils.extensions.hasPermission
import kotlinx.android.synthetic.main.fragment_profile.*
import java.io.File
import java.net.URI
import java.util.*

internal class ProfileFragment : MBBaseMenuFragment<ProfileViewModel>(), DatePickerDialog.OnDateSetListener {

    private var callback: ProfileCallback? = null

    private var imageUri: String? = null

    override fun getLayoutRes(): Int = R.layout.fragment_profile

    override fun getModelId(): Int = BR.model

    override fun createViewModel(): ProfileViewModel =
        ViewModelProviders.of(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application))
            .get(ProfileViewModel::class.java)

    override fun getToolbarTitleRes(): Int = R.string.profile_title

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        viewModel.itemsCreatedEvent.observe(this, itemsCreated())
        viewModel.editEvent.observe(this, editEvent())
        viewModel.logoutRequestEvent.observe(this, logoutRequestEvent())
        viewModel.deleteAccountEvent.observe(this, deleteAccountEvent())
        viewModel.logoutStateEvent.observe(this, logoutStateChangedEvent())
        viewModel.imageEvent.observe(this, imageEvent())
        viewModel.userUpdateError.observe(this, userUpdateError())
        viewModel.userChangedEvent.observe(this, userChangedEvent())
        viewModel.userPictureChangedEvent.observe(this, userPictureChangedEvent())
        viewModel.authenticationError.observe(this, authenticationError())
        viewModel.errorEvent.observe(this, errorEvent())
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        callback = context as? ProfileCallback
        if (callback == null) {
            MBLoggerKit.w("Host does not implement ProfileCallback.")
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    private fun itemsCreated() = LiveEventObserver<List<ProfileView>> { items ->
        context?.let {
            val creator = ProfileSectionsViewCreator(it)
            val view = creator.createLayoutStructure(items)
            profile_content_root.removeAllViews()
            profile_content_root.addView(view)
        }
    }

    private fun editEvent() = LiveEventObserver<ProfileViewModel.UserEditEvent> { event ->
        when (event.id) {
            ProfileViewModel.ID_PIN -> editPin(event.user.pinStatus != UserPinStatus.SET)
            ProfileViewModel.ID_LANGUAGE_CODE -> editLanguageCode(event.user.languageCode)
            ProfileViewModel.ID_BIRTHDAY -> editBirthday(event.user.birthday)
            else -> editUserItem(event.id, event.user)
        }
    }

    private fun editUserItem(id: Int, user: User) {
        val mode =
            when (id) {
                ProfileViewModel.ID_NAME -> ProfileEditMode.NAME
                ProfileViewModel.ID_ADDRESS -> ProfileEditMode.ADDRESS
                ProfileViewModel.ID_PHONE -> ProfileEditMode.PHONE
                ProfileViewModel.ID_MAIL -> ProfileEditMode.MAIL
                ProfileViewModel.ID_TAX_NUMBER -> ProfileEditMode.TAX_NUMBER
                ProfileViewModel.ID_ADAPTION_VALUES -> ProfileEditMode.ADAPTION_VALUES
                ProfileViewModel.ID_COMMUNICATION -> ProfileEditMode.COMMUNICATION
                else -> null
            }
        mode?.let {
            startActivityForResult(ProfileDynamicEditActivity.getStartIntent(context!!, user, it),
                REQ_CODE_EDIT)
        } ?: MBLoggerKit.e("ID $id is not supported!")
    }

    private fun logoutRequestEvent() = LiveEventObserver<Unit> {
        activity?.let {
            MBDialogFragment.Builder(0).apply {
                withTitle(getString(R.string.profile_logout_title))
                withMessage(getString(R.string.profile_logout_message))
                withPositiveButtonText(getString(R.string.general_yes))
                withPositiveAction { viewModel.logout() }
                withNegativeButtonText(getString(R.string.general_no))
            }.build().show(it.supportFragmentManager, null)
        }
    }

    private fun deleteAccountEvent() = LiveEventObserver<Unit> {
        activity?.let {
            MBDialogFragment.Builder(0).apply {
                withTitle(getString(R.string.profile_delete_user))
                withMessage(getString(R.string.profile_delete_user_msg))
                withPositiveButtonText(getString(R.string.profile_delete_popup_confirm_delete))
                withPositiveAction { viewModel.logoutAndDeleteAccount() }
                withNegativeButtonText(getString(R.string.profile_delete_popup_cancel))
            }.build().show(it.supportFragmentManager, null)
        }
    }

    private fun logoutStateChangedEvent() = LiveEventObserver<ProfileLogoutState> {
        notifyLogoutStateChanged(it)
    }

    private fun imageEvent() = LiveEventObserver<Unit> {
        handleImagePermissions()
    }

    private fun handleImagePermissions() {
        val permissions = mutableListOf<String>()
        if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!hasPermission(Manifest.permission.CAMERA)) {
            permissions.add(Manifest.permission.CAMERA)
        }
        if (permissions.isEmpty()) {
            selectImage()
        } else {
            MBLoggerKit.d("Requesting permissions: $permissions.")
            requestPermissions(permissions.toTypedArray(), REQ_IMAGE_PERMISSION)
        }
    }

    private fun userUpdateError() = createSimpleDialogObserver()

    private fun userChangedEvent() = LiveEventObserver<User> {
        notifyUserChanged(it)
    }

    private fun userPictureChangedEvent() = LiveEventObserver<ByteArray> {
        notifyProfilePictureChanged(it)
    }

    private fun authenticationError() = createSimpleDialogObserver()

    private fun errorEvent() = simpleToastObserver()

    private fun selectImage() {
        val intent = createImageChooserIntent(activity!!)
        imageUri = intent.cameraUri
        startActivityForResult(intent.intent, REQ_CODE_IMAGE)
    }

    private fun editBirthday(currentBirthday: String) {
        context?.let {
            val date: Date? = currentBirthday.toDate(DATE_PATTERN)
            val calendar = Calendar.getInstance()

            date?.let {
                calendar.time = date
            } ?: calendar.add(Calendar.YEAR, -YEARS_OFFSET)

            DatePickerDialog(it,
                R.style.MBDatePickerDialogTheme,
                this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
    }

    override fun onDateSet(view: DatePicker?, yy: Int, mm: Int, dd: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(yy, mm, dd)
        viewModel.onDateSelected(calendar.timeInMillis)
    }

    private fun editPin(isInitialPin: Boolean) =
        startActivityForResult(UserPinActivity.getStartIntent(context!!, isInitialPin),
            REQ_CODE_PIN)

    // TODO swap to requested languages
    private fun editLanguageCode(code: String?) =
        startActivityForResult(LanguageChooserActivity.getStartIntent(context!!, code),
            REQ_CODE_LANGUAGE)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_CODE_IMAGE ->
                data?.data?.let { setImageFromGallery(it) }
                    ?: imageUri?.let { setImageFromCamera(it) }
            REQ_CODE_EDIT -> {
                if (resultCode == Activity.RESULT_OK) {
                    val user: User? = data?.getParcelableExtra(ProfileDynamicEditActivity.ARG_UPDATED_USER)
                    user?.let { viewModel.adjustUser(it) }
                }
            }
            REQ_CODE_PIN -> if (resultCode == Activity.RESULT_OK) {
                viewModel.onPinSet()
            }
            REQ_CODE_LANGUAGE -> {
                val languageCode = data?.getStringExtra(LanguageChooserActivity.ARG_SELECTED_LANGUAGE_CODE)
                languageCode?.let { viewModel.onLanguageSelected(it) }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (REQ_IMAGE_PERMISSION == requestCode) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                selectImage()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun setImageFromGallery(uri: Uri) {
        setImage(uri)
    }

    private fun setImageFromCamera(uri: String) {
        val file = File(URI.create(uri))
        if (file.exists()) {
            setImage(Uri.parse(uri))
        }
    }

    private fun setImage(uri: Uri) {
        viewModel.applyProfileImageUrl(uri.toString())
    }

    private fun notifyUserChanged(user: User) {
        callback?.onUserChanged(user)
    }

    private fun notifyProfilePictureChanged(pictureBytes: ByteArray) {
        callback?.onProfilePictureUpdated(pictureBytes)
    }

    private fun notifyLogoutStateChanged(state: ProfileLogoutState) {
        callback?.onLogoutStateChanged(state)
    }

    companion object {
        private const val REQ_IMAGE_PERMISSION = 20
        private const val REQ_CODE_IMAGE = 21
        private const val REQ_CODE_EDIT = 22
        private const val REQ_CODE_PIN = 23
        private const val REQ_CODE_LANGUAGE = 24
        private const val YEARS_OFFSET = 30
        const val DATE_PATTERN = "yyyy-MM-dd"

        fun newInstance() = ProfileFragment()
    }
}