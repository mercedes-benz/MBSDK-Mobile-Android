package com.daimler.mbmobilesdk.app

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

open class UserLocale(
    @SerializedName("countryCode") val countryCode: String,
    @SerializedName("languageCode") val languageCode: String
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().orEmpty(),
        parcel.readString().orEmpty())

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(countryCode)
        dest.writeString(languageCode)
    }

    override fun describeContents(): Int = 0

    companion object {
        @JvmField
        val CREATOR = object : Parcelable.Creator<UserLocale> {
            override fun createFromParcel(parcel: Parcel): UserLocale {
                return UserLocale(parcel)
            }

            override fun newArray(size: Int): Array<UserLocale?> {
                return arrayOfNulls(size)
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null) return false
        other as UserLocale
        return countryCode == other.countryCode && languageCode == other.languageCode
    }

    override fun hashCode(): Int {
        return 31 * countryCode.hashCode() + languageCode.hashCode()
    }
}

fun UserLocale.format() = format("-")

fun UserLocale.format(separator: String) = "$languageCode$separator$countryCode"

object EmptyUserLocale : UserLocale("", "")