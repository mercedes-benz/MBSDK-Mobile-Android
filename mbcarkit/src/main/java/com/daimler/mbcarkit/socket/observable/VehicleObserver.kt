package com.daimler.mbcarkit.socket.observable

import com.daimler.mbnetworkkit.socket.message.MessageObserver
import com.daimler.mbnetworkkit.socket.message.ObservableMessage

sealed class VehicleObserver<T> : MessageObserver<T> {

    open class BaseVehicleObserver<T>(private val action: (T) -> Unit) : VehicleObserver<T>() {
        override fun onUpdate(observableMessage: ObservableMessage<T>) {
            action.invoke(observableMessage.data)
        }
    }

    /**
     * Observer for Auxiliary heating related status updates
     */
    class Auxheat(action: (com.daimler.mbcarkit.business.model.vehicle.Auxheat) -> Unit) : BaseVehicleObserver<com.daimler.mbcarkit.business.model.vehicle.Auxheat>(action)

    /**
     * Observer for Door related status updates
     */
    class Doors(action: (com.daimler.mbcarkit.business.model.vehicle.Doors) -> Unit) : BaseVehicleObserver<com.daimler.mbcarkit.business.model.vehicle.Doors>(action)

    /**
     * Observer for Eco score related status updates
     */
    class EcoScore(action: (com.daimler.mbcarkit.business.model.vehicle.EcoScore) -> Unit) : BaseVehicleObserver<com.daimler.mbcarkit.business.model.vehicle.EcoScore>(action)

    /**
     * Observer for Head Unit related status updates
     */
    class HeadUnit(action: (com.daimler.mbcarkit.business.model.vehicle.HeadUnit) -> Unit) : BaseVehicleObserver<com.daimler.mbcarkit.business.model.vehicle.HeadUnit>(action)

    /**
     * Observer for Vehicle location related status updates
     */
    class Location(action: (com.daimler.mbcarkit.business.model.vehicle.Location) -> Unit) : BaseVehicleObserver<com.daimler.mbcarkit.business.model.vehicle.Location>(action)

    /**
     * Observer for Journey/Statistics related status updates
     */
    class Statistics(action: (com.daimler.mbcarkit.business.model.vehicle.Statistics) -> Unit) : BaseVehicleObserver<com.daimler.mbcarkit.business.model.vehicle.Statistics>(action)

    /**
     * Observer for Tank related status updates
     */
    class Tank(action: (com.daimler.mbcarkit.business.model.vehicle.Tank) -> Unit) : BaseVehicleObserver<com.daimler.mbcarkit.business.model.vehicle.Tank>(action)

    /**
     * Observer for Tire related status updates
     */
    class Tires(action: (com.daimler.mbcarkit.business.model.vehicle.Tires) -> Unit) : BaseVehicleObserver<com.daimler.mbcarkit.business.model.vehicle.Tires>(action)

    /**
     * Observer for miscellaneous vehicle data related status updates
     */
    class VehicleData(action: (com.daimler.mbcarkit.business.model.vehicle.VehicleData) -> Unit) : BaseVehicleObserver<com.daimler.mbcarkit.business.model.vehicle.VehicleData>(action)

    /**
     * Observer for overall vehicle status updates
     */
    class VehicleStatus(action: (com.daimler.mbcarkit.business.model.vehicle.VehicleStatus) -> Unit) : BaseVehicleObserver<com.daimler.mbcarkit.business.model.vehicle.VehicleStatus>(action)

    /**
     * Observer for Warning lamp related status updates
     */
    class Warnings(action: (com.daimler.mbcarkit.business.model.vehicle.VehicleWarnings) -> Unit) : BaseVehicleObserver<com.daimler.mbcarkit.business.model.vehicle.VehicleWarnings>(action)

    /**
     * Observer for Window related status updates
     */
    class Windows(action: (com.daimler.mbcarkit.business.model.vehicle.Windows) -> Unit) : BaseVehicleObserver<com.daimler.mbcarkit.business.model.vehicle.Windows>(action)

    /**
     * Observer for Theft related status updates
     */
    class Theft(action: (com.daimler.mbcarkit.business.model.vehicle.Theft) -> Unit) : BaseVehicleObserver<com.daimler.mbcarkit.business.model.vehicle.Theft>(action)

    /**
     * Observer for Engine related status updates
     */
    class Engine(action: (com.daimler.mbcarkit.business.model.vehicle.Engine) -> Unit) : BaseVehicleObserver<com.daimler.mbcarkit.business.model.vehicle.Engine>(action)

    /**
     * Observer for Zero emission related status updates
     */
    class Zev(action: (com.daimler.mbcarkit.business.model.vehicle.Zev) -> Unit) : BaseVehicleObserver<com.daimler.mbcarkit.business.model.vehicle.Zev>(action)

    /**
     * Observer for vehicle status update metadata
     */
    class VehicleUpdate(action: (com.daimler.mbcarkit.business.model.vehicle.VehicleUpdate) -> Unit) : BaseVehicleObserver<com.daimler.mbcarkit.business.model.vehicle.VehicleUpdate>(action)
}
