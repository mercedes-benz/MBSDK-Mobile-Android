package com.daimler.mbmobilesdk.send2car.urlextractors

import android.location.Location
import okhttp3.HttpUrl
import java.util.regex.Pattern

internal object GoogleMapsUrlLocationExtractor : UrlLocationExtractor {

    private val LOCATION_PATTERN = Pattern.compile("!3d([\\d.]+)!4d([\\d.]+)")

    override fun fromUrl(url: HttpUrl): Location? {
        val matcher = LOCATION_PATTERN.matcher(url.toDecodedString())
        return if (matcher.find()) {
            Location("").apply {
                latitude = matcher.group(1).toDouble()
                longitude = matcher.group(2).toDouble()
            }
        } else {
            null
        }
    }
}