package com.daimler.mbcarkit.business

interface PinProvider {

    fun requestPin(pinRequest: PinRequest)
}
