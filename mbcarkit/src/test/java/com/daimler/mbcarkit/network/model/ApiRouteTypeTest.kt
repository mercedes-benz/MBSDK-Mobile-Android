package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.sendtocar.RouteType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiRouteTypeTest {

    @ParameterizedTest
    @EnumSource(RouteType::class)
    fun `Mapping from RouteType to ApiRouteType enum`(routeType: RouteType) {
        Assertions.assertEquals(routeType.name, ApiRouteType.fromRouteType(routeType).name)
    }
}
