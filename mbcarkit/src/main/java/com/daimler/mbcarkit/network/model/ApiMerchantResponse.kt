package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.merchants.Address
import com.daimler.mbcarkit.business.model.merchants.Communication
import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.daimler.mbcarkit.business.model.merchants.Merchant
import com.daimler.mbcarkit.business.model.merchants.Merchants
import com.daimler.mbcarkit.business.model.merchants.OpeningHours
import com.daimler.mbcarkit.business.model.merchants.Region
import com.daimler.mbcarkit.business.model.merchants.SpokenLanguage
import com.google.gson.annotations.SerializedName

internal data class ApiMerchantResponse(
    @SerializedName("id") val id: String,
    @SerializedName("legalName") val legalName: String?,
    @SerializedName("address") val address: ApiAddress?,
    @SerializedName("region") val region: ApiRegion?,
    @SerializedName("geoCoordinates") val geoCoordinates: ApiGeoCoordinates?,
    @SerializedName("spokenLanguages") val spokenLanguages: List<ApiSpokenLanguage>?,
    @SerializedName("communication") val communication: ApiCommunication?,
    @SerializedName("openingHours") val openingHours: ApiOpeningHours?

)

internal fun ApiMerchantResponse.toMerchant() =
    Merchant(
        id,
        legalName,
        Address(
            address?.street,
            address?.addressAddition,
            address?.zipCode,
            address?.city,
            address?.district,
            address?.countryIsoCode
        ),
        Region(
            region?.region,
            region?.subRegion
        ),
        GeoCoordinates(
            geoCoordinates?.latitude,
            geoCoordinates?.longitude
        ),
        spokenLanguages?.map { SpokenLanguage(it.languageIsoCode) },
        Communication(
            communication?.fax,
            communication?.email,
            communication?.website,
            communication?.facebook,
            communication?.mobile,
            communication?.googlePlus,
            communication?.twitter,
            communication?.phone
        ),
        OpeningHours(
            openingHours?.monday.toDay(),
            openingHours?.tuesday.toDay(),
            openingHours?.wednesday.toDay(),
            openingHours?.thursday.toDay(),
            openingHours?.friday.toDay(),
            openingHours?.saturday.toDay(),
            openingHours?.sunday.toDay()
        )
    )

internal fun List<ApiMerchantResponse>.toMerchants() =
    Merchants(map { it.toMerchant() })
