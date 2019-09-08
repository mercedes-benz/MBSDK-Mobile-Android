package com.daimler.mbmobilesdk.send2car.urlextractors

import android.location.Location
import okhttp3.HttpUrl
import java.util.regex.Pattern

internal object GenericUrlLocationExtractor : UrlLocationExtractor {

    private val LOCATION_PATTERN = Pattern.compile("([\\d.]+),([\\d.]+)")

    override fun fromUrl(url: HttpUrl): Location? {
        val matcher = LOCATION_PATTERN.matcher(url.toDecodedString())
        while (matcher.find()) {
            val latitude = matcher.group(1).toDouble()
            val longitude = matcher.group(2).toDouble()

            if (isValidLatitude(latitude) && isValidLongitude(longitude)) {
                return Location("").apply {
                    this.latitude = latitude
                    this.longitude = longitude
                }
            }
        }
        return null
    }

    private fun isValidLatitude(latitude: Double): Boolean {
        return latitude >= -90 && latitude <= 90
    }

    private fun isValidLongitude(longitude: Double): Boolean {
        return longitude >= -180 && longitude <= 180
    }
}