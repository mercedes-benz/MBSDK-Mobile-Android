package com.daimler.mbingresskit.persistence.model

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

internal open class RealmUser : RealmObject() {

    @PrimaryKey
    var ciamId: String = ""

    var userId: String? = null
    var firstName: String? = null
    var lastName: String? = null
    var birthday: String? = null
    var email: String? = null
    var mobilePhone: String? = null
    var landlinePhone: String? = null
    var countryCode: String? = null
    var languageCode: String? = null
    var createdAt: String? = null
    var updatedAt: String? = null
    var pinStatus: Int? = null
    var address: RealmUserAddress? = null
    var communicationPreference: RealmCommunicationPreferences? = null
    var unitPreferences: RealmUserUnitPreferences? = null
    var accountIdentifier: Int? = null
    var title: String? = null
    var salutationCode: String? = null
    var taxNumber: String? = null
    var imageBytes: ByteArray? = null
    var accountVerified: Boolean? = null
    var isEmailVerified: Boolean? = null
    var isMobileVerified: Boolean? = null
    var adaptionValues: RealmUserAdaptionValues? = null

    companion object {
        const val FIELD_ID = "ciamId"
    }
}
