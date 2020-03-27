package com.daimler.mbcarkit.business.model

import android.os.Parcel

inline fun <reified E : Enum<E>> Parcel.writeEnum(value: E?) {
    this.writeInt(value?.ordinal ?: -1)
}

inline fun <reified E : Enum<E>> Parcel.readEnum(): E? {
    return readInt().toEnum<E>()
}
