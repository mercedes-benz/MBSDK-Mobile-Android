package com.daimler.mbcommonkit.tracking

interface TrackingProvider {
    fun report(event: TrackingEvent)
}
