package com.daimler.mbcarkit.network.model

import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiMerchantResponseTest {

    @Test
    fun `map ApiMerchantResponse to Merchant`(softly: SoftAssertions) {
        val apiMerchant = ApiMerchantResponse(
            "id",
            "legalName",
            ApiAddress("street", null, null, null, null, null),
            ApiRegion("region", null),
            null,
            listOf(),
            ApiCommunication("fax", null, null, null, null, null, null, null),
            ApiOpeningHours(ApiDay("status", null), null, null, null, null, null, null)
        )
        val merchant = apiMerchant.toMerchant()

        softly.assertThat(merchant.id).isEqualTo(apiMerchant.id)
        softly.assertThat(merchant.legalName).isEqualTo(apiMerchant.legalName)
        softly.assertThat(merchant.address?.street).isEqualTo(apiMerchant.address?.street)
        softly.assertThat(merchant.region?.region).isEqualTo(apiMerchant.region?.region)
        softly.assertThat(merchant.coordinates?.latitude).isNull()
        softly.assertThat(merchant.coordinates?.longitude).isNull()
        softly.assertThat(merchant.communication?.fax).isEqualTo(apiMerchant.communication?.fax)
        softly.assertThat(merchant.openingHours?.monday?.status).isEqualTo(apiMerchant.openingHours?.monday?.status)
    }
}
