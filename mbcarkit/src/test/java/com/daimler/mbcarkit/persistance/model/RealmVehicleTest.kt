package com.daimler.mbcarkit.persistance.model

import com.daimler.mbcarkit.business.model.vehicle.AssignmentPendingState
import com.daimler.mbcarkit.business.model.vehicle.DataCollectorVersion
import com.daimler.mbcarkit.business.model.vehicle.DoorsCount
import com.daimler.mbcarkit.business.model.vehicle.FuelType
import com.daimler.mbcarkit.business.model.vehicle.HandDrive
import com.daimler.mbcarkit.business.model.vehicle.NormalizedProfileControlSupport
import com.daimler.mbcarkit.business.model.vehicle.ProfileSyncSupport
import com.daimler.mbcarkit.business.model.vehicle.RoofType
import com.daimler.mbcarkit.business.model.vehicle.StarArchitecture
import com.daimler.mbcarkit.business.model.vehicle.TcuHardwareVersion
import com.daimler.mbcarkit.business.model.vehicle.TcuSoftwareVersion
import com.daimler.mbcarkit.business.model.vehicle.TirePressureMonitoringState
import com.daimler.mbcarkit.business.model.vehicle.VehicleAmenity
import com.daimler.mbcarkit.business.model.vehicle.VehicleConnectivity
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbcarkit.business.model.vehicle.VehicleSegment
import com.daimler.mbcarkit.business.model.vehicle.WindowsLiftCount
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.util.Date

@ExtendWith(SoftAssertionsExtension::class)
internal class RealmVehicleTest {
    @Test
    fun `should map vehicle info to realm model`(softly: SoftAssertions) {
        val testInfo = VehicleInfo(
            "WDX12345678",
            "XYZ56789",
            "S-PO 1234",
            "A class",
            AssignmentPendingState.ASSIGN,
            1,
            "12345234",
            TirePressureMonitoringState.TIRE_PRESSURE_MONITORING,
            emptyList(),
            "abc",
            DataCollectorVersion.DC2,
            DoorsCount.FOUR_DOORS,
            FuelType.ELECTRIC,
            HandDrive.LEFT,
            true,
            true,
            RoofType.PANORAMA_SUNROOF,
            StarArchitecture.STAR_2,
            TcuHardwareVersion.HERMES_2,
            WindowsLiftCount.NO_LIFT,
            VehicleConnectivity.BUILT_IN,
            TcuSoftwareVersion.V_216_4,
            VehicleSegment.AMG,
            true,
            "https://x.com/xyz.png",
            false,
            Date(),
            ProfileSyncSupport.MANAGE_IN_HEAD_UNIT,
            NormalizedProfileControlSupport.SUPPORTED,
            VehicleAmenity("1234", "desc"),
            VehicleAmenity("567", "desc2"),
            VehicleAmenity("890", "desc3")
        )

        val realmVehicle = RealmVehicle().apply {
            fromVehicleInfoNoDealers(testInfo)
        }
        softly.assertThat(realmVehicle.vin).isEqualTo(testInfo.vin)
        softly.assertThat(realmVehicle.fin).isEqualTo(testInfo.fin)
        softly.assertThat(realmVehicle.licensePlate).isEqualTo(testInfo.licensePlate)
        softly.assertThat(realmVehicle.model).isEqualTo(testInfo.model)
        softly.assertThat(realmVehicle.assignmentState).isEqualTo(testInfo.assignmentState?.ordinal)
        softly.assertThat(realmVehicle.trustLevel).isEqualTo(testInfo.trustLevel)
        softly.assertThat(realmVehicle.tirePressureMonitoringState)
            .isEqualTo(testInfo.tirePressureMonitoringState?.ordinal)
        softly.assertThat(realmVehicle.carline).isEqualTo(testInfo.carline)
        softly.assertThat(realmVehicle.dataCollectionVersion)
            .isEqualTo(testInfo.dataCollectorVersion?.ordinal)
        softly.assertThat(realmVehicle.doorsCount).isEqualTo(testInfo.doorsCount?.ordinal)
        softly.assertThat(realmVehicle.fuelType).isEqualTo(testInfo.fuelType?.ordinal)
        softly.assertThat(realmVehicle.handDrive).isEqualTo(testInfo.handDrive?.ordinal)
        softly.assertThat(realmVehicle.hasAuxHeat).isEqualTo(testInfo.hasAuxHeat)
        softly.assertThat(realmVehicle.mopf).isEqualTo(testInfo.hasFaceLift)
        softly.assertThat(realmVehicle.roofType).isEqualTo(testInfo.roofType?.ordinal)
        softly.assertThat(realmVehicle.starArchitecture)
            .isEqualTo(testInfo.starArchitecture?.ordinal)
        softly.assertThat(realmVehicle.tcuHardwareVersion)
            .isEqualTo(testInfo.tcuHardwareVersion?.ordinal)
        softly.assertThat(realmVehicle.windowsLiftCount)
            .isEqualTo(testInfo.windowsLiftCount?.ordinal)
        softly.assertThat(realmVehicle.vehicleConnectivity)
            .isEqualTo(testInfo.vehicleConnectivity?.ordinal)
        softly.assertThat(realmVehicle.tcuSoftwareVersion)
            .isEqualTo(testInfo.tcuSoftwareVersion?.ordinal)
        softly.assertThat(realmVehicle.vehicleSegment).isEqualTo(testInfo.vehicleSegment?.ordinal)
        softly.assertThat(realmVehicle.isOwner).isEqualTo(testInfo.isOwner)
        softly.assertThat(realmVehicle.indicatorImageUrl).isEqualTo(testInfo.indicatorImageUrl)
        softly.assertThat(realmVehicle.temporarilyAccessible)
            .isEqualTo(testInfo.temporarilyAccessible)
        softly.assertThat(realmVehicle.accessibleUntil).isEqualTo(testInfo.accessibleUntil?.time)
        softly.assertThat(realmVehicle.profileSyncSupport)
            .isEqualTo(testInfo.profileSyncSupport?.ordinal)
        softly.assertThat(realmVehicle.normalizedProfileControlSupport)
            .isEqualTo(testInfo.normalizedProfileControlSupport?.ordinal)
        softly.assertThat(realmVehicle.paintCode).isEqualTo(testInfo.paint?.code)
        softly.assertThat(realmVehicle.paintDescription).isEqualTo(testInfo.paint?.description)
        softly.assertThat(realmVehicle.upholsteryCode).isEqualTo(testInfo.upholstery?.code)
        softly.assertThat(realmVehicle.upholsteryDescription)
            .isEqualTo(testInfo.upholstery?.description)
        softly.assertThat(realmVehicle.lineCode).isEqualTo(testInfo.line?.code)
        softly.assertThat(realmVehicle.lineDescription).isEqualTo(testInfo.line?.description)
    }
}
