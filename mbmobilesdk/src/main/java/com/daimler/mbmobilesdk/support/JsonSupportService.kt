package com.daimler.mbmobilesdk.support

import android.content.Context
import com.daimler.mbmobilesdk.assets.MBMobileSDKAssetManager
import com.daimler.mbmobilesdk.assets.AssetSubType

internal object JsonMBSupportKit {

    private const val SUPPORT_JSON = "support.json"

    fun loadSupportForLocale(context: Context, locale: String): SupportModel? {
        val support: SupportLocationsModel? = MBMobileSDKAssetManager.createAssetService(context, AssetSubType.SUPPORT)
            .parseFromJson(SUPPORT_JSON, SupportLocationsModel::class.java)

        return support?.let { supportLocationsModel ->
            val supportForLocation = supportLocationsModel.supportLocations.find { it.locale == locale }
            supportForLocation?.let {
                it
            } ?: createFallbackSupportModel(locale, supportLocationsModel)
        }
    }

    private fun createFallbackSupportModel(locale: String, support: SupportLocationsModel) =
        SupportModel(
            locale = locale,
            betaNumber = null,
            fallbackNumber = support.fallbackNumber,
            preferredNumber = null,
            email = support.fallbackMail
        )
}