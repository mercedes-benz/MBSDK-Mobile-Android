package com.daimler.mbrealmkit

import io.realm.RealmMigration

internal data class SchemaConfig(
    val schemaVersion: Long,
    val dbName: String,
    val module: Any,
    val migration: RealmMigration? = null,
    val encrypt: Boolean = false
)
