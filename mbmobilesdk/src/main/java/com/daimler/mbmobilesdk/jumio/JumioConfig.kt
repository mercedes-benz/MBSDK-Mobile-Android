package com.daimler.mbmobilesdk.jumio

import android.content.Context
import com.jumio.nv.R
import java.io.Serializable
import java.util.*

data class JumioConfig(
    val map: SortedMap<String, Map<Types, List<Variants>>>
) : Serializable

data class JumioSelectedConfig(
    val isoCode: String,
    val selectedType: Types,
    val selectedVariant: Variants
) : Serializable

enum class Types {
    PASSPORT, ID_CARD, DRIVER_LICENSE, VISA;

    fun getLocalizedName(var1: Context): String {
        val typeName = when (this) {
            PASSPORT -> R.string.netverify_documenttype_passport
            VISA -> R.string.netverify_documenttype_visa
            DRIVER_LICENSE -> R.string.netverify_documenttype_driverlicense
            ID_CARD -> R.string.netverify_documenttype_idcard
        }

        return if (typeName != 0) var1.getString(typeName) else ""
    }
}

enum class Variants {
    PAPER, PLASTIC
}