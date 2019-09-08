package com.daimler.mbmobilesdk.vehicleassignment

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import kotlinx.android.synthetic.main.activity_qr_code_reader.*

open class QRCodeReaderActivity<T : QRCodeReaderViewModel> : MBBaseViewModelActivity<T>() {

    private var pendingQrConfirmation = false
    private var enteringFinished = false
    private val handler = Handler(Looper.getMainLooper())

    @Suppress("UNCHECKED_CAST")
    override fun createViewModel(): T =
        ViewModelProviders.of(this).get(QRCodeReaderViewModel::class.java) as T

    override fun getLayoutRes(): Int = R.layout.activity_qr_code_reader

    override fun getModelId(): Int = BR.model

    override fun onEnterAnimationComplete() {
        super.onEnterAnimationComplete()
        enteringFinished()
    }

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        qr_reader.setAutofocusInterval(AUTO_FOCUS_INTERVAL_MILLIS)
        qr_reader.setBackCamera()
        viewModel.qrFoundEvent.observe(this, qrFoundEvent())
    }

    override fun onResume() {
        super.onResume()
        if (!pendingQrConfirmation && enteringFinished) {
            handler.postDelayed({ startCamera() }, CAMERA_DELAY_MILLIS)
        }
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacksAndMessages(null)
        if (!pendingQrConfirmation && enteringFinished) stopCamera()
    }

    private fun startCamera() = qr_reader.startCamera()

    private fun stopCamera() = qr_reader.stopCamera()

    private fun qrFoundEvent() = LiveEventObserver<String> {
        stopCamera()
        onQrCodeFound(it)
    }

    protected open fun onQrCodeFound(qr: String) {
        pendingQrConfirmation = true
        MBDialogFragment.Builder(0).apply {
            withMessage(String.format(getString(R.string.assign_qr_found), qr))
            withPositiveButtonText(getString(R.string.general_okay))
            withNegativeButtonText(getString(R.string.general_cancel))
            withListener(object : MBDialogFragment.DialogListener {
                override fun onNegativeAction(id: Int) {
                    qrCodeDenied()
                }

                override fun onPositiveAction(id: Int) {
                    qrCodeConfirmed(qr)
                }
            })
        }.build().show(supportFragmentManager, null)
    }

    protected fun resetCamera() {
        viewModel.reset()
        startCamera()
    }

    private fun qrCodeConfirmed(qr: String) {
        val intent = Intent()
        intent.putExtra(ARG_QR_CODE, qr)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun qrCodeDenied() {
        pendingQrConfirmation = false
        resetCamera()
    }

    private fun enteringFinished() {
        enteringFinished = true
        qr_reader.visibility = View.VISIBLE
    }

    companion object {
        internal const val ARG_QR_CODE = "arg.qr.code"

        private const val CAMERA_DELAY_MILLIS = 1000L
        private const val AUTO_FOCUS_INTERVAL_MILLIS = 2000L
    }
}