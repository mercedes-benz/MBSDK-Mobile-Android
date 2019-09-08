package com.daimler.mbmobilesdk.send2car.parsers

import android.content.Intent
import android.location.Location
import androidx.core.util.PatternsCompat
import com.daimler.mbmobilesdk.send2car.CustomTaskObject
import com.daimler.mbmobilesdk.send2car.urlextractors.UrlLocationExtractor
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject
import okhttp3.*
import java.io.IOException

internal open class UrlIntentLocationParser(
    private val urlExtractors: List<UrlLocationExtractor>
) : LocationParser {

    private val httpClient = OkHttpClient.Builder().build()

    override fun fromIntent(intent: Intent): FutureTask<Location, LocationParser.LocationExtractorError> {
        val mapsShareUrl = extractUrlFromIntent(intent)

        val task = CustomTaskObject<Location, LocationParser.LocationExtractorError>()

        if (mapsShareUrl == null) {
            task.fail(LocationParser.LocationExtractorError.IntentParseError(
                RuntimeException("Can not extract map url from intent extras")
            ))
        } else {
            try {
                val request = Request.Builder().head().url(mapsShareUrl).build()
                httpClient.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        task.fail(
                            LocationParser.LocationExtractorError.NetworkError(e))
                    }

                    override fun onResponse(call: Call, response: Response) {
                        handleHttpHeadResponse(response, task)
                    }
                })
            } catch (e: Exception) {
                task.fail(LocationParser.LocationExtractorError.NetworkError(e))
            }
        }

        return task
    }

    private fun extractUrlFromIntent(intent: Intent): String? {
        val extras = intent.extras.keySet().map { intent.extras.getString(it, "") }
        return extras
            .asSequence()
            .map { PatternsCompat.WEB_URL.matcher(it) }
            .flatMap {
                generateSequence {
                    if (it.find()) {
                        it.group()
                    } else null
                }
            }
            .firstOrNull { it.startsWith("http") }
    }

    protected open fun handleHttpHeadResponse(
        response: Response,
        task: TaskObject<Location, LocationParser.LocationExtractorError>
    ) {
        val url = response.request().url()
        val location = extractLocationFromUrl(url)

        if (location != null) {
            task.complete(location)
        } else {
            task.fail(LocationParser.LocationExtractorError.IntentParseError(
                RuntimeException("Can not extract location data from url $url")
            ))
        }
    }

    protected open fun extractLocationFromUrl(url: HttpUrl): Location? {
        return urlExtractors.asSequence().map { it.fromUrl(url) }.filterNotNull().firstOrNull()
    }
}