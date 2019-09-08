package com.daimler.mbmobilesdk.serviceactivation

import com.daimler.mbuikit.eventbus.Event

internal data class ServiceToggledEvent(val item: ServiceItem, val isChecked: Boolean) : Event