package com.daimler.mbmobilesdk.send2car

import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Looper
import com.daimler.mbmobilesdk.send2car.parsers.GeocoderIntentLocationParser
import com.daimler.mbmobilesdk.send2car.parsers.LocationParser
import com.daimler.mbmobilesdk.send2car.parsers.UrlIntentLocationParser
import com.daimler.mbmobilesdk.send2car.urlextractors.GenericUrlLocationExtractor
import com.daimler.mbmobilesdk.send2car.urlextractors.GoogleMapsUrlLocationExtractor
import com.daimler.mbmobilesdk.send2car.urlextractors.WazeUrlLocationExtractor
import com.daimler.mbmobilesdk.send2car.urlextractors.YandexUrlLocationExtractor
import com.daimler.mbmobilesdk.utils.post
import com.daimler.mbnetworkkit.task.FutureTask

internal class IntentLocationParser(context: Context) {

    private val concreteParsers = listOf(
        UrlIntentLocationParser(
            listOf(GoogleMapsUrlLocationExtractor,
                WazeUrlLocationExtractor,
                YandexUrlLocationExtractor,
                GenericUrlLocationExtractor)
        ),
        GeocoderIntentLocationParser(context)
    )

    fun getFromIntent(intent: Intent): FutureTask<Location, LocationParser.LocationExtractorError> {
        val resultTask = CustomTaskObject<Location, LocationParser.LocationExtractorError>()

        val taskQueue = concreteParsers.toMutableList()

        fun runNextOrTerminate() {
            if (taskQueue.isEmpty()) {
                resultTask.fail(LocationParser.LocationExtractorError.IntentParseError(
                    RuntimeException("Can not found suitable parser for intent"))
                )
                return
            }

            val extractor = taskQueue.removeAt(0)
            val extractorTask = extractor.fromIntent(intent)
            extractorTask
                .onComplete {
                    post({ resultTask.complete(it) }, Looper.getMainLooper())
                }
                .onFailure {
                    when (it) {
                        // unable to parse this intent,
                        // lets try with next extractor
                        is LocationParser.LocationExtractorError.IntentParseError,
                        is LocationParser.LocationExtractorError.GeocoderError -> runNextOrTerminate()
                        // Fail on network errors
                        is LocationParser.LocationExtractorError.NetworkError -> resultTask.fail(it)
                    }
                }
        }

        runNextOrTerminate()
        return resultTask
    }
}