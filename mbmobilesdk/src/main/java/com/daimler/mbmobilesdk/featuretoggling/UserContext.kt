package com.daimler.mbmobilesdk.featuretoggling

/**
 * Data class that is used for feature toggling purposes.
 *
 * @param id the unique user id
 * @param mail the mail of the user
 * @param countryCode the account country code of the user
 */
data class UserContext(
    val id: String,
    val mail: String? = null,
    val countryCode: String? = null,
    val firstName: String? = null,
    val lastName: String? = null
)