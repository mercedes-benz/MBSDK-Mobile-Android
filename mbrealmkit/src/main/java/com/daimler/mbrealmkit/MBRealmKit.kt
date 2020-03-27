package com.daimler.mbrealmkit

import com.daimler.mbloggerkit.MBLoggerKit
import io.realm.Realm

/**
 * Service singleton that provides accessors to created realm instances.
 */
object MBRealmKit {

    private val realmProviders = HashMap<String, RealmProvider>()

    /**
     * Creates a new realm instance. The given id must be unique. Will no create instance if it is already created
     */
    fun createRealmInstance(id: String, realmServiceConfig: RealmServiceConfig) {
        if (!realmProviders.containsKey(id)) {
            MBLoggerKit.d("Create provider with id $id.")
            realmProviders[id] = realmServiceConfig
        } else {
            MBLoggerKit.d("Skipped registration of provider $id - $realmServiceConfig because already registered")
        }
    }

    /**
     * Returns realm instance for the given id.
     *
     * @param id the ID of the realm instance used while calling [createRealmInstance]
     *
     * @throws UnableToCreateRealmInstanceException if there is realm instance for the given id
     */
    fun realm(id: String): Realm = realmProviders[id]?.realmInstance()
        ?: throw UnableToCreateRealmInstanceException("Provider with id $id was not created.")
}
