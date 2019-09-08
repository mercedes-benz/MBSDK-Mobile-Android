package com.daimler.mbmobilesdk.picker

import com.daimler.mbuikit.eventbus.Event

internal data class SearchItemPickedEvent(
    val key: String,
    val value: String,
    val selected: Boolean
) : Event