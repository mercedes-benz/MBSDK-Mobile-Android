package com.daimler.mbmobilesdk.utils.extensions

import com.daimler.mbingresskit.common.ProfileSelectableValues

internal fun ProfileSelectableValues.getDescription(key: String?): String? {
    return if (matchSelectableValueByKey) selectableValues.find { it.key == key }?.description else key
}