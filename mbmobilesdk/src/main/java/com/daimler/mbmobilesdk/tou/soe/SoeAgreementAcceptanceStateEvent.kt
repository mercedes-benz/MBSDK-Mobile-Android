package com.daimler.mbmobilesdk.tou.soe

import com.daimler.mbuikit.eventbus.Event

internal data class SoeAgreementAcceptanceStateEvent(val ids: List<String>, val accepted: Boolean) : Event