package com.daimler.mbcarkit.socket

import com.daimler.mbcarkit.business.model.vehicle.VehicleStatus
import com.daimler.mbcarkit.socket.observable.AuxheatObservableMessage
import com.daimler.mbcarkit.socket.observable.DoorsObservableMessage
import com.daimler.mbcarkit.socket.observable.DrivingModesObservableMessage
import com.daimler.mbcarkit.socket.observable.EcoScoreObservableMessage
import com.daimler.mbcarkit.socket.observable.EngineObservableMessage
import com.daimler.mbcarkit.socket.observable.HeadUnitObservableMessage
import com.daimler.mbcarkit.socket.observable.LocationObservableMessage
import com.daimler.mbcarkit.socket.observable.StatisticsObservableMessage
import com.daimler.mbcarkit.socket.observable.TankObservableMessage
import com.daimler.mbcarkit.socket.observable.TheftObservableMessage
import com.daimler.mbcarkit.socket.observable.TiresObservableMessage
import com.daimler.mbcarkit.socket.observable.VehicleDataObservable
import com.daimler.mbcarkit.socket.observable.VehicleStatusObservableMessage
import com.daimler.mbcarkit.socket.observable.WarningsObservableMessage
import com.daimler.mbcarkit.socket.observable.WindowsObservableMessage
import com.daimler.mbcarkit.socket.observable.ZevObservableMessage
import com.daimler.mbnetworkkit.socket.message.Notifyable
import com.daimler.mbnetworkkit.socket.message.notifyChange

