package com.daimler.mbmobilesdk.vehiclestage

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.socket.MyCarSocketAndroidViewModel
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbmobilesdk.utils.resizeBitmap
import com.daimler.mbmobilesdk.vehicleselection.VehicleImageLoader
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbcarkit.business.model.vehicle.image.Degrees
import com.daimler.mbcarkit.socket.observable.VehicleObserver
import com.daimler.mbnetworkkit.socket.message.Observables
import com.daimler.mbnetworkkit.socket.message.dispose
import com.daimler.mbnetworkkit.socket.message.observe
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf

class VehicleStageViewModel(
    app: Application,
    private var vehicle: VehicleInfo
) : MyCarSocketAndroidViewModel(app) {

    val toolbarTitle = MutableLiveData<String>()
    val carTitle = mutableLiveDataOf(vehicle.model)
    val carDescription = mutableLiveDataOf(vehicle.licensePlate)
    val carFin = mutableLiveDataOf(vehicle.finOrVin)
    val carImage = MutableLiveData<Bitmap>()
    val showFallbackImage = mutableLiveDataOf(false)

    private val vehicleUpdateObserver = VehicleObserver.VehicleUpdate { refreshVehicle() }

    init {
        loadCarImage()
        connect()
    }

    override fun onRegisterObservers(observables: Observables) {
        observables.observe(vehicleUpdateObserver)
    }

    override fun onDisposeObservers(observables: Observables) {
        observables.dispose(vehicleUpdateObserver)
    }

    internal fun vehicle() = vehicle

    internal fun updateVin(finOrVin: String) {
        updateVehicle(vehicle.copy(vin = finOrVin, fin = finOrVin))
    }

    private fun updateVehicle(vehicle: VehicleInfo) {
        this.vehicle = vehicle
        carTitle.postValue(vehicle.model)
        carDescription.postValue(vehicle.licensePlate)
        carFin.postValue(vehicle.finOrVin)
        loadCarImage()
    }

    private fun refreshVehicle() {
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                MBCarKit.vehicleService().fetchVehicles(token.jwtToken.plainToken)
                    .onComplete { vehicles ->
                        vehicles.find {
                            it.finOrVin == vehicle.finOrVin
                        }?.let { updateVehicle(it) }
                    }
                    .onFailure {
                        MBLoggerKit.re("Failed to load vehicles.", it)
                    }
            }
            .onFailure {
                MBLoggerKit.e("Failed to refresh token.", throwable = it)
            }
    }

    private fun loadCarImage() {
        if (vehicle.finOrVin.isBlank()) {
            showFallbackImage.postValue(true)
            return
        }

        VehicleImageLoader(vehicle.finOrVin).loadDefault(Degrees.DEGREES_320)
            .onComplete { images ->
                images.firstOrNull()?.let { image ->
                    showFallbackImage.postValue(false)
                    image.imageBytes?.let { setCarImage(it) }
                }
            }
            .onFailure {
                MBLoggerKit.re("Could not load vehicle image.", it)
                showFallbackImage.postValue(true)
            }
    }

    private fun setCarImage(image: ByteArray) {
        val bmp = resizeBitmap(image, MAX_IMAGE_WIDTH, MAX_IMAGE_HEIGHT)
        bmp?.let { carImage.postValue(it) }
    }

    private companion object {
        private const val MAX_IMAGE_WIDTH = 960
        private const val MAX_IMAGE_HEIGHT = 540
    }
}