package com.daimler.mbmobilesdk.vehicledeletion

import android.os.Parcel
import android.os.Parcelable

internal data class DeletableVehicle(val finOrVin: String, val model: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(finOrVin)
        parcel.writeString(model)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DeletableVehicle> {
        override fun createFromParcel(parcel: Parcel): DeletableVehicle {
            return DeletableVehicle(parcel)
        }

        override fun newArray(size: Int): Array<DeletableVehicle?> {
            return arrayOfNulls(size)
        }
    }
}