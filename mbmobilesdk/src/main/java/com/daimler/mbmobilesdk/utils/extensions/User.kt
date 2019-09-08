package com.daimler.mbmobilesdk.utils.extensions

import com.daimler.mbmobilesdk.featuretoggling.UserContext
import com.daimler.mbingresskit.common.Address
import com.daimler.mbingresskit.common.User

fun User.formatName() =
    "${if (!firstName.isBlank()) "$firstName " else ""}${if (!lastName.isBlank()) lastName else ""}"

fun User.formatNameWithSalutationAndTitle(salutation: String, title: String) =
    "${if (!salutation.isBlank()) "$salutation " else ""}${if (!title.isBlank()) "$title " else ""}${formatName()}"

fun User.formatAddress(): String? = address?.format()

fun User.toUserContext() = UserContext(ciamId, email, countryCode, firstName, lastName)

internal fun Address.format(): String {
    val builder = StringBuilder()
    builder.apply {
        if (!street.isNullOrBlank()) {
            append(street).blank().append(houseNumber ?: "").newLine()
        }
        if (!zipCode.isNullOrBlank()) {
            append(zipCode).blank().append(city ?: "").newLine()
        }
        if (!countryCode.isNullOrBlank()) {
            append(getCountryByCountryCode(countryCode) ?: "")
        }
    }
    return builder.toString()
}