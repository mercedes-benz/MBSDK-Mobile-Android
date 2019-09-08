package com.daimler.mbmobilesdk.send2car.urlextractors

import android.location.Location
import okhttp3.HttpUrl
import java.util.regex.Pattern

internal object YandexUrlLocationExtractor : UrlLocationExtractor {

    private val LOCATION_PATTERN = Pattern.compile("([\\d.]+),([\\d.]+)")

    override fun fromUrl(url: HttpUrl): Location? {
        if (!url.host().contains("yandex")) {
            return null
        }

        val matcher = LOCATION_PATTERN.matcher(url.toDecodedString())
        return if (matcher.find()) {
            Location("").apply {
                latitude = matcher.group(2).toDouble()
                longitude = matcher.group(1).toDouble()
            }
        } else {
            val latitude = url.queryParameter("lat")?.toDouble()
            val longitude = url.queryParameter("lon")?.toDouble()
            if (latitude != null && longitude != null) {
                Location("").apply {
                    this.latitude = latitude
                    this.longitude = longitude
                }
            } else {
                null
            }
        }
    }
}