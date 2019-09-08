package com.daimler.mbmobilesdk.profile.format

internal interface ProfileFieldValueReceiver {

    fun getSalutationForKey(key: String?): String?

    fun getTitleForKey(key: String?): String?
}