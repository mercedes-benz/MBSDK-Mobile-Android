package com.daimler.mbingresskit.implementation

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import com.daimler.mbcommonkit.extensions.getEncryptedSharedPreferences
import com.daimler.mbingresskit.common.AuthenticationState
import com.daimler.mbingresskit.login.AuthenticationStateTokenState
import com.daimler.mbingresskit.login.TokenState
import com.daimler.mbloggerkit.MBLoggerKit
import com.google.gson.Gson
import org.json.JSONException

internal class SsoAccountPrefs(
    private val context: Context,
    private val sharedUserId: String,
    private val keyStoreAlias: String,
    cryptoProvider: CryptoProvider
) : AuthstateRepository {

    companion object {
        private const val KEY_AUTH_STATE = "AuthenticationState"
        private const val KEY_LOGIN_ID = "LoginId"

        private const val SETTINGS_NAME = "account_prefs"

        private const val CLAIM_CIAM_ID = "ciamid"
    }

    private val gson: Gson = Gson()
    private val crypto = cryptoProvider.crypto

    fun initialize() {
        checkSharedUserIdConfigured(sharedUserId)
        checkInitialization()
    }

    private fun checkSharedUserIdConfigured(sharedUserId: String) {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        if (packageInfo.sharedUserId != sharedUserId) {
            throw SharedUserIdNotSetException(sharedUserId)
        }
    }

    override fun saveAuthState(authState: AuthenticationState) {
        clearAuthState()
        packageInfosWithSharedUserId(sharedUserId).forEach {
            getPreferences(context.createPackageContext(it.packageName, 0)).edit(true) {
                putString(KEY_AUTH_STATE, gson.toJson(authState))
            }
        }
    }

    override fun clearAuthState() {
        packageInfosWithSharedUserId(sharedUserId).forEach {
            getPreferences(context.createPackageContext(it.packageName, 0)).edit(true) {
                remove(KEY_AUTH_STATE)
            }
        }
    }

    override fun readAuthState(): AuthenticationState {
        val jsonAuthState = getPreferences(context).getString(KEY_AUTH_STATE, "").orEmpty()
        return authStateFromJson(jsonAuthState)
    }

    override fun saveUserLoginId(loginId: String) {
        clearUserLoginId()
        MBLoggerKit.d("Save User Login ID=$loginId")
        packageInfosWithSharedUserId(sharedUserId).forEach {
            getPreferences(context.createPackageContext(it.packageName, 0)).edit(true) {
                putString(KEY_LOGIN_ID, loginId)
            }
        }
    }

    override fun clearUserLoginId() {
        MBLoggerKit.d("Clear User Login ID")
        packageInfosWithSharedUserId(sharedUserId).forEach {
            getPreferences(context.createPackageContext(it.packageName, 0)).edit(true) {
                remove(KEY_LOGIN_ID)
            }
        }
    }

    override fun readUserLoginId(): String? {
        // this ID does not relate to any user, just to current login session
        return getPreferences(context).getString(KEY_LOGIN_ID, null)?.also {
            MBLoggerKit.d("User Login ID=$it")
        }
    }

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getEncryptedSharedPreferences(keyStoreAlias, SETTINGS_NAME, Context.MODE_MULTI_PROCESS, crypto)
    }

    private fun packageInfosWithSharedUserId(sharedUserId: String): List<PackageInfo> {
        val rsPackages = context.packageManager.getInstalledPackages(0)
        return rsPackages.filter {
            it.sharedUserId == sharedUserId
        }
    }

    private fun checkInitialization() {
        packageInfosWithSharedUserId(sharedUserId).forEach { pInfo ->
            val preferences =
                getPreferences(context.createPackageContext(pInfo.packageName, 0))
            val rsAuthStateJson = preferences.getString(KEY_AUTH_STATE, "").orEmpty()
            val rsAuthState = authStateFromJson(rsAuthStateJson)
            val tokenState = AuthenticationStateTokenState(rsAuthState).getTokenState()
            if (tokenState !is TokenState.LOGGEDOUT) {
                copyPreferencesFrom(preferences)
                return
            }
        }
    }

    private fun copyPreferencesFrom(preferences: SharedPreferences) {
        val localPreferences = getPreferences(context)
        copyPreferencesValues(
            preferences,
            localPreferences,
            listOf(KEY_AUTH_STATE, KEY_LOGIN_ID)
        )
    }

    private fun copyPreferencesValues(
        from: SharedPreferences,
        to: SharedPreferences,
        keys: List<String>,
        default: String = ""
    ) {
        to.edit(true) {
            keys.forEach {
                val value = from.getString(it, default)
                putString(it, value)
            }
        }
    }

    private fun authStateFromJson(json: String) =
        if (json.isNotEmpty()) try {
            gson.fromJson(json, AuthenticationState::class.java)
        } catch (jsa: JSONException) {
            AuthenticationState()
        } else AuthenticationState()

    inner class SharedUserIdNotSetException(
        expectedId: String
    ) : IllegalArgumentException("To share account between apps, the sharedUserId $expectedId must be configured in applications manifest too")
}
