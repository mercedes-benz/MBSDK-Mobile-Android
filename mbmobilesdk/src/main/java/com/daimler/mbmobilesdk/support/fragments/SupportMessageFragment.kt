package com.daimler.mbmobilesdk.support.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.MediaStore
import androidx.core.app.ShareCompat
import androidx.core.content.FileProvider
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.BuildConfig
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.dialogs.DateTimePickerDialogFragment
import com.daimler.mbmobilesdk.dialogs.SupportAdditionalDataDialog
import com.daimler.mbmobilesdk.dialogs.RadioDialogFragment
import com.daimler.mbmobilesdk.support.viewmodels.SupportMessageViewModel
import com.daimler.mbmobilesdk.utils.extensions.hideKeyboard
import com.daimler.mbmobilesdk.utils.extensions.showKeyboard
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbuikit.components.fragments.MBBaseViewModelFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.utils.extensions.hasPermission
import kotlinx.android.synthetic.main.fragment_support_message.*
import java.io.File
import java.net.URLDecoder
import java.text.SimpleDateFormat
import java.util.*

class SupportMessageFragment : MBBaseViewModelFragment<SupportMessageViewModel>(),
        DateTimePickerDialogFragment.SelectDateCallback,
        RadioDialogFragment.SelectCallback {

    private var mPhotoFile: File? = null

    override fun getLayoutRes(): Int = R.layout.fragment_support_message

    override fun getModelId(): Int = BR.model

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initializeGridViews()

        mbet_support_edit_question.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) v.showKeyboard()
        }
    }

    override fun createViewModel(): SupportMessageViewModel =
            ViewModelProviders.of(this,
                    ViewModelProvider.AndroidViewModelFactory.getInstance(activity!!.application))
                    .get(SupportMessageViewModel::class.java)

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        viewModel.vehicleOptionList.observe(this, androidx.lifecycle.Observer<Array<CharSequence>> { list -> updateCarSelection(list) })
        viewModel.clickEvent.observe(this, clickEvent())
    }

    private fun clickEvent() = LiveEventObserver<Int> {
        when (it) {
            SupportMessageViewModel.HIDE_KEYBOARD -> hideKeyboard()
            SupportMessageViewModel.DATA_DIALOG -> openDataDialog()
            SupportMessageViewModel.DATE_SELECTION -> openDateTimeDialog()
            SupportMessageViewModel.SHARE_ANSWER -> openShareChooser()
            SupportMessageViewModel.REQ_PERMISSION -> handleImagePermissions()
        }
    }

    private fun hideKeyboard() = mbet_support_edit_question.hideKeyboard()

    private fun openDateTimeDialog() {
        val date = viewModel.cacDate ?: Date()
        val dialog = DateTimePickerDialogFragment.newInstance(this, date,
                mbsupportkit_TIMEPICKER_DIALOG,
                getString(R.string.rssm_message_details_timestamp_picker_title),
                null,
                resources.getString(R.string.general_ok),
                resources.getString(R.string.general_cancel))
        dialog.show(fragmentManager, "selectDateTimeDialog")
    }

    private fun openDataDialog() {
        SupportAdditionalDataDialog(getString(R.string.rssm_message_data_hint_content)).show(fragmentManager, null)
    }

    private fun openShareChooser() {
        ShareCompat.IntentBuilder
                .from(activity)
                .setText(viewModel.botAnswer.value)
                .setType("text/plain")
                .setChooserTitle(getString(R.string.rssm_message_chat_bot_share))
                .startChooser()
    }

    private fun updateCarSelection(carList: Array<CharSequence>) {
        mbcl_support_car_select.setOnClickListener {
            if (carList.size >= 2) {
                val carSelectionDialog = RadioDialogFragment.newInstance(this,
                        carList,
                        viewModel.selectedCarChoice,
                        mbsupportkit_CAR_SELECTION_DIALOG,
                        getString(R.string.rssm_message_details_car_selection_title),
                        null,
                        getString(R.string.general_ok),
                        getString(R.string.general_cancel))
                carSelectionDialog.show(fragmentManager!!, "CarSelectionDialog")
            }
        }
    }

    // OptionsDialog
    override fun onSelectItem(dialogId: Int, choice: Int) {
        when (dialogId) {
            mbsupportkit_CAR_SELECTION_DIALOG -> viewModel.onCarSelection(choice)
            mbsupportkit_IMAGE_SELECTION_DIALOG -> onGetImageSelected(choice)
            else -> { } // Do nothing
        }
    }

    override fun onCancelDialog(dialogId: Int) {
        when (dialogId) {
            mbsupportkit_CAR_SELECTION_DIALOG -> viewModel.onCarSelection(null)
            else -> { } // Do nothing
        }
    }

    private fun onGetImageSelected(choice: Int) {
        when (choice) {
            MB_CAMERA_SELECTED -> onCameraChoice()
            MB_GALLERY_SELECTED -> onGalleryChoice()
        }
    }

    // Date selection
    override fun onPickDateTime(selection: Date) = viewModel.onDateSelected(selection)

    override fun onCancelSelectDateDialog() = viewModel.onDateSelected(null)

    private fun initializeGridViews() {
        mbrv_overview_images.layoutManager = GridLayoutManager(context, 3)

        mbrv_image_selection.layoutManager = GridLayoutManager(context, 3)
    }

    private fun onCameraChoice() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val tmpContext = context ?: return
        val tmpPhotoFile = getOutputMediaFile(tmpContext)
        mPhotoFile = tmpPhotoFile
        val photoURI: Uri? = FileProvider.getUriForFile(
                tmpContext, "${tmpContext.applicationContext.packageName}.${BuildConfig.IMAGE_PROVIDER_AUTHORITY_SUFFIX}", tmpPhotoFile
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
        startActivityForResult(intent, mbsupportkit_REQUEST_IMAGE_CAPTURE)
    }

    private fun onGalleryChoice() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "!Select Picture"),
                mbsupportkit_REQUEST_PICK_IMAGE)
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
            showImageSelectionDialog()
        } else {
            MBLoggerKit.d("Requesting permissions: $permissions.")
            requestPermissions(permissions.toTypedArray(), MB_REQUEST_PERMISSIONS)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val tmpContext = context ?: return
        when (requestCode) {
            mbsupportkit_REQUEST_IMAGE_CAPTURE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val extras = data?.extras
                    var imageBitmap = extras?.get("data") as Bitmap?
                    if (imageBitmap == null) imageBitmap = MediaStore.Images.Media
                            .getBitmap(tmpContext.contentResolver, Uri.fromFile(mPhotoFile))
                    viewModel.addImage(imageBitmap!!, imageBitmap)
                }
            }
            mbsupportkit_REQUEST_PICK_IMAGE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val imageUri = data?.data
                    imageUri?.let {
                        val thumb = MediaStore.Images.Thumbnails.getThumbnail(
                                tmpContext.contentResolver,
                                getImageId(imageUri),
                                MediaStore.Images.Thumbnails.MICRO_KIND,
                                null
                        )
                        viewModel.addImage(thumb
                                ?: BitmapFactory.decodeResource(resources, R.drawable.ic_image_attachment),
                                MediaStore.Images.Media.getBitmap(tmpContext.contentResolver, imageUri))
                    }
                }
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (MB_REQUEST_PERMISSIONS == requestCode) {
            if (grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                showImageSelectionDialog()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    private fun showImageSelectionDialog() {
        val selectionList: Array<CharSequence> = arrayOf(resources.getString(R.string.rssm_message_details_image_selection_photo), resources.getString(R.string.rssm_message_details_image_selection_library))
        val dialog = RadioDialogFragment.newInstance(this, selectionList,
                0,
                mbsupportkit_IMAGE_SELECTION_DIALOG,
                getString(R.string.rssm_message_details_image_selection_title),
                null,
                resources.getString(R.string.general_ok),
                resources.getString(R.string.general_cancel))
        dialog.show(fragmentManager!!, "selectImageSource")
    }

    private fun getImageId(imageUri: Uri): Long {
        // this needs to be decoded, ':' is occasionally encoded as "%3A"
        val uriPath = imageUri.pathSegments.map { URLDecoder.decode(it, "UTF-8") }
        return try {
            // last part of the last element is the image id.
            uriPath[uriPath.size - 1].split(":").last().toLong()
        } catch (e: Exception) {
            MBLoggerKit.e("Error getting image Id", null, e)
            0
        }
    }

    // Get Uri Of captured Image
    private fun getOutputMediaFile(context: Context): File {
        val mediaStorageDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Camera")
        // If File is not present create directory
        if (!mediaStorageDir.exists()) {
            if (mediaStorageDir.mkdir())
                MBLoggerKit.e("Create Directory", "Main Directory Created : $mediaStorageDir")
        }

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return File(mediaStorageDir.path + File.separator + "IMG_" + timeStamp + ".jpg")
    }

    companion object {
        const val mbsupportkit_IMAGE_SELECTION_DIALOG = 23452
        const val mbsupportkit_MESSAGE_EXTRA_INFO_DIALOG = 23454
        const val mbsupportkit_TIMEPICKER_DIALOG = 24453
        const val mbsupportkit_CAR_SELECTION_DIALOG = 23433

        const val MB_REQUEST_PERMISSIONS = 7
        const val mbsupportkit_REQUEST_IMAGE_CAPTURE = 5698
        const val mbsupportkit_REQUEST_PICK_IMAGE = 5699

        const val MB_CAMERA_SELECTED = 0
        const val MB_GALLERY_SELECTED = 1

        fun newInstance() = SupportMessageFragment()
    }
}
