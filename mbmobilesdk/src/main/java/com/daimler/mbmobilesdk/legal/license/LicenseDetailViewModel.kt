package com.daimler.mbmobilesdk.legal.license

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.assets.MBMobileSDKAssetManager
import com.daimler.mbmobilesdk.assets.AssetSubType
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent

class LicenseDetailViewModel(
    app: Application,
    val title: String,
    fileName: String
) : AndroidViewModel(app) {

    val text = MutableLiveData<String>()

    val onCloseEvent = MutableLiveUnitEvent()

    init {
        loadTextFromAssets(fileName)
    }

    fun onCloseClicked() {
        onCloseEvent.sendEvent()
    }

    private fun loadTextFromAssets(fileName: String) {
        val text =
            MBMobileSDKAssetManager.createAssetService(getApplication(), AssetSubType.LICENSES)
                .read(fileName)
        this.text.postValue(text)
    }
}