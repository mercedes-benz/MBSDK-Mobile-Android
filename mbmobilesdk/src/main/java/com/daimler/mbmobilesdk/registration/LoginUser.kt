package com.daimler.mbmobilesdk.registration

import android.os.Parcel
import android.os.Parcelable
import com.daimler.mbmobilesdk.app.UserLocale
import com.daimler.mbcommonkit.extensions.readBoolean
import com.daimler.mbcommonkit.extensions.writeBoolean
import com.daimler.mbingresskit.common.UserAgreementUpdates

data class LoginUser(
    val user: String,
    val isMail: Boolean,
    val userLocale: UserLocale,
    val pendingNatconAgreements: UserAgreementUpdates? = null
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readBoolean(),
        parcel.readParcelable(UserLocale::class.java.classLoader),
        parcel.readParcelable(UserAgreementUpdates::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(user)
        parcel.writeBoolean(isMail)
        parcel.writeParcelable(userLocale, flags)
        parcel.writeParcelable(pendingNatconAgreements, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginUser> {
        override fun createFromParcel(parcel: Parcel): LoginUser {
            return LoginUser(parcel)
        }

        override fun newArray(size: Int): Array<LoginUser?> {
            return arrayOfNulls(size)
        }
    }
}