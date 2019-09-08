package com.daimler.mbmobilesdk.registration.locale

import com.daimler.mbmobilesdk.views.ModelNumberPicker

data class LocaleModel(
    val name: String,
    val code: String,
    val type: LocaleType
) : ModelNumberPicker.Pickable {

    override fun displayValue(): String = name
}