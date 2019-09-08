package com.daimler.mbmobilesdk.profile.fields

import android.view.View

interface ProfileViewableView : ProfileViewable {

    fun view(): View?
}