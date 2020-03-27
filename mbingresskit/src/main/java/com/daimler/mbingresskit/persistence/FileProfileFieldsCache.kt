package com.daimler.mbingresskit.persistence

import com.daimler.mbcommonkit.filestorage.FileStorage
import com.daimler.mbingresskit.common.ProfileFieldsData

internal class FileProfileFieldsCache(
    private val storage: FileStorage<ProfileFieldsData, ProfileFieldsData>
) : ProfileFieldsCache {

    override fun loadProfileFields(countryCode: String, locale: String): ProfileFieldsData? {
        return storage.readFromFile(fileIdentifier(countryCode, locale))
    }

    override fun createOrUpdateProfileFields(countryCode: String, locale: String, data: ProfileFieldsData) {
        storage.writeToFile(data, fileIdentifier(countryCode, locale))
    }

    override fun clear() {
        storage.deleteFiles()
    }

    private fun fileIdentifier(countryCode: String, locale: String) =
        "${countryCode}_$locale"
}
