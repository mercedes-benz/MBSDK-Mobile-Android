package com.daimler.mbmobilesdk.send2car.urlextractors

import android.location.Location
import okhttp3.HttpUrl
import java.net.URLDecoder

internal interface UrlLocationExtractor {
    fun fromUrl(url: HttpUrl): Location?

    fun HttpUrl.toDecodedString(): String {
        return URLDecoder.decode(toString(), "UTF-8")
    }
}