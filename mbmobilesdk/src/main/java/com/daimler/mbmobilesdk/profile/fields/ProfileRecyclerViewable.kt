package com.daimler.mbmobilesdk.profile.fields

import com.daimler.mbmobilesdk.profile.items.BaseProfileRecyclerItem

internal interface ProfileRecyclerViewable : ProfileViewable {

    fun item(): BaseProfileRecyclerItem?
    fun destroy()
}