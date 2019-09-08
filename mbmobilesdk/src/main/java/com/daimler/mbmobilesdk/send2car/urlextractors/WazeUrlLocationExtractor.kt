package com.daimler.mbmobilesdk.send2car.urlextractors

import android.location.Location
import com.daimler.mbmobilesdk.send2car.GeoUtils
import okhttp3.HttpUrl

internal object WazeUrlLocationExtractor : UrlLocationExtractor {

    override fun fromUrl(url: HttpUrl): Location? {
        val geoHash = url.queryParameter("h") ?: return null

        return GeoUtils.decodeGeoHash(geoHash)
    }
}