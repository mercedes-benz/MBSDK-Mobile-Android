package com.daimler.mbmobilesdk.legal.license

import android.os.Parcel
import android.os.Parcelable

data class LicenseDetail(val libraryTitle: String, val licenseFileName: String) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(libraryTitle)
        dest.writeString(licenseFileName)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<LicenseDetail> {
        override fun createFromParcel(parcel: Parcel): LicenseDetail {
            return LicenseDetail(parcel)
        }

        override fun newArray(size: Int): Array<LicenseDetail?> {
            return arrayOfNulls(size)
        }
    }
}