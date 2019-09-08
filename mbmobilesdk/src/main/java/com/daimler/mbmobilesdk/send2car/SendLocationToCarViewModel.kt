package com.daimler.mbmobilesdk.send2car

import android.app.Application
import android.content.Intent
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.R
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.sendtocar.RouteType
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarRoute
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarWaypoint
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.utils.extensions.getString
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf

class SendLocationToCarViewModel(app: Application) : AndroidViewModel(app) {

    private val intentLocationParser = IntentLocationParser(app)

    val progressVisible = mutableLiveDataOf(true)
    val statusText = MutableLiveData<String>()
    val locationText = MutableLiveData<String>()
    val vehicles = MutableLiveArrayList<VehicleItem>()
    val screenFinishedEvent = MutableLiveEvent<Unit>()

    private var locationLoaded = false
    private var vehiclesLoaded = false
    private var location: Location? = null

    private val vehicleClickListener: (VehicleItem) -> Unit = { vehicleItem ->
        location?.let { loc ->
            progressVisible.value = true
            MBIngressKit.refreshTokenIfRequired().onComplete { token ->
                val plainToken = token.jwtToken.plainToken
                val route = SendToCarRoute(RouteType.SINGLE_POI, listOf(SendToCarWaypoint(loc.latitude, loc.longitude)))
                MBCarKit.sendToCarService().sendRoute(plainToken, vehicleItem.finOrVin, route)
                    .onComplete {
                        screenFinishedEvent.sendEvent(Unit)
                    }
                    .onFailure {
                        progressVisible.value = false
                        statusText.value = getString(R.string.bottom_share_sheet_send_route_error)
                    }
            }.onFailure {
                progressVisible.value = false
                statusText.value = getString(R.string.login_session_expired)
            }
        }
    }

    init {
        MBIngressKit.refreshTokenIfRequired().onComplete {
            MBCarKit.vehicleService().fetchVehicles(it.jwtToken.plainToken)
                .onComplete { vehiclesResponse ->
                    vehicles.addAllAndDispatch(vehiclesResponse.vehicles.map { vehicleInfo ->
                        VehicleItem(vehicleInfo.finOrVin, vehicleInfo.model, vehicleClickListener)
                    })
                }
                .onAlways { _, _, _ ->
                    vehiclesLoaded = true
                    tryInit()
                }
        }.onFailure {
            progressVisible.value = false
            statusText.value = getString(R.string.login_session_expired)
        }
    }

    fun processNewIntent(intent: Intent) {
        intentLocationParser.getFromIntent(intent)
            .onComplete {
                location = it
                locationText.value = "${it.longitude}, ${it.latitude}"
            }
            .onAlways { _, _, _ ->
                locationLoaded = true
                tryInit()
            }
    }

    private fun tryInit() {
        if (locationLoaded && vehiclesLoaded) {
            progressVisible.value = false

            if (locationText.value.isNullOrEmpty()) {
                statusText.value = getString(R.string.bottom_share_sheet_fetch_location_info_error)
                return
            }

            if (vehicles.value.isEmpty()) {
                statusText.value = getString(R.string.bottom_share_sheet_vehicles_error)
                return
            }
        }
    }
}