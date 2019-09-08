package com.daimler.mbmobilesdk.jumio.identityScanner

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.daimler.mbuikit.components.viewmodels.MBBaseToolbarViewModel
import com.jumio.nv.custom.NetverifyCustomScanPresenter

class IdentityScannerViewModel(app: Application) : MBBaseToolbarViewModel(app) {
    var presenter: NetverifyCustomScanPresenter? = null
        set(value) {
            field = value
            hintText.value = value?.helpText
        }

    val scanState = MutableLiveData(true)
    val uploading = MutableLiveData(false)
    val uploaded = MutableLiveData(false)
    val hintTitleText = MutableLiveData("")
    val stepText = MutableLiveData("")
    val hintText = MutableLiveData("")
    val title = MutableLiveData("")

    fun clickRetry() {
        scanState.value = true
        presenter?.retryScan()
    }
}