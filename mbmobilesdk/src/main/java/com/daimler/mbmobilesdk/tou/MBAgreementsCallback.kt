package com.daimler.mbmobilesdk.tou

interface MBAgreementsCallback {

    fun onSoeUpdated()

    fun onSoeCancelled()

    fun onNatconUpdated()

    fun onNatconCancelled()

    fun onUpdateAgreementsTitle(title: String)
}