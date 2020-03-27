package com.daimler.mbrealmkit

import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbcommonkit.security.Crypto
import com.daimler.mbcommonkit.security.EncryptionAlgorithm
import com.daimler.mbcommonkit.security.RandomStringGenerator
import com.daimler.mbloggerkit.MBLoggerKit
import io.realm.Realm
import io.realm.RealmConfiguration
import java.io.File
import java.nio.charset.Charset

/**
 * Factory Method to create RealmConfiguration
 */
internal object RealmConfigurationFactory {

    private const val REALM_SETTINGS_SUFFIX = ".realmcache.settings"
    private const val SHARED_PREFERENCE_KEY_ALIAS = "realm.settings.key_alias"
    private const val REALM_KEY_ALIAS = ".realmcache.alias"
    /**
     * A string with a length of 64 chars has a related length of 64 bytes which is required
     * by Realm as key for encryption
     */
    private const val KEY_LENGTH = 64

    fun realmConfiguration(
        context: Context,
        realmSchema: SchemaConfig
    ): RealmConfiguration = RealmConfiguration.Builder().apply {
        name(realmSchema.dbName)
        schemaVersion(realmSchema.schemaVersion)
        realmSchema.migration?.let { migration(it) } ?: deleteRealmIfMigrationNeeded()
        if (realmSchema.encrypt) encryptionKey(getKeyForEncryptRealm(context, realmSchema))
        modules(realmSchema.module)
    }.build()

    private fun getKeyForEncryptRealm(
        context: Context,
        realmSchema: SchemaConfig,
        isRecoveryAttempt: Boolean = false
    ): ByteArray {
        val keyAlias = "${context.packageName}$REALM_KEY_ALIAS"
        val preferencesName = "${context.packageName}$REALM_SETTINGS_SUFFIX"
        val sharedPreferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
        val crypto = Crypto()

        // Generate crypto key if it doesn't exist
        if (!crypto.keyExists(keyAlias)) crypto.generateKey(keyAlias)

        val encryptedKey: String
        if (!sharedPreferences.contains(SHARED_PREFERENCE_KEY_ALIAS)) {
            encryptedKey = crypto.encrypt(keyAlias, RandomStringGenerator().generateString(KEY_LENGTH))
            sharedPreferences.edit().putString(SHARED_PREFERENCE_KEY_ALIAS, encryptedKey).apply()
        } else {
            encryptedKey = sharedPreferences.getString(SHARED_PREFERENCE_KEY_ALIAS, "") ?: ""
        }

        return try {
            crypto.decrypt(keyAlias, encryptedKey).toByteArray(Charset.defaultCharset())
        } catch (exception: EncryptionAlgorithm.AlgorithmException) {
            MBLoggerKit.e("${exception.message}; trigger recovery")
            recovery(context, sharedPreferences, realmSchema)
            if (!isRecoveryAttempt) {
                getKeyForEncryptRealm(context, realmSchema, true)
            } else {
                throw UnableToRecoverException("Recovery didn't work out")
            }
        }
    }

    /**
     * Recovery strategy
     * 1) Remove existing table from Realm
     * 2) Remove encryptedKey from SharedPreferences. This results that a new key will be generated
     * 3) Therefore a new realm table with a new encrypted key will be created
     */
    private fun recovery(context: Context, sharedPreferences: SharedPreferences, realmSchema: SchemaConfig) {
        val config = RealmConfiguration.Builder().apply {
            name(realmSchema.dbName)
            schemaVersion(realmSchema.schemaVersion)
        }.build()
        Realm.deleteRealm(config)
        sharedPreferences.edit().remove(SHARED_PREFERENCE_KEY_ALIAS).apply()
        deleteRealmFiles(context, realmSchema.dbName)
    }

    private fun deleteRealmFiles(context: Context, dbName: String) = File(context.filesDir.absolutePath).list()?.filter {
        it.startsWith(dbName, true)
    }?.forEach { realmFileName ->
        File(context.filesDir, realmFileName).takeIf { it.exists() }?.delete()
    }
}
