package com.daimler.mbcarkit.persistance

import com.daimler.mbcarkit.business.SendToCarCache
import com.daimler.mbcarkit.business.model.sendtocar.SendToCarCapabilities
import com.daimler.mbcarkit.persistance.model.RealmSendToCarCapability
import com.daimler.mbcarkit.persistance.model.toSendToCarCapabilities
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.createObject
import io.realm.kotlin.delete
import io.realm.kotlin.where

internal class RealmSendToCarCache(private val realm: Realm) : SendToCarCache {

    override fun loadCapabilities(finOrVin: String): SendToCarCapabilities? =
        realmCapabilities(realm, finOrVin)?.toSendToCarCapabilities()

    override fun saveCapabilities(finOrVin: String, capabilities: SendToCarCapabilities) {
        realm.executeTransaction { r ->
            realmCapabilities(r, finOrVin)?.deleteFromRealm()
            r.createObject<RealmSendToCarCapability>(finOrVin).apply {
                this.capabilities = RealmList<Int>().apply { addAll(capabilities.capabilities.map { it.ordinal }) }
                this.preconditions = RealmList<Int>().apply { addAll(capabilities.preconditions.map { it.ordinal }) }

                r.copyToRealmOrUpdate(this)
            }
        }
    }

    override fun deleteCapabilities(finOrVin: String) =
        realm.executeTransaction {
            realmCapabilities(it, finOrVin)?.deleteFromRealm()
        }

    override fun deleteAllCapabilities() =
        realm.executeTransaction {
            it.delete<RealmSendToCarCapability>()
        }

    private fun realmCapabilities(realm: Realm, finOrVin: String) =
        realm.where<RealmSendToCarCapability>()
            .equalTo(RealmSendToCarCapability.FIELD_VIN, finOrVin)
            .findFirst()
}
