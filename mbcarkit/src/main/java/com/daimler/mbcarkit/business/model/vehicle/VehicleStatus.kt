package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.vehicle.unit.ClockHourUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.CombustionConsumptionUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.DisplayUnitCase
import com.daimler.mbcarkit.business.model.vehicle.unit.DistanceUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.ElectricityConsumptionUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.GasConsumptionUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.NoUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.PressureUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.RatioUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.SpeedUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.TemperatureUnit

data class VehicleStatus(
    /**
     * Attribute change as unix timestamp in milliseconds with UTC timezone
     */
    var timestamp: Long,

    /**
     * Vehicle identification number
     */
    val finOrVin: String,

    /**
     * Auxiliary heating status
     */
    val auxheat: Auxheat,

    /**
     * Doors status
     */
    val doors: Doors,

    /**
     * Eco-score status
     */
    val ecoScore: EcoScore,

    /**
     * HeadUnit status
     */
    val hu: HeadUnit,

    /**
     * Vehicle's location
     */
    val location: Location,

    /**
     * Journey related vehicle statistics
     */
    val statistics: Statistics,

    /**
     * Tank status
     */
    val tank: Tank,

    /**
     * Tire status
     */
    val tires: Tires,

    /**
     * Miscellaneous vehicle status details
     */
    val vehicle: VehicleData,

    /**
     * Current vehicle warnings
     */
    val warnings: VehicleWarnings,

    /**
     * Windows status
     */
    val windows: Windows,

    /**
     * Theft alarm status
     */
    val theft: Theft,

    /**
     * Engine status
     */
    val engine: Engine,

    /**
     * Zero emission vehicle status
     */
    val zev: Zev,

    /**
     * Driving modes
     */
    val drivingModes: DrivingModes,

    /**
     * Sequence number
     */
    var sequenceNumber: Int = 0
) {

    companion object {
        fun initialState(finOrVin: String): VehicleStatus {
            return VehicleStatus(
                0,
                finOrVin,
                Auxheat(
                    initialNoUnitVehicleAttribute(ActiveState.INACTIVE),
                    initialNoUnitVehicleAttribute(null),
                    initialNoUnitVehicleAttribute(AuxheatState.INACTIVE),
                    initialClockHourAttribute(0),
                    initialClockHourAttribute(0),
                    initialClockHourAttribute(0),
                    initialNoUnitVehicleAttribute(AuxheatTimeSelectionState.NONE),
                    initialNoUnitVehicleAttribute(AuxheatWarnings())
                ),
                Doors(
                    Door(
                        initialNoUnitVehicleAttribute(OpenStatus.UNKNOWN),
                        initialNoUnitVehicleAttribute(LockStatus.UNKNOWN)
                    ),
                    Door(
                        initialNoUnitVehicleAttribute(OpenStatus.UNKNOWN),
                        initialNoUnitVehicleAttribute(LockStatus.UNKNOWN)
                    ),
                    Door(
                        initialNoUnitVehicleAttribute(OpenStatus.UNKNOWN),
                        initialNoUnitVehicleAttribute(LockStatus.UNKNOWN)
                    ),
                    Door(
                        initialNoUnitVehicleAttribute(OpenStatus.UNKNOWN),
                        initialNoUnitVehicleAttribute(LockStatus.UNKNOWN)
                    ),
                    Door(
                        initialNoUnitVehicleAttribute(OpenStatus.UNKNOWN),
                        initialNoUnitVehicleAttribute(LockStatus.UNKNOWN)
                    ),
                    initialNoUnitVehicleAttribute(DoorOverallStatus.UNKNOWN),
                    initialNoUnitVehicleAttribute(DoorLockOverallStatus.UNKNOWN)
                ),
                EcoScore(
                    initialRatioAttribute(null),
                    initialDistanceAttribute(null),
                    initialRatioAttribute(null),
                    initialRatioAttribute(null),
                    initialRatioAttribute(null)
                ),
                HeadUnit(
                    initialNoUnitVehicleAttribute(ActiveState.ACTIVE),
                    initialNoUnitVehicleAttribute(LanguageState.UNKNOWN),
                    initialNoUnitVehicleAttribute(TemperatureType.CELSIUS),
                    initialNoUnitVehicleAttribute(TimeFormatType.FORMAT_12),
                    initialNoUnitVehicleAttribute(listOf()),
                    initialNoUnitVehicleAttribute(null)
                ),
                Location(
                    initialNoUnitVehicleAttribute(null),
                    initialNoUnitVehicleAttribute(null),
                    initialNoUnitVehicleAttribute(null),
                    initialNoUnitVehicleAttribute(null),
                    initialNoUnitVehicleAttribute(null)
                ),
                Statistics(
                    ResetStart<Double, SpeedUnit>(
                        initialSpeedAttribute(null),
                        initialSpeedAttribute(null)
                    ),
                    ZeResetStart<Double, DistanceUnit>(
                        initialDistanceAttribute(null),
                        initialDistanceAttribute(null),
                        ResetStart<Int, DistanceUnit>(
                            initialDistanceAttribute(null),
                            initialDistanceAttribute(null)
                        )
                    ),
                    ZeResetStart<Int, NoUnit>(
                        initialNoUnitVehicleAttribute(null),
                        initialNoUnitVehicleAttribute(null),
                        ResetStart<Int, NoUnit>(
                            initialNoUnitVehicleAttribute(null),
                            initialNoUnitVehicleAttribute(null)
                        )
                    ),
                    TankStatistics(
                        ResetStart<Double, ElectricityConsumptionUnit>(
                            initialElectricityConsumptionAttribute(null),
                            initialElectricityConsumptionAttribute(null)
                        ),
                        ResetStart<Double, DistanceUnit>(
                            initialDistanceAttribute(null),
                            initialDistanceAttribute(null)
                        )
                    ),
                    TankStatistics(
                        ResetStart<Double, GasConsumptionUnit>(
                            initialGasConsumptionAttribute(null),
                            initialGasConsumptionAttribute(null)
                        ),
                        ResetStart<Double, DistanceUnit>(
                            initialDistanceAttribute(null),
                            initialDistanceAttribute(null)
                        )
                    ),
                    TankStatistics(
                        ResetStart<Double, CombustionConsumptionUnit>(
                            initialCombustionConsumptionAttribute(null),
                            initialCombustionConsumptionAttribute(null)
                        ),
                        ResetStart<Double, DistanceUnit>(
                            initialDistanceAttribute(null),
                            initialDistanceAttribute(null)
                        )
                    )
                ),
                Tank(
                    initialRatioAttribute(null),
                    initialRatioAttribute(null),
                    initialDistanceAttribute(null),
                    initialRatioAttribute(null),
                    initialDistanceAttribute(null),
                    initialRatioAttribute(null),
                    initialDistanceAttribute(null),
                    initialDistanceAttribute(null)
                ),
                Tires(
                    Tire(
                        initialPressureAttribute(null),
                        initialNoUnitVehicleAttribute(TireMarkerState.UNKNOWN)
                    ),

                    Tire(
                        initialPressureAttribute(null),
                        initialNoUnitVehicleAttribute(TireMarkerState.UNKNOWN)
                    ),

                    Tire(
                        initialPressureAttribute(null),
                        initialNoUnitVehicleAttribute(TireMarkerState.UNKNOWN)
                    ),

                    Tire(
                        initialPressureAttribute(null),
                        initialNoUnitVehicleAttribute(TireMarkerState.UNKNOWN)
                    ),
                    initialNoUnitVehicleAttribute(null),
                    initialNoUnitVehicleAttribute(SensorState.UNKNOWN),
                    initialNoUnitVehicleAttribute(TireSrdkState.UNKNOWN)
                ),
                VehicleData(
                    initialNoUnitVehicleAttribute(BatteryState.UNKNOWN),
                    initialNoUnitVehicleAttribute(ActiveState.UNKNOWN),
                    initialNoUnitVehicleAttribute(OpenStatus.UNKNOWN),
                    initialNoUnitVehicleAttribute(FilterParticleState.UNKNOWN),
                    initialDistanceAttribute(null),
                    initialNoUnitVehicleAttribute(LockStatus.UNKNOWN),
                    initialNoUnitVehicleAttribute(DoorLockState.UNKNOWN),
                    initialNoUnitVehicleAttribute(ActiveState.UNKNOWN),
                    initialNoUnitVehicleAttribute(RooftopState.UNKNOWN),
                    initialNoUnitVehicleAttribute(null),
                    initialDistanceAttribute(null),
                    initialRatioAttribute(null),
                    initialSpeedAttribute(emptyList()),
                    initialNoUnitVehicleAttribute(SpeedUnitType.UNKNOWN),
                    initialNoUnitVehicleAttribute(null)
                ),
                VehicleWarnings(
                    initialNoUnitVehicleAttribute(OnOffState.UNKNOWN),
                    initialNoUnitVehicleAttribute(OnOffState.UNKNOWN),
                    initialNoUnitVehicleAttribute(OnOffState.UNKNOWN),
                    initialNoUnitVehicleAttribute(OnOffState.UNKNOWN),
                    initialNoUnitVehicleAttribute(OnOffState.UNKNOWN),
                    initialNoUnitVehicleAttribute(OnOffState.UNKNOWN),
                    initialNoUnitVehicleAttribute(OnOffState.UNKNOWN),
                    initialNoUnitVehicleAttribute(TireLampState.UNKNOWN),
                    initialNoUnitVehicleAttribute(TireLevelPrwState.NO_WARNING),
                    initialNoUnitVehicleAttribute(OnOffState.UNKNOWN),
                    initialNoUnitVehicleAttribute(OnOffState.UNKNOWN)
                ),
                Windows(
                    initialNoUnitVehicleAttribute(WindowState.UNKNOWN),
                    initialNoUnitVehicleAttribute(WindowState.UNKNOWN),
                    initialNoUnitVehicleAttribute(WindowState.UNKNOWN),
                    initialNoUnitVehicleAttribute(WindowState.UNKNOWN),
                    Sunroof(
                        initialNoUnitVehicleAttribute(SunroofEventState.UNKNOWN),
                        initialNoUnitVehicleAttribute(ActiveState.UNKNOWN),
                        initialNoUnitVehicleAttribute(SunroofState.UNKNOWN),
                        initialNoUnitVehicleAttribute(SunroofBlindState.UNKNOWN),
                        initialNoUnitVehicleAttribute(SunroofBlindState.UNKNOWN)
                    ),
                    initialNoUnitVehicleAttribute(WindowsOverallStatus.UNKNOWN),
                    initialNoUnitVehicleAttribute(OpenStatus.UNKNOWN),
                    initialNoUnitVehicleAttribute(WindowBlindState.UNKNOWN),
                    initialNoUnitVehicleAttribute(WindowBlindState.UNKNOWN),
                    initialNoUnitVehicleAttribute(WindowBlindState.UNKNOWN)
                ),
                Theft(
                    initialNoUnitVehicleAttribute(ActiveSelectionState.UNKNOWN),
                    initialNoUnitVehicleAttribute(ActiveState.UNKNOWN),
                    initialNoUnitVehicleAttribute(ArmedState.UNKNOWN),
                    initialNoUnitVehicleAttribute(ActiveSelectionState.UNKNOWN),
                    initialNoUnitVehicleAttribute(null),
                    initialNoUnitVehicleAttribute(null),
                    initialNoUnitVehicleAttribute(null),
                    Collision(
                        initialClockHourAttribute(0),
                        initialNoUnitVehicleAttribute(LastParkEventState.UNKNOWN),
                        initialNoUnitVehicleAttribute(ParkEventLevel.UNKNOWN),
                        initialNoUnitVehicleAttribute(ParkEventType.UNKNOWN),
                        initialNoUnitVehicleAttribute(ActiveSelectionState.UNKNOWN)
                    )
                ),
                Engine(
                    initialNoUnitVehicleAttribute(IgnitionState.UNKNOWN),
                    initialNoUnitVehicleAttribute(RunningState.UNKNOWN),
                    initialNoUnitVehicleAttribute(ActiveState.UNKNOWN),
                    initialTemperatureAttribute(null),
                    initialNoUnitVehicleAttribute(null)
                ),
                Zev(
                    initialNoUnitVehicleAttribute(ActiveState.UNKNOWN),
                    initialNoUnitVehicleAttribute(ChargingError.UNKNOWN),
                    initialNoUnitVehicleAttribute(ChargingMode.UNKNOWN),
                    initialNoUnitVehicleAttribute(ChargingStatus.UNKNOWN),
                    initialNoUnitVehicleAttribute(HybridWarningState.UNKNOWN),
                    initialNoUnitVehicleAttribute(ActiveState.UNKNOWN),
                    initialRatioAttribute(null),
                    initialRatioAttribute(null),
                    initialNoUnitVehicleAttribute(ChargingProgram.UNKNOWN),
                    initialNoUnitVehicleAttribute(SmartCharging.UNKNOWN),
                    initialNoUnitVehicleAttribute(SmartChargingDeparture.UNKNOWN),
                    initialNoUnitVehicleAttribute(SmartChargingDeparture.UNKNOWN),
                    ZevTemperature(
                        initialTemperatureAttribute(null),
                        initialTemperatureAttribute(null),
                        initialTemperatureAttribute(null),
                        initialTemperatureAttribute(null),
                        initialTemperatureAttribute(null),
                        initialTemperatureAttribute(null),
                        initialTemperatureAttribute(null)
                    ),
                    initialNoUnitVehicleAttribute(null),
                    initialNoUnitVehicleAttribute(null),
                    initialNoUnitVehicleAttribute(null),
                    initialClockHourAttribute(null),
                    initialNoUnitVehicleAttribute(DepartureTimeMode.DISABLED),
                    initialRatioAttribute(null),
                    initialNoUnitVehicleAttribute(Day.UNKNOWN),
                    initialClockHourAttribute(null),
                    initialClockHourAttribute(null),
                    initialNoUnitVehicleAttribute(Day.UNKNOWN),
                    initialDistanceAttribute(null),
                    initialNoUnitVehicleAttribute(ActiveState.UNKNOWN),
                    initialNoUnitVehicleAttribute(ActiveState.UNKNOWN),
                    initialNoUnitVehicleAttribute(DisabledState.UNKNOWN),
                    initialNoUnitVehicleAttribute(null),
                    initialNoUnitVehicleAttribute(PrecondErrorState.UNKNOWN),
                    initialNoUnitVehicleAttribute(ActiveState.UNKNOWN),
                    initialNoUnitVehicleAttribute(PrecondErrorState.UNKNOWN),
                    initialNoUnitVehicleAttribute(OnOffState.UNKNOWN),
                    initialNoUnitVehicleAttribute(OnOffState.UNKNOWN),
                    initialNoUnitVehicleAttribute(OnOffState.UNKNOWN),
                    initialNoUnitVehicleAttribute(OnOffState.UNKNOWN),
                    initialNoUnitVehicleAttribute(null),
                    initialNoUnitVehicleAttribute(null),
                    initialNoUnitVehicleAttribute(OnOffState.UNKNOWN),
                    initialRatioAttribute(null),
                    initialNoUnitVehicleAttribute(AcChargingCurrentLimitation.UNKNOWN),
                    initialNoUnitVehicleAttribute(ChargingErrorInfrastructure.UNKNOWN),
                    initialNoUnitVehicleAttribute(ChargingTimeType.UNKNOWN),
                    initialRatioAttribute(null),
                    initialClockHourAttribute(null),
                    initialNoUnitVehicleAttribute(Day.UNKNOWN),
                    initialNoUnitVehicleAttribute(DepartureTimeIcon.UNKNOWN),
                    initialNoUnitVehicleAttribute(ChargingErrorWim.UNKNOWN),
                    initialRatioAttribute(null),
                    initialRatioAttribute(null),
                    initialNoUnitVehicleAttribute(null),
                    initialNoUnitVehicleAttribute(ChargeFlapStatus.UNKNOWN),
                    initialNoUnitVehicleAttribute(ChargeFlapStatus.UNKNOWN),
                    initialNoUnitVehicleAttribute(ChargeCouplerLockStatus.UNKNOWN),
                    initialNoUnitVehicleAttribute(ChargeCouplerLockStatus.UNKNOWN),
                    initialNoUnitVehicleAttribute(ChargeCouplerStatus.UNKNOWN),
                    initialNoUnitVehicleAttribute(ChargeCouplerStatus.UNKNOWN),
                    initialNoUnitVehicleAttribute(null),
                    initialNoUnitVehicleAttribute(null),
                    initialNoUnitVehicleAttribute(null),
                    initialNoUnitVehicleAttribute(null)
                ),
                DrivingModes(
                    initialNoUnitVehicleAttribute(DrivingModeState.UNKNOWN),
                    initialNoUnitVehicleAttribute(DrivingModeState.UNKNOWN)
                )
            )
        }

        fun <T> initialNoUnitVehicleAttribute(value: T?): VehicleAttribute<T, NoUnit> {
            return VehicleAttribute(StatusEnum.INVALID, null, value, null)
        }

        fun <T> initialRatioAttribute(value: T?): VehicleAttribute<T, RatioUnit> {
            return VehicleAttribute(StatusEnum.INVALID, null, value, initialRatioUnit())
        }

        fun <T> initialClockHourAttribute(value: T?): VehicleAttribute<T, ClockHourUnit> {
            return VehicleAttribute(StatusEnum.INVALID, null, value, initialClockHourUnit())
        }

        fun <T> initialSpeedAttribute(value: T?): VehicleAttribute<T, SpeedUnit> {
            return VehicleAttribute(StatusEnum.INVALID, null, value, initialSpeedUnit())
        }

        fun <T> initialDistanceAttribute(value: T?): VehicleAttribute<T, DistanceUnit> {
            return VehicleAttribute(StatusEnum.INVALID, null, value, initialDistanceUnit())
        }

        fun <T> initialElectricityConsumptionAttribute(value: T?): VehicleAttribute<T, ElectricityConsumptionUnit> {
            return VehicleAttribute(
                StatusEnum.INVALID,
                null,
                value,
                initialElectricityConsumptionUnit()
            )
        }

        fun <T> initialGasConsumptionAttribute(value: T?): VehicleAttribute<T, GasConsumptionUnit> {
            return VehicleAttribute(StatusEnum.INVALID, null, value, initialGasConsumptionUnit())
        }

        fun <T> initialCombustionConsumptionAttribute(value: T?): VehicleAttribute<T, CombustionConsumptionUnit> {
            return VehicleAttribute(
                StatusEnum.INVALID,
                null,
                value,
                initialCombustionConsumptionUnit()
            )
        }

        fun <T> initialPressureAttribute(value: T?): VehicleAttribute<T, PressureUnit> {
            return VehicleAttribute(StatusEnum.INVALID, null, value, initialPressureUnit())
        }

        fun <T> initialTemperatureAttribute(value: T?): VehicleAttribute<T, TemperatureUnit> {
            return VehicleAttribute(StatusEnum.INVALID, null, value, initialTemperatureUnit())
        }

        fun initialRatioUnit(): VehicleAttribute.Unit<RatioUnit> {
            return VehicleAttribute.Unit(
                "",
                DisplayUnitCase.RATIO_UNIT,
                RatioUnit.UNSPECIFIED_RATIO_UNIT
            )
        }

        fun initialClockHourUnit(): VehicleAttribute.Unit<ClockHourUnit> {
            return VehicleAttribute.Unit(
                "",
                DisplayUnitCase.CLOCK_HOUR_UNIT,
                ClockHourUnit.UNSPECIFIED_CLOCK_HOUR_UNIT
            )
        }

        fun initialSpeedUnit(): VehicleAttribute.Unit<SpeedUnit> {
            return VehicleAttribute.Unit(
                "",
                DisplayUnitCase.SPEED_UNIT,
                SpeedUnit.UNSPECIFIED_SPEED_UNIT
            )
        }

        fun initialDistanceUnit(): VehicleAttribute.Unit<DistanceUnit> {
            return VehicleAttribute.Unit(
                "",
                DisplayUnitCase.DISTANCE_UNIT,
                DistanceUnit.UNSPECIFIED_DISTANCE_UNIT
            )
        }

        fun initialGasConsumptionUnit(): VehicleAttribute.Unit<GasConsumptionUnit> {
            return VehicleAttribute.Unit(
                "",
                DisplayUnitCase.GAS_CONSUMPTION_UNIT,
                GasConsumptionUnit.UNSPECIFIED_GAS_CONSUMPTION_UNIT
            )
        }

        fun initialElectricityConsumptionUnit(): VehicleAttribute.Unit<ElectricityConsumptionUnit> {
            return VehicleAttribute.Unit(
                "",
                DisplayUnitCase.ELECTRICITY_CONSUMPTION_UNIT,
                ElectricityConsumptionUnit.UNSPECIFIED_ELECTRICITY_CONSUMPTION_UNIT
            )
        }

        fun initialCombustionConsumptionUnit(): VehicleAttribute.Unit<CombustionConsumptionUnit> {
            return VehicleAttribute.Unit(
                "",
                DisplayUnitCase.COMBUSTION_CONSUMPTION_UNIT,
                CombustionConsumptionUnit.UNSPECIFIED_COMBUSTION_CONSUMPTION_UNIT
            )
        }

        fun initialPressureUnit(): VehicleAttribute.Unit<PressureUnit> {
            return VehicleAttribute.Unit(
                "",
                DisplayUnitCase.PRESSURE_UNIT,
                PressureUnit.UNSPECIFIED_PRESSURE_UNIT
            )
        }

        fun initialTemperatureUnit(): VehicleAttribute.Unit<TemperatureUnit> {
            return VehicleAttribute.Unit(
                "",
                DisplayUnitCase.TEMPERATURE_UNIT,
                TemperatureUnit.UNSPECIFIED_TEMPERATURE_UNIT
            )
        }

        fun openStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<OpenStatus, *> {
            var value = OpenStatus.UNKNOWN
            state.value?.let {
                value = OpenStatus.values().getOrElse(it) { OpenStatus.CLOSED }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun lockStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<LockStatus, *> {
            // LockStatus.values().getOrElse(state) { LockStatus.UNKNOWN }
            var value = LockStatus.UNKNOWN
            state.value?.let {
                value = LockStatus.values().getOrElse(it) { LockStatus.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun activeStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<ActiveState, *> {
            var value = ActiveState.UNKNOWN
            state.value?.let {
                value = ActiveState.values().getOrElse(it) { ActiveState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun armedStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<ArmedState, *> {
            var value = ArmedState.UNKNOWN
            state.value?.let {
                value = ArmedState.values().getOrElse(it) { ArmedState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun dayForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<Day, *> {
            var value = Day.UNKNOWN
            state.value?.let {
                value = Day.values().getOrElse(it) { Day.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun doorLockStateOverallForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<DoorLockOverallStatus, *> {
            var value = DoorLockOverallStatus.UNKNOWN
            state.value?.let {
                value =
                    DoorLockOverallStatus.values().getOrElse(it) { DoorLockOverallStatus.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun doorStateOverallForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<DoorOverallStatus, *> {
            var value = DoorOverallStatus.UNKNOWN
            state.value?.let {
                value = DoorOverallStatus.values().getOrElse(it) { DoorOverallStatus.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun drivingModeStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<DrivingModeState, *> {
            var value = DrivingModeState.UNKNOWN
            state.value?.let {
                value = DrivingModeState.values().getOrElse(it) { DrivingModeState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun onOffStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<OnOffState, *> {
            // OnOffState.values().getOrElse(state) { OnOffState.OFF }
            var value = OnOffState.UNKNOWN
            state.value?.let {
                value = OnOffState.values().getOrElse(it) { OnOffState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun parkEventLevelForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<ParkEventLevel, *> {
            var value = ParkEventLevel.UNKNOWN
            state.value?.let {
                value = ParkEventLevel.values().getOrElse(it) { ParkEventLevel.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun parkEventTypeForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<ParkEventType, *> {
            var value = ParkEventType.UNKNOWN
            state.value?.let {
                value = ParkEventType.values().getOrElse(it) { ParkEventType.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun ignitionStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<IgnitionState, *> {
            var value = IgnitionState.UNKNOWN
            state.value?.let {
                value = IgnitionState.byValueOrElse(it) { IgnitionState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun activeSelectionStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<ActiveSelectionState, *> {
            var value = ActiveSelectionState.UNKNOWN
            state.value?.let {
                value = ActiveSelectionState.values().getOrElse(it) { ActiveSelectionState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun lastParkEventStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<LastParkEventState, *> {
            var value = LastParkEventState.UNKNOWN
            state.value?.let {
                value = LastParkEventState.values().getOrElse(it) { LastParkEventState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun sensorStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<SensorState, *> {
            var value = SensorState.UNKNOWN
            state.value?.let {
                value = SensorState.values().getOrElse(it) { SensorState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun chargingErrorForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<ChargingError, *> {
            var value = ChargingError.UNKNOWN
            state.value?.let {
                value = ChargingError.values().getOrElse(it) { ChargingError.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun chargingModeForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<ChargingMode, *> {
            var value = ChargingMode.UNKNOWN
            state.value?.let {
                value = ChargingMode.values().getOrElse(it) { ChargingMode.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun chargingProgramForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<ChargingProgram, *> {
            var value = ChargingProgram.UNKNOWN
            state.value?.let {
                value = ChargingProgram.values().getOrElse(it) { ChargingProgram.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun chargingStatusForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<ChargingStatus, *> {
            var value = ChargingStatus.UNKNOWN
            state.value?.let {
                value = ChargingStatus.values().getOrElse(it) { ChargingStatus.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun hybridWarningStatusForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<HybridWarningState, *> {
            var value = HybridWarningState.UNKNOWN
            state.value?.let {
                value = HybridWarningState.values().getOrElse(it) { HybridWarningState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun smartChargingForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<SmartCharging, *> {
            var value = SmartCharging.UNKNOWN
            state.value?.let {
                value = SmartCharging.values().getOrElse(it) { SmartCharging.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun smartChargingDepartureForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<SmartChargingDeparture, *> {
            var value = SmartChargingDeparture.UNKNOWN
            state.value?.let {
                value =
                    SmartChargingDeparture.values().getOrElse(it) { SmartChargingDeparture.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun tireMarkerStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<TireMarkerState, *> {
            var value = TireMarkerState.UNKNOWN
            state.value?.let {
                value = TireMarkerState.values().getOrElse(it) { TireMarkerState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun vehicleDoorLockstateFromInt(state: VehicleAttribute<Int, *>): VehicleAttribute<DoorLockState, *> {
            var value = DoorLockState.UNKNOWN
            state.value?.let {
                value = DoorLockState.values().getOrElse(it) { DoorLockState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun auxheatStateFromInt(state: VehicleAttribute<Int, *>): VehicleAttribute<AuxheatState, *> {
            var value = AuxheatState.UNKNOWN
            state.value?.let {
                value = AuxheatState.values().getOrElse(it) { AuxheatState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun auxheatTimeSelectionStateFromInt(state: VehicleAttribute<Int, *>): VehicleAttribute<AuxheatTimeSelectionState, *> {
            var value = AuxheatTimeSelectionState.UNKNOWN
            state.value?.let {
                value = AuxheatTimeSelectionState.values()
                    .getOrElse(it) { AuxheatTimeSelectionState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun batteryStateFromInt(state: VehicleAttribute<Int, *>): VehicleAttribute<BatteryState, *> {
            // BatteryState.values().getOrElse(state) { BatteryState.GREEN }
            var value = BatteryState.UNKNOWN
            state.value?.let {
                value = BatteryState.values().getOrElse(it) { BatteryState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun filterParticelStateFromInt(state: VehicleAttribute<Int, *>): VehicleAttribute<FilterParticleState, *> {
            var value = FilterParticleState.UNKNOWN
            state.value?.let {
                value = FilterParticleState.values().getOrElse(it) { FilterParticleState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun languageHuFromInt(state: VehicleAttribute<Int, *>): VehicleAttribute<LanguageState, *> {
            var value = LanguageState.UNKNOWN
            state.value?.let {
                value = LanguageState.values().getOrElse(it) { LanguageState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun keyActivationStateFromInt(state: VehicleAttribute<Int, *>): VehicleAttribute<KeyActivationState, *> {
            var value = KeyActivationState.UNKNOWN
            state.value?.let {
                value = KeyActivationState.values().getOrElse(it) { KeyActivationState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun rooftopStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<RooftopState, *> {
            var value = RooftopState.UNKNOWN
            state.value?.let {
                value = RooftopState.values().getOrElse(it) { RooftopState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun speedUnitForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<SpeedUnitType, *> {
            // SpeedUnitType.values().getOrElse(state) { SpeedUnitType.KM }
            var value = SpeedUnitType.UNKNOWN
            state.value?.let {
                value = SpeedUnitType.values().getOrElse(it) { SpeedUnitType.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun sunroofEventStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<SunroofEventState, *> {
            var value = SunroofEventState.UNKNOWN
            state.value?.let {
                value = SunroofEventState.values().getOrElse(it) { SunroofEventState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun temperatureTypeFromInt(state: VehicleAttribute<Int, *>): VehicleAttribute<TemperatureType, *> {
            var value = TemperatureType.UNKNOWN
            state.value?.let {
                value = TemperatureType.values().getOrElse(it) { TemperatureType.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun timeFormatFromInt(state: VehicleAttribute<Int, *>): VehicleAttribute<TimeFormatType, *> {
            var value = TimeFormatType.UNKNOWN
            state.value?.let {
                value = TimeFormatType.values().getOrElse(it) { TimeFormatType.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun tireLampStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<TireLampState, *> {
            var value = TireLampState.UNKNOWN
            state.value?.let {
                value = TireLampState.values().getOrElse(it) { TireLampState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun tireSrdkStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<TireSrdkState, *> {
            var value = TireSrdkState.UNKNOWN
            state.value?.let {
                value = TireSrdkState.values().getOrElse(it) { TireSrdkState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun tireLevelPrwStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<TireLevelPrwState, *> {
            var value = TireLevelPrwState.NO_WARNING
            state.value?.let {
                value = TireLevelPrwState.values().getOrElse(it) { TireLevelPrwState.NO_WARNING }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun windowStateFromInt(state: VehicleAttribute<Int, *>): VehicleAttribute<WindowState, *> {
            var value = WindowState.UNKNOWN
            state.value?.let {
                value = WindowState.values().getOrElse(it) { WindowState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun sunroofStateFromInt(state: VehicleAttribute<Int, *>): VehicleAttribute<SunroofState, *> {
            var value = SunroofState.UNKNOWN
            state.value?.let {
                value = SunroofState.values().getOrElse(it) { SunroofState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun sunroofBlindStateFromInt(state: VehicleAttribute<Int, *>): VehicleAttribute<SunroofBlindState, *> {
            var value = SunroofBlindState.UNKNOWN
            state.value?.let {
                value = SunroofBlindState.values().getOrElse(it) { SunroofBlindState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun auxheatWarningsFromInt(state: VehicleAttribute<Int, *>): VehicleAttribute<AuxheatWarnings, *> {
            return VehicleAttribute(
                state.status,
                state.lastChanged,
                AuxheatWarnings(
                    state.value
                        ?: AuxheatWarnings.NONE
                ),
                state.unit
            )
        }

        fun windowStateOverallForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<WindowsOverallStatus, *> {
            var value = WindowsOverallStatus.UNKNOWN
            state.value?.let {
                value = WindowsOverallStatus.values().getOrElse(it) { WindowsOverallStatus.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun windowBlindStateFromInt(state: VehicleAttribute<Int, *>): VehicleAttribute<WindowBlindState, *> {
            var value = WindowBlindState.UNKNOWN
            state.value?.let {
                value = WindowBlindState.values().getOrElse(it) { WindowBlindState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun runningStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<RunningState, *> {
            var value = RunningState.UNKNOWN
            state.value?.let {
                value = RunningState.values().getOrElse(it) { RunningState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun departureTimeModeStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<DepartureTimeMode, *> {
            var value = DepartureTimeMode.DISABLED
            state.value?.let {
                value = DepartureTimeMode.values().getOrElse(it) { DepartureTimeMode.DISABLED }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun enableStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<DisabledState, *> {
            var value = DisabledState.UNKNOWN
            state.value?.let {
                value = DisabledState.values().getOrElse(it) { DisabledState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun precondErrorStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<PrecondErrorState, *> {
            var value = PrecondErrorState.UNKNOWN
            state.value?.let {
                value = PrecondErrorState.values().getOrElse(it) { PrecondErrorState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun theftWarningReasonStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<TheftWarningReasonState, *> {
            var value = TheftWarningReasonState.NO_ALARM
            state.value?.let {
                value =
                    TheftWarningReasonState.byValueOrElse(it) { TheftWarningReasonState.NO_ALARM }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun vehicleLocationErrorStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<VehicleLocationErrorState, *> {
            var value = VehicleLocationErrorState.UNKNOWN
            state.value?.let {
                value = VehicleLocationErrorState.values()
                    .getOrElse(it) { VehicleLocationErrorState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun requiredStateForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<RequiredState, *> {
            var value = RequiredState.UNKNOWN
            state.value?.let {
                value = RequiredState.values().getOrElse(it) { RequiredState.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun acChargingCurrentLimitationForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<AcChargingCurrentLimitation, *> {
            var value = AcChargingCurrentLimitation.UNKNOWN
            state.value?.let {
                value = AcChargingCurrentLimitation.values()
                    .getOrElse(it) { AcChargingCurrentLimitation.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun chargingErrorInfrastructureForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<ChargingErrorInfrastructure, *> {
            var value = ChargingErrorInfrastructure.UNKNOWN
            state.value?.let {
                value = ChargingErrorInfrastructure.values()
                    .getOrElse(it) { ChargingErrorInfrastructure.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun chargingTimeTypeForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<ChargingTimeType, *> {
            var value = ChargingTimeType.UNKNOWN
            state.value?.let {
                value = ChargingTimeType.values().getOrElse(it) { ChargingTimeType.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun departureTimeIconForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<DepartureTimeIcon, *> {
            var value = DepartureTimeIcon.UNKNOWN
            state.value?.let {
                value = DepartureTimeIcon.values().getOrElse(it) { DepartureTimeIcon.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun chargingErrorWimForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<ChargingErrorWim, *> {
            var value = ChargingErrorWim.UNKNOWN
            state.value?.let {
                value = ChargingErrorWim.values().getOrElse(it) { ChargingErrorWim.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun chargeFlapStatusForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<ChargeFlapStatus, *> {
            var value = ChargeFlapStatus.UNKNOWN
            state.value?.let {
                value = ChargeFlapStatus.values().getOrElse(it) { ChargeFlapStatus.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun chargeCouplerLockStatusForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<ChargeCouplerLockStatus, *> {
            var value = ChargeCouplerLockStatus.UNKNOWN
            state.value?.let {
                value = ChargeCouplerLockStatus.values()
                    .getOrElse(it) { ChargeCouplerLockStatus.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        fun chargeCouplerStatusForInt(state: VehicleAttribute<Int, *>): VehicleAttribute<ChargeCouplerStatus, *> {
            var value = ChargeCouplerStatus.UNKNOWN
            state.value?.let {
                value = ChargeCouplerStatus.values().getOrElse(it) { ChargeCouplerStatus.UNKNOWN }
            }
            return VehicleAttribute(state.status, state.lastChanged, value, state.unit)
        }

        // unit parsing for enums

        fun displayUnitCaseFromInt(displayUnitCase: Int?) = DisplayUnitCase.values().getOrElse(
            displayUnitCase
                ?: -1
        ) { DisplayUnitCase.DISPLAYUNIT_NOT_SET }

        fun clockHourUnitFromInt(clockHourUnit: Int) =
            ClockHourUnit.values().getOrElse(clockHourUnit) {
                ClockHourUnit.UNSPECIFIED_CLOCK_HOUR_UNIT
            }

        fun combustionConsumptionUnitFromInt(combustionConsumption: Int) =
            CombustionConsumptionUnit.values().getOrElse(combustionConsumption) {
                CombustionConsumptionUnit.UNSPECIFIED_COMBUSTION_CONSUMPTION_UNIT
            }

        fun electricityConsumptionUnitFromInt(electricityConsumption: Int) =
            ElectricityConsumptionUnit.values().getOrElse(electricityConsumption) {
                ElectricityConsumptionUnit.UNSPECIFIED_ELECTRICITY_CONSUMPTION_UNIT
            }

        fun gasConsumptionUnitFromInt(gasConsumption: Int) =
            GasConsumptionUnit.values().getOrElse(gasConsumption) {
                GasConsumptionUnit.UNSPECIFIED_GAS_CONSUMPTION_UNIT
            }

        fun pressureUnitFromInt(pressureUnit: Int) = PressureUnit.values().getOrElse(pressureUnit) {
            PressureUnit.UNSPECIFIED_PRESSURE_UNIT
        }

        fun ratioUnitFromInt(ratioUnit: Int) = RatioUnit.values().getOrElse(ratioUnit) {
            RatioUnit.UNSPECIFIED_RATIO_UNIT
        }

        fun speedUnitFromInt(speedUnit: Int) = SpeedUnit.values().getOrElse(speedUnit) {
            SpeedUnit.UNSPECIFIED_SPEED_UNIT
        }

        fun distanceUnitFromInt(distanceUnit: Int) = DistanceUnit.values().getOrElse(distanceUnit) {
            DistanceUnit.UNSPECIFIED_DISTANCE_UNIT
        }

        fun temperatureUnitFromInt(temperatureUnit: Int) = TemperatureUnit.values().getOrElse(temperatureUnit) {
            TemperatureUnit.UNSPECIFIED_TEMPERATURE_UNIT
        }
    }
}
