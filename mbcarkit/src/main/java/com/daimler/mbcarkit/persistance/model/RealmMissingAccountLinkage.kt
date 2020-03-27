package com.daimler.mbcarkit.persistance.model

import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import com.daimler.mbcarkit.business.model.services.MissingAccountLinkage
import io.realm.RealmObject

internal open class RealmMissingAccountLinkage : RealmObject() {

    var mandatory: Boolean? = null
    var type: Int? = null
}

internal fun RealmMissingAccountLinkage.cascadeDeleteFromRealm() {
    deleteFromRealm()
}

internal fun RealmMissingAccountLinkage.toMissingAccountLinkage() =
    MissingAccountLinkage(
        mandatory == true,
        AccountType.values().getOrNull(type ?: -1)
    )

internal fun RealmMissingAccountLinkage.applyFromMissingAccountLinkage(
    linkage: MissingAccountLinkage
) = apply {
    mandatory = linkage.mandatory
    type = linkage.type?.ordinal
}

internal fun RealmMissingAccountLinkage.isSameAccountType(type: AccountType?) =
    this.type == type?.ordinal
