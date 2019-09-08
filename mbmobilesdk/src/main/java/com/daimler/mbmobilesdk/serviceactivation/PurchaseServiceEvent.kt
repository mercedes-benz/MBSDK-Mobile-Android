package com.daimler.mbmobilesdk.serviceactivation

import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbuikit.eventbus.Event

data class PurchaseServiceEvent(val service: Service) : Event