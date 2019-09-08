package com.daimler.mbmobilesdk.profile.fields

import android.view.View

internal interface ProfileView : ProfileViewable {

    fun view(): View?
}