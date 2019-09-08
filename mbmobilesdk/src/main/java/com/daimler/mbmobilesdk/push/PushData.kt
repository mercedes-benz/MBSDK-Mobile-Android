package com.daimler.mbmobilesdk.push

import android.os.Parcel
import android.os.Parcelable
import com.daimler.mbmobilesdk.push.process.PushDataState
import com.daimler.mbmobilesdk.push.process.PushState

internal data class PushData(
    val title: String? = null,
    val body: String? = null,
    val url: String? = null,
    val category: String? = null,
    val deepLinkReference: String? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(body)
        parcel.writeString(url)
        parcel.writeString(category)
        parcel.writeString(deepLinkReference)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PushData> {
        override fun createFromParcel(parcel: Parcel): PushData {
            return PushData(parcel)
        }

        override fun newArray(size: Int): Array<PushData?> {
            return arrayOfNulls(size)
        }
    }
}

internal fun PushData.toPushState(): PushState =
    when {
        !url.isNullOrBlank() -> PushDataState.Url(url)
        !deepLinkReference.isNullOrBlank() -> PushDataState.DeepLink(deepLinkReference)
        else -> PushDataState.General
    }