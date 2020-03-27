package com.daimler.mbingresskit.implementation.network.ropc.nonce

interface NonceProvider {

    fun get(key: String): String?

    fun set(key: String, value: String?)

    fun clear(key: String)

    fun clearAll()
}

internal object Nonces {
    const val NONCE_LOGIN = "nonce.user.login"
}

internal var NonceProvider.loginNonce: String?
    get() = get(Nonces.NONCE_LOGIN)
    set(value) {
        set(Nonces.NONCE_LOGIN, value)
    }
