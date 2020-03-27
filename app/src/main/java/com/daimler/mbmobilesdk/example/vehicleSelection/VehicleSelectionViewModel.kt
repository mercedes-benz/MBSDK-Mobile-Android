package com.daimler.mbmobilesdk.example.vehicleSelection

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.daimler.mbmobilesdk.example.car.CarKitRepository
import com.daimler.mbmobilesdk.example.utils.MutableLiveUnitEvent

class VehicleSelectionViewModel : ViewModel() {

    private val carKitRepository = CarKitRepository()
    val vehicles = MutableLiveData<List<VehicleInfoItem>?>()
    val onVehicleSelectedEvent = MutableLiveUnitEvent()

    fun fetchVehiclesForSelection() {
        carKitRepository.fetchVehicleImages()
        carKitRepository.fetchVehicles().onComplete {
            vehicles.postValue(
                it.vehicles.map { info ->
                    VehicleInfoItem(info) { vehicleInfo ->
                        carKitRepository.selectVehicle(vehicleInfo)
                        onVehicleSelectedEvent.sendEvent()
                    }
                }.toList()
            )
        }
    }
}
