package com.daimler.mbmobilesdk.profile

import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileValueCallback
import com.daimler.mbmobilesdk.profile.fields.ProfileViewable

class TestViewable(val changeValue: String) : ProfileViewable {

    var callback: ProfileValueCallback? = null
    var field: ProfileField? = null

    fun changeValue() {
        callback?.onValueChanged(changeValue)
    }

    override fun applyCallback(callback: ProfileValueCallback) {
        this.callback = callback
    }

    override fun applyAssociatedField(profileField: ProfileField) {
        this.field = profileField
    }

    override fun associatedField(): ProfileField? = field

    override fun applyValue(value: String?) = Unit

    override fun currentValue(): String? = null

    companion object {

        fun create(obj: ProfileField) = TestViewable(obj::class.java.simpleName).apply {
            applyAssociatedField(obj)
        }
    }
}