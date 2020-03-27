package com.daimler.mbrealmkit

import io.realm.Realm

internal interface RealmProvider {
    fun realmInstance(): Realm
}
