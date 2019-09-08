package com.daimler.mbmobilesdk.profile.fields

interface ProfileFieldActionResolvable {

    fun addCallback(actionHandler: ProfileFieldActionHandler, viewable: ProfileViewable)
}