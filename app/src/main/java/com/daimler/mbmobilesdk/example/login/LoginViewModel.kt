package com.daimler.mbmobilesdk.example.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daimler.mbmobilesdk.example.ingress.IngressKitRepository
import com.daimler.mbmobilesdk.example.utils.MutableLiveEvent
import com.daimler.mbmobilesdk.example.utils.MutableLiveUnitEvent
import com.daimler.mbmobilesdk.example.utils.stateful

class LoginViewModel(
    private val loginRepo: IngressKitRepository
) : ViewModel() {

    val progressVisible = MutableLiveData<Boolean>()

    val credentials = MutableLiveData<String>()
    val tanInputEnabled: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(credentials) {
            value = it.isNotBlank()
        }
    }

    val tan = MutableLiveData<String>()
    val loginVisible = MutableLiveData(false)
    val loginEnabled: LiveData<Boolean> = MediatorLiveData<Boolean>().apply {
        addSource(tan) {
            value = it.isNotBlank()
        }
    }

    val notRegisteredEvent = MutableLiveEvent<String>()
    val errorEvent = MutableLiveUnitEvent()
    val userLoggedInEvent = MutableLiveUnitEvent()

    fun onSendTanClicked() {
        loginRepo
            .sendTan(credentials.value.orEmpty())
            .stateful(progressVisible)
            .onComplete {
                if (!it.isRegistered) {
                    notRegisteredEvent.sendEvent(it.user)
                } else {
                    onSendTanSuccess()
                }
            }.onFailure {
                errorEvent.sendEvent()
            }
    }

    fun onLoginClicked() {
        loginRepo
            .login(tan.value.orEmpty())
            .stateful(progressVisible)
            .onComplete {
                userLoggedInEvent.sendEvent()
            }.onFailure {
                errorEvent.sendEvent()
            }
    }

    private fun onSendTanSuccess() {
        loginVisible.value = true
    }
}