internal class CarKitMessageNotificator(
    private val notifyable: Notifyable
) {

    fun notifyVehicleRelatedObservers(
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        notifyVehicleStatusObservablesIfChanged(
            VehicleStatusObservableMessage(updatedVehicleStatus),
            oldVehicleStatus,
            updatedVehicleStatus
        )
        notifyAuxheatObservablesIfChanged(
            AuxheatObservableMessage(updatedVehicleStatus.auxheat),
            oldVehicleStatus,
            updatedVehicleStatus
        )
        notifyDoorsObservablesIfChanged(
            DoorsObservableMessage(updatedVehicleStatus.doors),
            oldVehicleStatus,
            updatedVehicleStatus
        )
        notifyEcoScoreObservablesIfChanged(
            EcoScoreObservableMessage(updatedVehicleStatus.ecoScore),
            oldVehicleStatus,
            updatedVehicleStatus
        )
        notifyHuObservablesIfChanged(
            HeadUnitObservableMessage(updatedVehicleStatus.hu),
            oldVehicleStatus,
            updatedVehicleStatus
        )
        notifyLocationObservableIfChanged(
            LocationObservableMessage(updatedVehicleStatus.location),
            oldVehicleStatus,
            updatedVehicleStatus
        )
        notifyStatisticsObservableIfChanged(
            StatisticsObservableMessage(updatedVehicleStatus.statistics),
            oldVehicleStatus,
            updatedVehicleStatus
        )
        notifyTankObservableIfChanged(
            TankObservableMessage(updatedVehicleStatus.tank),
            oldVehicleStatus,
            updatedVehicleStatus
        )
        notifyTiresObservableIfChanged(
            TiresObservableMessage(updatedVehicleStatus.tires),
            oldVehicleStatus,
            updatedVehicleStatus
        )
        notifyVehicleDataObservableIfChanged(
            VehicleDataObservable(updatedVehicleStatus.vehicle),
            oldVehicleStatus,
            updatedVehicleStatus
        )
        notifyWarningsObservableIfChanged(
            WarningsObservableMessage(updatedVehicleStatus.warnings),
            oldVehicleStatus,
            updatedVehicleStatus
        )
        notifyWindowsObservableIfChanged(
            WindowsObservableMessage(updatedVehicleStatus.windows),
            oldVehicleStatus,
            updatedVehicleStatus
        )
        notifyTheftObservableIfChanged(
            TheftObservableMessage(updatedVehicleStatus.theft),
            oldVehicleStatus,
            updatedVehicleStatus
        )
        notifyEngineObservableIfChanged(
            EngineObservableMessage(updatedVehicleStatus.engine),
            oldVehicleStatus,
            updatedVehicleStatus
        )
        notifyZevObservableIfChanged(
            ZevObservableMessage(updatedVehicleStatus.zev),
            oldVehicleStatus,
            updatedVehicleStatus
        )
        notifyDrivingModesObservableIfChanged(
            DrivingModesObservableMessage(updatedVehicleStatus.drivingModes),
            oldVehicleStatus,
            updatedVehicleStatus
        )
    }

    private fun notifyDrivingModesObservableIfChanged(
        drivingModesObservableMessage: DrivingModesObservableMessage,
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        if (drivingModesObservableMessage.hasChanged(oldVehicleStatus, updatedVehicleStatus)) {
            notifyable.notifyChange(drivingModesObservableMessage.data)
        }
    }

    private fun notifyZevObservableIfChanged(
        zevObservableMessage: ZevObservableMessage,
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        if (zevObservableMessage.hasChanged(oldVehicleStatus, updatedVehicleStatus)) {
            notifyable.notifyChange(zevObservableMessage.data)
        }
    }

    private fun notifyVehicleStatusObservablesIfChanged(
        vehicleStatusObservableMessage: VehicleStatusObservableMessage,
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        if (vehicleStatusObservableMessage.hasChanged(oldVehicleStatus, updatedVehicleStatus)) {
            notifyable.notifyChange(vehicleStatusObservableMessage.data)
        }
    }

    private fun notifyAuxheatObservablesIfChanged(
        auxheatObservableMessage: AuxheatObservableMessage,
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        if (auxheatObservableMessage.hasChanged(oldVehicleStatus, updatedVehicleStatus)) {
            notifyable.notifyChange(auxheatObservableMessage.data)
        }
    }

    private fun notifyDoorsObservablesIfChanged(
        doorsObservableMessage: DoorsObservableMessage,
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        if (doorsObservableMessage.hasChanged(oldVehicleStatus, updatedVehicleStatus)) {
            notifyable.notifyChange(doorsObservableMessage.data)
        }
    }

    private fun notifyEcoScoreObservablesIfChanged(
        ecoScoreObservableMessage: EcoScoreObservableMessage,
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        if (ecoScoreObservableMessage.hasChanged(oldVehicleStatus, updatedVehicleStatus)) {
            notifyable.notifyChange(ecoScoreObservableMessage.data)
        }
    }

    private fun notifyHuObservablesIfChanged(
        headUnitObservableMessage: HeadUnitObservableMessage,
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        if (headUnitObservableMessage.hasChanged(oldVehicleStatus, updatedVehicleStatus)) {
            notifyable.notifyChange(headUnitObservableMessage.data)
        }
    }

    private fun notifyLocationObservableIfChanged(
        locationObservableMessage: LocationObservableMessage,
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        if (locationObservableMessage.hasChanged(oldVehicleStatus, updatedVehicleStatus)) {
            notifyable.notifyChange(locationObservableMessage.data)
        }
    }

    private fun notifyStatisticsObservableIfChanged(
        statisticsObservableMessage: StatisticsObservableMessage,
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        if (statisticsObservableMessage.hasChanged(oldVehicleStatus, updatedVehicleStatus)) {
            notifyable.notifyChange(statisticsObservableMessage.data)
        }
    }

    private fun notifyTankObservableIfChanged(
        tankObservableMessage: TankObservableMessage,
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        if (tankObservableMessage.hasChanged(oldVehicleStatus, updatedVehicleStatus)) {
            notifyable.notifyChange(tankObservableMessage.data)
        }
    }

    private fun notifyTiresObservableIfChanged(
        tiresObservableMessage: TiresObservableMessage,
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        if (tiresObservableMessage.hasChanged(oldVehicleStatus, updatedVehicleStatus)) {
            notifyable.notifyChange(tiresObservableMessage.data)
        }
    }

    private fun notifyVehicleDataObservableIfChanged(
        vehicleDataObservableMessage: VehicleDataObservable,
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        if (vehicleDataObservableMessage.hasChanged(oldVehicleStatus, updatedVehicleStatus)) {
            notifyable.notifyChange(vehicleDataObservableMessage.data)
        }
    }

    private fun notifyWarningsObservableIfChanged(
        warningsObservableMessage: WarningsObservableMessage,
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        if (warningsObservableMessage.hasChanged(oldVehicleStatus, updatedVehicleStatus)) {
            notifyable.notifyChange(warningsObservableMessage.data)
        }
    }

    private fun notifyWindowsObservableIfChanged(
        windowsObservableMessage: WindowsObservableMessage,
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        if (windowsObservableMessage.hasChanged(oldVehicleStatus, updatedVehicleStatus)) {
            notifyable.notifyChange(windowsObservableMessage.data)
        }
    }

    private fun notifyTheftObservableIfChanged(
        theftObservableMessage: TheftObservableMessage,
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        if (theftObservableMessage.hasChanged(oldVehicleStatus, updatedVehicleStatus)) {
            notifyable.notifyChange(theftObservableMessage.data)
        }
    }

    private fun notifyEngineObservableIfChanged(
        engineObservableMessage: EngineObservableMessage,
        oldVehicleStatus: VehicleStatus?,
        updatedVehicleStatus: VehicleStatus
    ) {
        if (engineObservableMessage.hasChanged(oldVehicleStatus, updatedVehicleStatus)) {
            notifyable.notifyChange(engineObservableMessage.data)
        }
    }
}
