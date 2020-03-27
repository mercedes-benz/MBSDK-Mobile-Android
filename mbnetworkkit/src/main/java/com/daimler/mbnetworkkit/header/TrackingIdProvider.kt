package com.daimler.mbnetworkkit.header

internal interface TrackingIdProvider {

    fun newTrackingId(): String
}
