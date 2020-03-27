package com.daimler.mbmobilesdk.example.car

interface SendToCarStatusListener {
    fun onSuccess()
    fun onFailed(error: SendToCarError)
}
