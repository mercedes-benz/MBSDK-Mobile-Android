package com.daimler.mbmobilesdk.profile.fields

interface ProfileViewable {

    fun applyCallback(callback: ProfileValueCallback)
    fun applyAssociatedField(profileField: ProfileField)
    fun associatedField(): ProfileField?
    fun applyValue(value: String?)
    fun currentValue(): String?
}