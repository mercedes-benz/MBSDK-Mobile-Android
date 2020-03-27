package com.daimler.mbingresskit.common

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CommunicationPreference(
    val contactByPhone: Boolean,
    val contactByLetter: Boolean,
    val contactByMail: Boolean,
    val contactBySms: Boolean
) : Parcelable {

    companion object {
        fun initialState() =
            CommunicationPreference(
                contactByPhone = false,
                contactByLetter = false,
                contactByMail = false,
                contactBySms = false
            )
    }
}
