package com.daimler.mbmobilesdk.example.vehicleSelection

import android.graphics.Bitmap
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.MutableLiveData
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbmobilesdk.example.R
import com.daimler.mbmobilesdk.example.car.CarKitRepository
import com.daimler.mbmobilesdk.example.utils.MultipleRecyclerItem
import com.daimler.mbmobilesdk.example.utils.getBitmap

class VehicleInfoItem(
    private val vehicleInfo: VehicleInfo,
    private val clicked: ((VehicleInfo) -> Unit)? = null
) : MultipleRecyclerItem() {

    private val carKitRepository = CarKitRepository()

    val vehicleImage = MutableLiveData<Bitmap>()
    val model = MutableLiveData(vehicleInfo.model)
    val vin = MutableLiveData(vehicleInfo.finOrVin)
    val fuelType = MutableLiveData(vehicleInfo.fuelType.toString())
    val roofType = MutableLiveData(vehicleInfo.roofType.toString())

    init {
        carKitRepository.fetchVehicleImagesWithVin(vehicleInfo.finOrVin)
            .onComplete { vehicleImages ->
                vehicleImage.postValue(vehicleImages.first().getBitmap())
            }
    }

    override fun getLayoutRes() = R.layout.item_vehicle_selection

    override fun getModelId() = BR.item

    fun onClicked() = clicked?.invoke(vehicleInfo)
}
