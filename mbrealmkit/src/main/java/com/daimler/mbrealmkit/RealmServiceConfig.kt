package com.daimler.mbrealmkit

import android.content.Context
import io.realm.Realm
import io.realm.RealmMigration
import io.realm.annotations.RealmModule

class RealmServiceConfig internal constructor(
    context: Context,
    schemaConfig: SchemaConfig
) : RealmProvider {

    private val realm: Realm by lazy {
        Realm.init(context)
        Realm.getInstance(RealmConfigurationFactory.realmConfiguration(context, schemaConfig))
    }

    override fun realmInstance(): Realm = realm

    /**
     * This is used to created a config, required to instantiate a [RealmProvider].
     *
     * @param context the Android Context
     * @param schemaVersion the schema version
     * @param module the realm module, needs to be annotated with [RealmModule]
     */
    class Builder(private val context: Context, private val schemaVersion: Long, private val module: Any) {
        private var encrypt = false
        private var dbName: String? = null
        private var migration: RealmMigration? = null

        /**
         * Use a different name than [DEFAULT_DB_NAME]
         *
         * @param dbName name of the database
         */
        fun useDbName(dbName: String) = apply { this.dbName = dbName }

        /**
         * @param migration a custom migration; the database will be deleted and re-created
         */
        fun migrate(migration: RealmMigration) = apply { this.migration = migration }

        /**
         * Encrypt Realm.
         */
        fun encrypt() = apply { this.encrypt = true }

        fun build() = RealmServiceConfig(
            context,
            SchemaConfig(schemaVersion, dbName ?: defaultDbName(), module, migration, encrypt)
        )

        private fun defaultDbName(): String = if (encrypt) DEFAULT_DB_NAME_ENCRYPTED else DEFAULT_DB_NAME

        private companion object {
            private const val DEFAULT_DB_NAME = "realm_table"
            private const val DEFAULT_DB_NAME_ENCRYPTED = "realm_table_encrypted"
        }
    }
}
