package com.daimler.mbmobilesdk.notificationcenter.presentation.messagedetail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.notificationcenter.model.MessageDetail
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError

class MessageDetailsViewModel(app: Application) : AndroidViewModel(app) {

    val loading = MutableLiveData<Boolean>()

    private val messageResult = MutableLiveData<DetailsResult>()

    fun showDetails(messageKey: String) {
        loadMessageDetails(messageKey)
    }

    fun messageResult(): LiveData<DetailsResult> = messageResult

    private fun loadMessageDetails(messageKey: String) {
    }

    sealed class DetailsResult {
        class Success(val details: MessageDetail) : DetailsResult()
        class Error(val cause: ResponseError<out RequestError>?) : DetailsResult()
    }
}