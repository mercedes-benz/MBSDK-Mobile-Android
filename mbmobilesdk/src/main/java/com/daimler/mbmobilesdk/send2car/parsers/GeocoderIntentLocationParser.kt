package com.daimler.mbmobilesdk.send2car.parsers

import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import com.daimler.mbmobilesdk.send2car.CustomTaskObject
import com.daimler.mbnetworkkit.task.FutureTask

internal open class GeocoderIntentLocationParser(context: Context) : LocationParser {

    private val geocoder = Geocoder(context)

    override fun fromIntent(intent: Intent): FutureTask<Location, LocationParser.LocationExtractorError> {
        val task = CustomTaskObject<Location, LocationParser.LocationExtractorError>()

        try {
            val intentText = intent.getStringExtra(Intent.EXTRA_TEXT)
            val addresses = geocoder.getFromLocationName(intentText, 1)
            val location = addresses.firstOrNull()?.let { address ->
                Location("").apply {
                    latitude = address.latitude
                    longitude = address.longitude
                }
            }
            if (location != null) {
                task.complete(location)
            } else {
                task.fail(LocationParser.LocationExtractorError.GeocoderError(
                    RuntimeException("Can not extract location data from intent with text \"$intentText\""))
                )
            }
        } catch (e: Exception) {
            task.fail(LocationParser.LocationExtractorError.GeocoderError(e))
        }

        return task
    }
}