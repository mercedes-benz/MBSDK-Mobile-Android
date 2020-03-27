package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.sendtocar.SendToCarWaypoint
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiSendToCarWaypointTest {

    @Test
    fun `map ApiSendToCarWaypoint from SendToCarWaypoint`(softly: SoftAssertions) {
        val sendToCarWaypoint = SendToCarWaypoint(
            0.0,
            1.0,
            "title",
            "country",
            "state",
            "city",
            "district",
            "postalCode",
            "street",
            "houseNumber",
            "subdivision",
            "phoneNumber"
        )
        val apiSendToCarWaypoint = ApiSendToCarWaypoint.fromSendToCarWaypoint(sendToCarWaypoint)

        softly.assertThat(apiSendToCarWaypoint.latitude).isEqualTo(sendToCarWaypoint.latitude)
        softly.assertThat(apiSendToCarWaypoint.longitude).isEqualTo(sendToCarWaypoint.longitude)
        softly.assertThat(apiSendToCarWaypoint.title).isEqualTo(sendToCarWaypoint.title)
        softly.assertThat(apiSendToCarWaypoint.country).isEqualTo(sendToCarWaypoint.country)
        softly.assertThat(apiSendToCarWaypoint.state).isEqualTo(sendToCarWaypoint.state)
        softly.assertThat(apiSendToCarWaypoint.city).isEqualTo(sendToCarWaypoint.city)
        softly.assertThat(apiSendToCarWaypoint.district).isEqualTo(sendToCarWaypoint.district)
        softly.assertThat(apiSendToCarWaypoint.postalCode).isEqualTo(sendToCarWaypoint.postalCode)
        softly.assertThat(apiSendToCarWaypoint.street).isEqualTo(sendToCarWaypoint.street)
        softly.assertThat(apiSendToCarWaypoint.houseNumber).isEqualTo(sendToCarWaypoint.houseNumber)
        softly.assertThat(apiSendToCarWaypoint.subdivision).isEqualTo(sendToCarWaypoint.subdivision)
        softly.assertThat(apiSendToCarWaypoint.phoneNumber).isEqualTo(sendToCarWaypoint.phoneNumber)
    }
}
