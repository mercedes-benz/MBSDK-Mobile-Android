package com.daimler.mbcarkit.business.model.vehicle

import android.os.Parcelable
import com.daimler.mbcarkit.business.model.merchants.Merchant
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class VehicleDealer(
    val dealerId: String,
    val role: DealerRole,
    val updatedAt: Date? = null,
    val merchant: Merchant? = null
) : Parcelable {

    companion object {

        fun dealerRoleFromInt(index: Int?) =
            DealerRole.values().getOrElse(index ?: -1) { DealerRole.UNKNOWN }
    }
}
