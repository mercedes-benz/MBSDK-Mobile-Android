package com.daimler.mbingresskit.persistence

import com.daimler.mbingresskit.common.ProfileFieldsData

interface ProfileFieldsCache {

    fun loadProfileFields(countryCode: String, locale: String): ProfileFieldsData?

    fun createOrUpdateProfileFields(countryCode: String, locale: String, data: ProfileFieldsData)

    fun clear()
}
