package com.daimler.mbnetworkkit.header

import java.util.UUID

internal class UUIDTrackingIdProvider : TrackingIdProvider {

    override fun newTrackingId(): String = UUID.randomUUID().toString()
}
