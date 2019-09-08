package com.daimler.mbmobilesdk.vehicleselection

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbcarkit.business.model.vehicle.image.Degrees
import com.daimler.mbuikit.components.viewmodels.MBBaseToolbarViewModel
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf
import java.util.*

class VehicleInfoViewModel(
    app: Application,
    private var vehicle: VehicleInfo
) : MBBaseToolbarViewModel(app) {

    val progressVisible = MutableLiveData<Boolean>()

    val carTitle = mutableLiveDataOf(vehicle.model)
    val licensePlate = mutableLiveDataOf(vehicle.licensePlate)
    val carFin = mutableLiveDataOf(vehicle.finOrVin)
    val carImage = MutableLiveData<Bitmap>()
    val showFallbackImage = mutableLiveDataOf(false)

    val errorEvent = MutableLiveEvent<String>()

    init {
        loadCarImage()
    }

    fun onLicensePlateEntered() {
        val licensePlate = licensePlate.value
        if (licensePlate == vehicle.licensePlate) return

        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                MBCarKit.vehicleService().updateLicensePlate(jwt,
                    Locale.getDefault().country, vehicle.finOrVin, licensePlate.orEmpty())
                    .onComplete { MBLoggerKit.d("Updated license plate to $licensePlate.") }
                    .onFailure { MBLoggerKit.re("Failed to update license plate.", it) }
            }.onFailure {
                MBLoggerKit.e("Failed to refresh token.", throwable = it)
                errorEvent.sendEvent(defaultErrorMessage(it))
            }
    }

    private fun loadCarImage() {
        VehicleImageLoader(vehicle.finOrVin).loadDefault(Degrees.DEGREES_320)
            .onComplete { images ->
                images.firstOrNull()?.let { image ->
                    image.imageBytes?.let {
                        showFallbackImage.postValue(false)
                        carImage.postValue(BitmapFactory.decodeByteArray(it, 0, it.size))
                    }
                }
            }
            .onFailure {
                MBLoggerKit.re("Could not load vehicle image.", it)
                showFallbackImage.postValue(true)
            }
    }
}