package com.daimler.mbmobilesdk.tracking

import com.daimler.mbcarkit.business.model.command.CommandState
import com.daimler.mbcarkit.tracking.MyCarEvent
import com.daimler.mbcarkit.tracking.MyCarTrackingModel
import java.util.concurrent.TimeUnit

internal class MyCarTrackingMemoryParametersHandler : MyCarTrackingParametersHandler {

    private val commandParameters = mutableMapOf<String, MyCarCommandTrackingParameters>()

    override fun prepareTrackingParameters(event: MyCarEvent, model: MyCarTrackingModel): MyCarCommandTrackingParameters {
        val key = event::class.java.simpleName
        val data = commandParameters[key]
        return when {
            data == null -> {
                // Start
                MyCarCommandTrackingParameters(System.currentTimeMillis(), 0).apply {
                    commandParameters[key] = this
                }
            }
            model.state == CommandState.FINISHED -> {
                // Stop
                commandParameters.remove(key)
                data.applyDuration()
            }
            else -> data.applyDuration()
        }
    }

    private fun MyCarCommandTrackingParameters.applyDuration(): MyCarCommandTrackingParameters {
        val durationSeconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - startTimeMillis)
        return copy(durationSeconds = durationSeconds)
    }
}