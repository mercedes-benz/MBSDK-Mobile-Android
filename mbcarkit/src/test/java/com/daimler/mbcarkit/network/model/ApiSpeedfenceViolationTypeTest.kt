package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.speedfence.SpeedFenceViolationType
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class ApiSpeedfenceViolationTypeTest {

    @Test
    fun `Mapping 'null' ApiSpeedfenceViolationType should default to 'null'`() {
        Assertions.assertNull(null.toSpeedFenceViolationType())
    }

    @ParameterizedTest
    @EnumSource(ApiSpeedfenceViolationType::class)
    fun `Mapping from ApiSpeedfenceViolationType to SpeedFenceViolationType enum`(apiSpeedfenceViolationType: ApiSpeedfenceViolationType) {
        val actual = apiSpeedfenceViolationType.toSpeedFenceViolationType()
        val expected = when (apiSpeedfenceViolationType) {
            ApiSpeedfenceViolationType.ENTER -> SpeedFenceViolationType.ENTER
            ApiSpeedfenceViolationType.LEAVE -> SpeedFenceViolationType.LEAVE
            ApiSpeedfenceViolationType.LEAVE_AND_ENTER -> SpeedFenceViolationType.LEAVE_AND_ENTER
            else -> fail("Not all conditions covered!")
        }
        Assertions.assertEquals(expected, actual)
    }

    @ParameterizedTest
    @EnumSource(SpeedFenceViolationType::class)
    fun `Mapping from SpeedFenceViolationType to ApiSpeedfenceViolationType enum`(speedFenceViolationType: SpeedFenceViolationType) {
        val actual = ApiSpeedfenceViolationType.fromSpeedFenceViolationType(speedFenceViolationType)
        val expected = when (speedFenceViolationType) {
            SpeedFenceViolationType.ENTER -> ApiSpeedfenceViolationType.ENTER
            SpeedFenceViolationType.LEAVE -> ApiSpeedfenceViolationType.LEAVE
            SpeedFenceViolationType.LEAVE_AND_ENTER -> ApiSpeedfenceViolationType.LEAVE_AND_ENTER
            else -> fail("Not all conditions covered!")
        }
        Assertions.assertEquals(expected, actual)
    }
}
