package com.daimler.mbmobilesdk.legal.json

import android.content.Context
import com.daimler.mbmobilesdk.assets.MBMobileSDKAssetManager
import com.daimler.mbmobilesdk.assets.AssetSubType
import com.daimler.mbmobilesdk.legal.license.Library
import com.daimler.mbmobilesdk.legal.license.License

internal class LicenseParser {

    fun parseLibraries(context: Context): List<Library> {
        val jsonLibraries = MBMobileSDKAssetManager.createAssetService(
            context, AssetSubType.LICENSES
        ).parseFromJson(APP_FAMILY_LICENSES_JSON, JsonLibraries::class.java)
        return mapJsonLibrariesToLibraries(jsonLibraries)
    }

    private fun mapJsonLibrariesToLibraries(jsonLibs: JsonLibraries) =
        jsonLibs.libraries?.map { mapJsonLibraryToLibrary(it) } ?: emptyList()

    private fun mapJsonLibraryToLibrary(jsonLibrary: JsonLibrary) =
        Library(
            jsonLibrary.name.orEmpty(),
            jsonLibrary.version.orEmpty(),
            jsonLibrary.dependency.orEmpty(),
            jsonLibrary.fileUrl.orEmpty(),
            jsonLibrary.file.orEmpty(),
            mapJsonLicensesToLicenses(jsonLibrary.licenses)
        )

    private fun mapJsonLicensesToLicenses(jsonLicenses: List<JsonLicense>?) =
        jsonLicenses?.map { mapJsonLicenseToLicense(it) } ?: emptyList()

    private fun mapJsonLicenseToLicense(jsonLicense: JsonLicense) =
        License(jsonLicense.name.orEmpty(), jsonLicense.url.orEmpty())

    private companion object {
        private const val APP_FAMILY_LICENSES_JSON = "appfamily_sdk_licenses.json"
    }
}