package com.daimler.mbmobilesdk.profile.format

internal interface ProfileFieldValueFormattable {

    fun getFormattedValue(formatter: ProfileFieldValueFormatter): String?
}