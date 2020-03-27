package com.daimler.mbcarkit.business.model.sendtocar

data class SendToCarCapabilities(
    /**
     * List of available send to car modes.
     */
    val capabilities: List<SendToCarCapability>,
    /**
     * These preconditions are returned if something needs to be done before a user can use send to car.
     */
    val preconditions: List<SendToCarPrecondition>
)

fun SendToCarCapabilities.hasUnmetPreconditions() = preconditions.isNotEmpty()
