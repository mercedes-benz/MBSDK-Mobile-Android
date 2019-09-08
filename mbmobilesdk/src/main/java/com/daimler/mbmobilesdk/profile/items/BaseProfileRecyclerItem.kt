package com.daimler.mbmobilesdk.profile.items

import androidx.annotation.CallSuper
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileRecyclerViewable
import com.daimler.mbmobilesdk.profile.fields.ProfileValueCallback
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerItem

abstract class BaseProfileRecyclerItem : MBBaseRecyclerItem(), ProfileRecyclerViewable {

    protected var callback: ProfileValueCallback? = null
        private set

    protected var profileField: ProfileField? = null
        private set

    var tag: Any? = null

    final override fun applyCallback(callback: ProfileValueCallback) {
        this.callback = callback
    }

    final override fun item(): BaseProfileRecyclerItem? = this

    final override fun applyAssociatedField(profileField: ProfileField) {
        this.profileField = profileField
    }

    final override fun associatedField(): ProfileField? = profileField

    @CallSuper
    override fun destroy() {
        callback = null
        profileField = null
        tag = null
    }

    companion object {
        const val MANDATORY_CHAR = '*'
    }
}