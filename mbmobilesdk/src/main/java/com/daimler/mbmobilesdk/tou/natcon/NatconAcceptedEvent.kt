package com.daimler.mbmobilesdk.tou.natcon

import com.daimler.mbuikit.eventbus.Event

internal data class NatconAcceptedEvent(val id: String, val accepted: Boolean) : Event