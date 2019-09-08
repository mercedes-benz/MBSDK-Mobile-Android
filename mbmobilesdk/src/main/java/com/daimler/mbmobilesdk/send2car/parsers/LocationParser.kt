package com.daimler.mbmobilesdk.send2car.parsers

import android.content.Intent
import android.location.Location
import com.daimler.mbnetworkkit.task.FutureTask

internal interface LocationParser {

    fun fromIntent(intent: Intent): FutureTask<Location, LocationExtractorError>

    sealed class LocationExtractorError(val e: Exception) {
        class IntentParseError(e: Exception) : LocationExtractorError(e)
        class GeocoderError(e: Exception) : LocationExtractorError(e)
        class NetworkError(e: Exception) : LocationExtractorError(e)
    }
}