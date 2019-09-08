package com.daimler.mbmobilesdk.tracking

import com.daimler.mbcarkit.tracking.MyCarEvent
import com.daimler.mbcarkit.tracking.MyCarTrackingModel
import com.daimler.mbcarkit.tracking.MyCarTrackingService
import com.google.gson.Gson
import com.yandex.metrica.YandexMetrica

internal class MyCarAppMetricaTrackingService(
    private val parametersHandler: MyCarTrackingParametersHandler
) : MyCarTrackingService {

    override val id: String = MyCarAppMetricaTrackingService::class.java.canonicalName.orEmpty()

    override fun track(event: MyCarEvent.DoorLock) {
        val parameters = parametersHandler.prepareTrackingParameters(event, event.model)
        event.model.commandDuration = parameters.durationSeconds
        reportEventForName(EVENT_NAME_DOOR_LOCK, event.model)
    }

    override fun track(event: MyCarEvent.DoorUnlock) {
        val parameters = parametersHandler.prepareTrackingParameters(event, event.model)
        event.model.commandDuration = parameters.durationSeconds
        reportEventForName(EVENT_NAME_DOOR_UNLOCK, event.model)
    }

    override fun track(event: MyCarEvent.StartAuxHeat) {
        val parameters = parametersHandler.prepareTrackingParameters(event, event.model)
        event.model.commandDuration = parameters.durationSeconds
        reportEventForName(EVENT_NAME_START_AUXHEAT, event.model)
    }

    override fun track(event: MyCarEvent.StopAuxHeat) {
        val parameters = parametersHandler.prepareTrackingParameters(event, event.model)
        event.model.commandDuration = parameters.durationSeconds
        reportEventForName(EVENT_NAME_STOP_AUXHEAT, event.model)
    }

    override fun track(event: MyCarEvent.ConfigureAuxHeat) {
        val parameters = parametersHandler.prepareTrackingParameters(event, event.model)
        event.model.commandDuration = parameters.durationSeconds
        reportEventForName(EVENT_NAME_CONFIGURE_AUXHEAT, event.model)
    }

    override fun track(event: MyCarEvent.EngineStart) {
        val parameters = parametersHandler.prepareTrackingParameters(event, event.model)
        event.model.commandDuration = parameters.durationSeconds
        reportEventForName(EVENT_NAME_ENGINE_START, event.model)
    }

    override fun track(event: MyCarEvent.EngineStop) {
        val parameters = parametersHandler.prepareTrackingParameters(event, event.model)
        event.model.commandDuration = parameters.durationSeconds
        reportEventForName(EVENT_NAME_ENGINE_STOP, event.model)
    }

    override fun track(event: MyCarEvent.OpenSunroof) {
        val parameters = parametersHandler.prepareTrackingParameters(event, event.model)
        event.model.commandDuration = parameters.durationSeconds
        reportEventForName(EVENT_NAME_SUNROOF_OPEN, event.model)
    }

    override fun track(event: MyCarEvent.CloseSunroof) {
        val parameters = parametersHandler.prepareTrackingParameters(event, event.model)
        event.model.commandDuration = parameters.durationSeconds
        reportEventForName(EVENT_NAME_SUNROOF_CLOSE, event.model)
    }

    override fun track(event: MyCarEvent.LiftSunroof) {
        val parameters = parametersHandler.prepareTrackingParameters(event, event.model)
        event.model.commandDuration = parameters.durationSeconds
        reportEventForName(EVENT_NAME_SUNROOF_LIFT, event.model)
    }

    override fun track(event: MyCarEvent.OpenWindow) {
        val parameters = parametersHandler.prepareTrackingParameters(event, event.model)
        event.model.commandDuration = parameters.durationSeconds
        reportEventForName(EVENT_NAME_WINDOWS_OPEN, event.model)
    }

    override fun track(event: MyCarEvent.CloseWindow) {
        val parameters = parametersHandler.prepareTrackingParameters(event, event.model)
        event.model.commandDuration = parameters.durationSeconds
        reportEventForName(EVENT_NAME_WINDOWS_CLOSE, event.model)
    }

    override fun track(event: MyCarEvent.SendToCar) {
        val parameters = parametersHandler.prepareTrackingParameters(event, event.model)
        event.model.commandDuration = parameters.durationSeconds
        reportEventForName(EVENT_NAME_SEND_TO_CAR, event.model)
    }

    override fun track(event: MyCarEvent.LocateMe) {
        val parameters = parametersHandler.prepareTrackingParameters(event, event.model)
        event.model.commandDuration = parameters.durationSeconds
        reportEventForName(EVENT_NAME_LOCATE_ME, event.model)
    }

    override fun track(event: MyCarEvent.LocateVehicle) {
        val parameters = parametersHandler.prepareTrackingParameters(event, event.model)
        event.model.commandDuration = parameters.durationSeconds
        reportEventForName(EVENT_NAME_LOCATE_VEHICLE, event.model)
    }

    override fun track(event: MyCarEvent.BatteryCharge) {}

    override fun track(event: MyCarEvent.BatteryMaxSoc) {}

    override fun track(event: MyCarEvent.ChargeOptimizationConfigure) {}

    override fun track(event: MyCarEvent.ChargeOptimizationStart) {}

    override fun track(event: MyCarEvent.ChargeOptimizationStop) {}

    override fun track(event: MyCarEvent.SigposStart) {}

    override fun track(event: MyCarEvent.SpeedAlertStart) {}

    override fun track(event: MyCarEvent.SpeedAlertStop) {}

    override fun track(event: MyCarEvent.TemperatureConfigure) {}

    override fun track(event: MyCarEvent.TheftalarmConfirmDamagedetection) {}

    override fun track(event: MyCarEvent.TheftalarmDeselectDamagedetection) {}

    override fun track(event: MyCarEvent.TheftalarmDeselectInterior) {}

    override fun track(event: MyCarEvent.TheftalarmDeselectTow) {}

    override fun track(event: MyCarEvent.TheftalarmSelectDamagedetection) {}

    override fun track(event: MyCarEvent.TheftalarmSelectInterior) {}

    override fun track(event: MyCarEvent.TheftalarmSelectTow) {}

    override fun track(event: MyCarEvent.TheftalarmStart) {}

    override fun track(event: MyCarEvent.TheftalarmStop) {}

    override fun track(event: MyCarEvent.WeekProfileConfigure) {}

    override fun track(event: MyCarEvent.ZevPreconditioningConfigure) {}

    override fun track(event: MyCarEvent.ZevPreconditioningConfigureSeats) {}

    override fun track(event: MyCarEvent.ZevPreconditioningStart) {}

    override fun track(event: MyCarEvent.ZevPreconditioningStop) {}

    private fun reportEventForName(name: String, model: MyCarTrackingModel) {
        YandexMetrica.reportEvent(name, model.toJson())
    }

    private fun MyCarTrackingModel.toJson() = Gson().toJson(this)

    private companion object {
        const val EVENT_NAME_DOOR_LOCK = "DoorLock"
        const val EVENT_NAME_DOOR_UNLOCK = "DoorUnlock"
        const val EVENT_NAME_START_AUXHEAT = "StartAuxHeat"
        const val EVENT_NAME_STOP_AUXHEAT = "StopAuxHeat"
        const val EVENT_NAME_CONFIGURE_AUXHEAT = "ConfigureAuxHeat"
        const val EVENT_NAME_ENGINE_START = "EngineStart"
        const val EVENT_NAME_ENGINE_STOP = "EngineStop"
        const val EVENT_NAME_SUNROOF_OPEN = "OpenSunroof"
        const val EVENT_NAME_SUNROOF_CLOSE = "CloseSunroof"
        const val EVENT_NAME_SUNROOF_LIFT = "LiftSunroof"
        const val EVENT_NAME_WINDOWS_OPEN = "OpenWindow"
        const val EVENT_NAME_WINDOWS_CLOSE = "CloseWindow"
        const val EVENT_NAME_SEND_TO_CAR = "SendToCar"
        const val EVENT_NAME_LOCATE_ME = "LocateMe"
        const val EVENT_NAME_LOCATE_VEHICLE = "LocateVehicle"
    }
}