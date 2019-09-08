package com.daimler.mbmobilesdk.profile.edit

data class ProfilePickerOptions(
    val title: String,
    val initialValue: String?,
    val values: HashMap<String, String>,
    val event: PickerEvent
)

enum class PickerEvent {
    LANGUAGE,
    ADDRESS_COUNTRY,
    ADDRESS_STATE,
    ADDRESS_PROVINCE,
    ADDRESS_CITY
}