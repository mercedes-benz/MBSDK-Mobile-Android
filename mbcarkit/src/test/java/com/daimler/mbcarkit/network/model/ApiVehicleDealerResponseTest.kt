package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiVehicleDealerResponseTest {

    @Test
    fun `map ApiVehicleDealerResponse to VehicleDealer`(softly: SoftAssertions) {
        val apiVehicleDealer = ApiVehicleDealerResponse(
            "id",
            ApiDealerRole.SALES,
            null,
            ApiMerchantResponse(
                "id",
                "legalName",
                ApiAddress(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                ),
                ApiRegion(
                    null,
                    null
                ),
                ApiGeoCoordinates(
                    null,
                    null
                ),
                null,
                ApiCommunication(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                ),
                ApiOpeningHours(
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                )
            )
        )
        val vehicleDealer = apiVehicleDealer.toVehicleDealer()

        softly.assertThat(vehicleDealer.dealerId).isEqualTo(apiVehicleDealer.dealerId)
        softly.assertThat(vehicleDealer.role.name).isEqualTo(apiVehicleDealer.role.name)
        softly.assertThat(vehicleDealer.updatedAt).isEqualTo(apiVehicleDealer.updatedAt)
        softly.assertThat(vehicleDealer.merchant?.id).isEqualTo(apiVehicleDealer.dealerData?.id)
        softly.assertThat(vehicleDealer.merchant?.legalName).isEqualTo(apiVehicleDealer.dealerData?.legalName)
        softly.assertThat(vehicleDealer.merchant?.address?.street).isEqualTo(apiVehicleDealer.dealerData?.address?.street)
        softly.assertThat(vehicleDealer.merchant?.address?.addressAddition).isEqualTo(apiVehicleDealer.dealerData?.address?.addressAddition)
        softly.assertThat(vehicleDealer.merchant?.address?.zipCode).isEqualTo(apiVehicleDealer.dealerData?.address?.zipCode)
        softly.assertThat(vehicleDealer.merchant?.address?.city).isEqualTo(apiVehicleDealer.dealerData?.address?.city)
        softly.assertThat(vehicleDealer.merchant?.address?.district).isEqualTo(apiVehicleDealer.dealerData?.address?.district)
        softly.assertThat(vehicleDealer.merchant?.address?.countryIsoCode).isEqualTo(apiVehicleDealer.dealerData?.address?.countryIsoCode)
        softly.assertThat(vehicleDealer.merchant?.region?.region).isEqualTo(apiVehicleDealer.dealerData?.region?.region)
        softly.assertThat(vehicleDealer.merchant?.region?.subRegion).isEqualTo(apiVehicleDealer.dealerData?.region?.subRegion)
        softly.assertThat(vehicleDealer.merchant?.coordinates?.latitude).isEqualTo(apiVehicleDealer.dealerData?.geoCoordinates?.latitude)
        softly.assertThat(vehicleDealer.merchant?.coordinates?.longitude).isEqualTo(apiVehicleDealer.dealerData?.geoCoordinates?.longitude)
        softly.assertThat(vehicleDealer.merchant?.spokenLanguage).isEqualTo(apiVehicleDealer.dealerData?.spokenLanguages)
        softly.assertThat(vehicleDealer.merchant?.communication?.fax).isEqualTo(apiVehicleDealer.dealerData?.communication?.fax)
        softly.assertThat(vehicleDealer.merchant?.communication?.email).isEqualTo(apiVehicleDealer.dealerData?.communication?.email)
        softly.assertThat(vehicleDealer.merchant?.communication?.website).isEqualTo(apiVehicleDealer.dealerData?.communication?.website)
        softly.assertThat(vehicleDealer.merchant?.communication?.facebook).isEqualTo(apiVehicleDealer.dealerData?.communication?.facebook)
        softly.assertThat(vehicleDealer.merchant?.communication?.mobile).isEqualTo(apiVehicleDealer.dealerData?.communication?.mobile)
        softly.assertThat(vehicleDealer.merchant?.communication?.googlePlus).isEqualTo(apiVehicleDealer.dealerData?.communication?.googlePlus)
        softly.assertThat(vehicleDealer.merchant?.communication?.twitter).isEqualTo(apiVehicleDealer.dealerData?.communication?.twitter)
        softly.assertThat(vehicleDealer.merchant?.communication?.phone).isEqualTo(apiVehicleDealer.dealerData?.communication?.phone)
        softly.assertThat(vehicleDealer.merchant?.openingHours?.monday).isEqualTo(apiVehicleDealer.dealerData?.openingHours?.monday)
        softly.assertThat(vehicleDealer.merchant?.openingHours?.tuesday).isEqualTo(apiVehicleDealer.dealerData?.openingHours?.tuesday)
        softly.assertThat(vehicleDealer.merchant?.openingHours?.wednesday).isEqualTo(apiVehicleDealer.dealerData?.openingHours?.wednesday)
        softly.assertThat(vehicleDealer.merchant?.openingHours?.thursday).isEqualTo(apiVehicleDealer.dealerData?.openingHours?.thursday)
        softly.assertThat(vehicleDealer.merchant?.openingHours?.friday).isEqualTo(apiVehicleDealer.dealerData?.openingHours?.friday)
        softly.assertThat(vehicleDealer.merchant?.openingHours?.saturday).isEqualTo(apiVehicleDealer.dealerData?.openingHours?.saturday)
        softly.assertThat(vehicleDealer.merchant?.openingHours?.sunday).isEqualTo(apiVehicleDealer.dealerData?.openingHours?.sunday)
    }
}
