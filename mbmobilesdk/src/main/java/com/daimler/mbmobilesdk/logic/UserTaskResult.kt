package com.daimler.mbmobilesdk.logic

import com.daimler.mbingresskit.common.User

/**
 * Represents the result of a task that loads a valid [User] object.
 *
 * @param user the user object, if loaded successfully
 * @param wasRequested true, if the user object was requested through the network
 */
data class UserTaskResult(val user: User, val wasRequested: Boolean)