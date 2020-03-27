package com.daimler.mbingresskit.persistence

import com.daimler.mbingresskit.persistence.model.RealmCommunicationPreferences
import com.daimler.mbingresskit.persistence.model.RealmCountry
import com.daimler.mbingresskit.persistence.model.RealmCountryLocale
import com.daimler.mbingresskit.persistence.model.RealmUser
import com.daimler.mbingresskit.persistence.model.RealmUserAdaptionValues
import com.daimler.mbingresskit.persistence.model.RealmUserAddress
import com.daimler.mbingresskit.persistence.model.RealmUserUnitPreferences
import io.realm.annotations.RealmModule

@RealmModule(
    library = true,
    classes = [
        RealmCommunicationPreferences::class,
        RealmUserAddress::class,
        RealmUserAdaptionValues::class,
        RealmUserUnitPreferences::class,
        RealmUser::class,
        RealmCountryLocale::class,
        RealmCountry::class,
    ]
)
class MBIngressRealmModule
