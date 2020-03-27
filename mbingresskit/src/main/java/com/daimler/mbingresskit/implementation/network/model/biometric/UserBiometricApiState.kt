package com.daimler.mbingresskit.implementation.network.model.biometric

import com.google.gson.annotations.SerializedName

internal enum class UserBiometricApiState {
    @SerializedName("enabled")
    ENABLED,
    @SerializedName("disabled")
    DISABLED;

    companion object {
        private val map: Map<String, UserBiometricApiState> = values().associateBy(UserBiometricApiState::name)

        fun forName(name: String) = map[name] ?: DISABLED
    }
}
