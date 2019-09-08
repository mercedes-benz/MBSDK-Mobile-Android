package com.daimler.mbmobilesdk.dialogs

import androidx.lifecycle.ViewModel
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.vehicle.DealerRole
import com.daimler.mbcarkit.business.model.vehicle.VehicleDealer
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import java.util.*

class DealerActionDialogViewModel(
    val dealerId: String?,
    val finOrVin: String?,
    val dealerName: String?,
    val phone: String?,
    val city: String?,
    val street: String?,
    var dealers: Array<VehicleDealer>?
) : ViewModel() {

    val onCallClickEvent = MutableLiveEvent<String>()
    val onNavigationClickEvent = MutableLiveEvent<Pair<String?, String?>>()
    val onCancelClickEvent = MutableLiveUnitEvent()
    val onSaveClickEvent = MutableLiveUnitEvent()
    val onSaveErrorEvent = MutableLiveEvent<String?>()

    val saveAvailable = !dealerId.isNullOrBlank() && !finOrVin.isNullOrBlank()
    val navigateAvailable = city != null || street != null
    val dealerNameAvailable = !dealerName.isNullOrBlank()

    fun onCallClick() {
        phone?.let {
            onCallClickEvent.sendEvent(it)
        }
    }

    fun onNavigateClick() {
        if (navigateAvailable) {
            onNavigationClickEvent.sendEvent(Pair(city, street))
        }
    }

    fun onSaveClick() {
        if (dealerId.isNullOrBlank() || finOrVin.isNullOrBlank()) return

        onSaveClickEvent.sendEvent()

        if (dealers == null) dealers = arrayOf()

        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                dealers?.plus(VehicleDealer(dealerId, DealerRole.SERVICE, Date()))?.let {
                    MBCarKit.vehicleService().updateVehicleDealers(token.jwtToken.plainToken, finOrVin, it.toList())
                }
            }
            .onFailure {
                onSaveErrorEvent.sendEvent(it?.localizedMessage)
            }
    }

    fun onCancelClick() {
        onCancelClickEvent.sendEvent()
    }
}