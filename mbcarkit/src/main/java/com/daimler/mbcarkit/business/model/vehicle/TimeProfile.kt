package com.daimler.mbcarkit.business.model.vehicle

// Application identifier used for entries created by the user in the app.
internal const val APPLICATION_IDENTIFIER_FOR_NEW_ENTRY = -1

data class TimeProfile(
    // a unique identifier of this time profile entry
    internal val identifier: Int? = null,
    // Hour after midnight range [0, 23]
    var hour: Int,
    // Minute after full hour range [0, 59]
    var minute: Int,
    // Whether this profile entry is active or not
    var active: Boolean,
    // Days for which the above time should be applied
    var days: Set<Day>,

    internal var applicationIdentifier: Int = APPLICATION_IDENTIFIER_FOR_NEW_ENTRY,
    internal var toBeRemoved: Boolean = false

)
