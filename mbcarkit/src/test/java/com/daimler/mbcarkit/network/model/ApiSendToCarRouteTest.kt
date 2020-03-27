package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.sendtocar.RouteType
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarRoute
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarWaypoint
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiSendToCarRouteTest {

    @Test
    fun `map ApiSendToCarRoute from SendToCarRoute`(softly: SoftAssertions) {
        val sendToCarRoute = SendToCarRoute(RouteType.DYNAMIC_ROUTE, listOf(SendToCarWaypoint(0.0, 1.0)), "", "")
        val apiSendToCarRoute = ApiSendToCarRoute.fromSendToCarRoute(sendToCarRoute)

        softly.assertThat(apiSendToCarRoute.routeType.name).isEqualTo(sendToCarRoute.routeType.name)
        softly.assertThat(apiSendToCarRoute.waypoints[0].latitude).isEqualTo(sendToCarRoute.waypoints[0].latitude)
        softly.assertThat(apiSendToCarRoute.waypoints[0].longitude).isEqualTo(sendToCarRoute.waypoints[0].longitude)
        softly.assertThat(apiSendToCarRoute.routeTitle).isEqualTo(sendToCarRoute.routeTitle)
        softly.assertThat(apiSendToCarRoute.notificationText).isEqualTo(sendToCarRoute.notificationText)
    }
}
