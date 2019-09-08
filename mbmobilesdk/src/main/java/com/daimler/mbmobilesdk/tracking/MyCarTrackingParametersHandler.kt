package com.daimler.mbmobilesdk.tracking

import com.daimler.mbcarkit.tracking.MyCarEvent
import com.daimler.mbcarkit.tracking.MyCarTrackingModel

internal interface MyCarTrackingParametersHandler {

    fun prepareTrackingParameters(event: MyCarEvent, model: MyCarTrackingModel): MyCarCommandTrackingParameters
}