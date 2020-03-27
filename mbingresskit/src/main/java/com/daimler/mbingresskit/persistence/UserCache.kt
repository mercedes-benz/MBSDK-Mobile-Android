package com.daimler.mbingresskit.persistence

import com.daimler.mbingresskit.common.User

/**
 * Cache implementation for the [User] object.
 */
interface UserCache {

    /**
     * Creates or updates the given user in the cache.
     */
    fun createOrUpdateUser(user: User)

    /**
     * Updates the current user in the cache if there is one.
     *
     * @param action the changes to be executed on the current cached user; this is
     * only called if there is a user available in the cache
     */
    fun updateUser(action: (User) -> User)

    /**
     * Returns the current cached user or null if there is none cached.
     */
    fun loadUser(): User?

    /**
     * Updates the user's image in the cache.
     */
    fun updateUserImage(imageBytes: ByteArray?)

    /**
     * Returns the bytes of the user's image or null if none are persisted.
     */
    fun loadUserImage(): ByteArray?

    /**
     * Clears the whole user cache. It will be empty afterwards.
     */
    fun clear()
}
