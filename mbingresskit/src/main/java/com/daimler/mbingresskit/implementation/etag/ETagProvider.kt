package com.daimler.mbingresskit.implementation.etag

internal interface ETagProvider {

    fun get(key: String): String?

    fun set(key: String, value: String?)

    fun clear(key: String)

    fun clearAll()
}

internal var ETagProvider.profilePicture: String?
    get() = get(ETags.PROFILE_PICTURE)
    set(value) {
        set(ETags.PROFILE_PICTURE, value)
    }
