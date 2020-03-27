package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.business.model.vehicle.AssignmentPendingState
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiVehicleTest {

    @Test
    fun `map ApiVehicle to VehicleInfo`(softly: SoftAssertions) {
        val apiVehicle = ApiVehicle(
            "fin",
            "vin",
            "licensePlate",
            ApiTechnicalVehicleInfo("consumerCountry", "sales", "baumuster"),
            ApiSalesRelatedVehicleInfo(
                ApiBaumuster("value", "description"),
                ApiVehicleAmenity("code", "description"),
                ApiVehicleAmenity("code", "description"),
                ApiVehicleAmenity("code", "description")
            ),
            0,
            ApiTirePressureMonitoringState.NO_TIRE_PRESSURE,
            null,
            "carline",
            ApiDataCollectorVersion.DC2_PLUS,
            ApiDoorsCount.THREE_DOORS,
            ApiFuelType.COMBUSTION,
            ApiHandDrive.LEFT,
            true,
            true,
            ApiRoofType.CONVERTIBLE,
            ApiStarArchitecture.STAR_3,
            ApiTcuHardwareVersion.RAMSES,
            ApiWindowsLiftCount.FOUR_LIFT,
            ApiVehicleConnectivity.BUILT_IN,
            ApiTcuSoftwareVersion.V_220,
            ApiVehicleSegment.AMG,
            true,
            "url",
            true,
            null,
            ApiProfileSyncSupport.SUPPORTED,
            ApiNormalizedProfileControlSupport.SUPPORTED
        )
        val vehicleInfo = apiVehicle.toVehicleInfo()

        softly.assertThat(vehicleInfo.fin).isEqualTo(apiVehicle.fin)
        softly.assertThat(vehicleInfo.vin).isEqualTo(apiVehicle.vin)
        softly.assertThat(vehicleInfo.licensePlate).isEqualTo(apiVehicle.licensePlate)
        softly.assertThat(vehicleInfo.model).isEqualTo(apiVehicle.salesRelatedInformation?.baumuster?.description)
        softly.assertThat(vehicleInfo.assignmentState).isEqualTo(AssignmentPendingState.NONE)
        softly.assertThat(vehicleInfo.trustLevel).isEqualTo(apiVehicle.trustLevel)
        softly.assertThat(vehicleInfo.baumuster).isEqualTo(apiVehicle.salesRelatedInformation?.baumuster?.value)
        softly.assertThat(vehicleInfo.tirePressureMonitoringState?.name).isEqualTo(apiVehicle.tirePressureMonitoringState?.name)
        softly.assertThat(vehicleInfo.carline).isEqualTo(apiVehicle.carline)
        softly.assertThat(vehicleInfo.dataCollectorVersion?.name).isEqualTo(apiVehicle.dataCollectorVersion?.name)
        softly.assertThat(vehicleInfo.doorsCount?.name).isEqualTo(apiVehicle.doorsCount?.name)
        softly.assertThat(vehicleInfo.fuelType?.name).isEqualTo(apiVehicle.fuelType?.name)
        softly.assertThat(vehicleInfo.handDrive?.name).isEqualTo(apiVehicle.handDrive?.name)
        softly.assertThat(vehicleInfo.hasAuxHeat).isTrue
        softly.assertThat(vehicleInfo.hasFaceLift).isTrue
        softly.assertThat(vehicleInfo.roofType?.name).isEqualTo(apiVehicle.roofType?.name)
        softly.assertThat(vehicleInfo.starArchitecture?.name).isEqualTo(apiVehicle.starArchitecture?.name)
        softly.assertThat(vehicleInfo.tcuHardwareVersion?.name).isEqualTo(apiVehicle.tcuHardwareVersion?.name)
        softly.assertThat(vehicleInfo.windowsLiftCount?.name).isEqualTo(apiVehicle.windowsLiftCount?.name)
        softly.assertThat(vehicleInfo.vehicleConnectivity?.name).isEqualTo(apiVehicle.vehicleConnectivity?.name)
        softly.assertThat(vehicleInfo.tcuSoftwareVersion?.name).isEqualTo(apiVehicle.tcuSoftwareVersion?.name)
        softly.assertThat(vehicleInfo.vehicleSegment?.name).isEqualTo(apiVehicle.vehicleSegment.name)
        softly.assertThat(vehicleInfo.isOwner).isTrue
        softly.assertThat(vehicleInfo.indicatorImageUrl).isEqualTo(apiVehicle.indicatorImageUrl)
        softly.assertThat(vehicleInfo.temporarilyAccessible).isTrue
        softly.assertThat(vehicleInfo.accessibleUntil).isEqualTo(apiVehicle.accessibleUntil)
        softly.assertThat(vehicleInfo.profileSyncSupport?.name).isEqualTo(apiVehicle.profileSyncSupport?.name)
        softly.assertThat(vehicleInfo.normalizedProfileControlSupport?.name).isEqualTo(apiVehicle.normalizedProfileControlSupport?.name)
        softly.assertThat(vehicleInfo.paint?.code).isEqualTo(apiVehicle.salesRelatedInformation?.paint?.code)
        softly.assertThat(vehicleInfo.paint?.description).isEqualTo(apiVehicle.salesRelatedInformation?.paint?.description)
        softly.assertThat(vehicleInfo.upholstery?.code).isEqualTo(apiVehicle.salesRelatedInformation?.upholstery?.code)
        softly.assertThat(vehicleInfo.upholstery?.description).isEqualTo(apiVehicle.salesRelatedInformation?.upholstery?.description)
        softly.assertThat(vehicleInfo.line?.code).isEqualTo(apiVehicle.salesRelatedInformation?.line?.code)
        softly.assertThat(vehicleInfo.line?.description).isEqualTo(apiVehicle.salesRelatedInformation?.line?.description)
    }
}
