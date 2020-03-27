package com.daimler.mbingresskit.implementation

import android.content.Context
import android.content.SharedPreferences
import com.daimler.mbcommonkit.extensions.getEncryptedSharedPreferences
import com.daimler.mbingresskit.common.AuthenticationState
import com.google.gson.Gson
import org.json.JSONException

internal class PrivateAccountPrefs(
    context: Context,
    keyStoreAlias: String
) : AuthstateRepository {

    companion object {
        private const val KEY_AUTH_STATE = "AuthenticationState"
    }

    private val SETTINGS_NAME = "${context.packageName}_oauth_prefs"

    private val sharedPreferences: SharedPreferences = context.getEncryptedSharedPreferences(keyStoreAlias, SETTINGS_NAME, Context.MODE_PRIVATE)

    private val gson: Gson = Gson()

    override fun saveAuthState(authState: AuthenticationState) {
        clearAuthState()
        sharedPreferences.edit {
            putString(KEY_AUTH_STATE, gson.toJson(authState))
        }
    }

    override fun clearAuthState() {
        sharedPreferences.edit {
            remove(KEY_AUTH_STATE)
        }
    }

    override fun readAuthState(): AuthenticationState {
        val jsonAuthState = sharedPreferences.getString(KEY_AUTH_STATE, "").orEmpty()
        return if (jsonAuthState.isNotEmpty()) try {
            gson.fromJson(jsonAuthState, AuthenticationState::class.java)
        } catch (jsa: JSONException) {
            AuthenticationState()
        } else AuthenticationState()
    }

    // since SSO is not supported here, we do not need these methods
    override fun saveUserLoginId(loginId: String) = Unit
    override fun clearUserLoginId() = Unit
    override fun readUserLoginId(): String? = null
}
