package com.daimler.mbmobilesdk.vehicleassignment

import android.graphics.PointF
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf
import com.dlazaro66.qrcodereaderview.QRCodeReaderView

open class QRCodeReaderViewModel(val hintText: String?) : ViewModel() {

    val points = MutableLiveData<Array<PointF>>().apply { value = emptyArray() }

    val qrFoundEvent = MutableLiveEvent<String>()

    val progressVisible = MutableLiveData<Boolean>()
    val hintBoxVisible = mutableLiveDataOf(!hintText.isNullOrBlank())

    fun getOnQrCodeReadeListener(): QRCodeReaderView.OnQRCodeReadListener =
        QRCodeReaderView.OnQRCodeReadListener { text, points ->
            MBLoggerKit.d("text = $text")
            MBLoggerKit.d("points = $points")
            qrFoundEvent.sendEvent(text)
            this.points.postValue(points)
        }

    fun onScreenClicked() {
        hintBoxVisible.postValue(false)
    }

    fun reset() {
        points.value = emptyArray()
    }
}