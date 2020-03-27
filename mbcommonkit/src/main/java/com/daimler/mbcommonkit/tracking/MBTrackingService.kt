package com.daimler.mbcommonkit.tracking

object MBTrackingService {

    var trackingProvider: TrackingProvider? = null

    var trackingEnabled: Boolean = true

    fun trackEvent(event: TrackingEvent) {
        if (trackingEnabled) trackingProvider?.report(event)
    }
}
