// THIS FILE IS GENERATED! DO NOT EDIT!
// The generator can be found at https://git.daimler.com/RisingStars/commons-go-lib/tree/master/gen
package com.daimler.mbcarkit.business.model.command

/**
 * This enum lists the set of error codes that are internally possible for every vehicle command.
 */
enum class InternalVehicleCommandError(val errorCodes: Set<String>) {

    /**
     * Accelerator pressed
     */
    AcceleratorPressed(setOf("6819")),

    /**
     * Failed due to afterrun active
     */
    AfterRunActive(setOf("4276")),

    /**
     * Failed due to afterrun active on front left window
     */
    AfterRunActiveFrontLeftWindow(setOf("4277")),

    /**
     * Failed due to afterrun active on front right window
     */
    AfterRunActiveFrontRightWindow(setOf("4278")),

    /**
     * Failed due to afterrun active on front roof roller blind
     */
    AfterRunActiveFrontRoofRollerBlind(setOf("4284")),

    /**
     * Failed due to afterrun active on rear left roller blind
     */
    AfterRunActiveRearLeftRollerBlind(setOf("4281")),

    /**
     * Failed due to afterrun active on rear left window
     */
    AfterRunActiveRearLeftWindow(setOf("4279")),

    /**
     * Failed due to afterrun active on rear right roller blind
     */
    AfterRunActiveRearRightRollerBlind(setOf("4282")),

    /**
     * Failed due to afterrun active on rear right window
     */
    AfterRunActiveRearRightWindow(setOf("4280")),

    /**
     * Failed due to afterrun active on rear roof roller blind
     */
    AfterRunActiveRearRoofRollerBlind(setOf("4285")),

    /**
     * Failed due to afterrun active on sunroof
     */
    AfterRunActiveSunroof(setOf("4283")),

    /**
     * Alarm, panic alarm and/or warning blinker active
     */
    AlarmActive(setOf("6817")),

    /**
     * theft alarm /panic alarm / emergency flashers got triggered
     */
    AlarmTriggered(setOf("6806")),

    /**
     * Failed due to another car steered by system currently
     */
    AnotherCarSteeredCurrently(setOf("3002")),

    /**
     * Failed due to anti-trap protection active
     */
    AntiTrapProtectionActive(setOf("4205")),

    /**
     * Failed due to anti-trap protection active on front left window
     */
    AntiTrapProtectionActiveFrontLeftWindow(setOf("4206")),

    /**
     * Failed due to anti-trap protection active on front right window
     */
    AntiTrapProtectionActiveFrontRightWindow(setOf("4207")),

    /**
     * Failed due to anti-trap protection active on front roof roller blind
     */
    AntiTrapProtectionActiveFrontRoofRollerBlind(setOf("4213")),

    /**
     * Failed due to anti-trap protection active on rear left roller blind
     */
    AntiTrapProtectionActiveRearLeftRollerBlind(setOf("4210")),

    /**
     * Failed due to anti-trap protection active on rear left window
     */
    AntiTrapProtectionActiveRearLeftWindow(setOf("4208")),

    /**
     * Failed due to anti-trap protection active on rear right roller blind
     */
    AntiTrapProtectionActiveRearRightRollerBlind(setOf("4211")),

    /**
     * Failed due to anti-trap protection active on rear right window
     */
    AntiTrapProtectionActiveRearRightWindow(setOf("4209")),

    /**
     * Failed due to anti-trap protection active on rear roof roller blind
     */
    AntiTrapProtectionActiveRearRoofRollerBlind(setOf("4214")),

    /**
     * Failed due to anti-trap protection active on sunroof
     */
    AntiTrapProtectionActiveSunroof(setOf("4212")),

    /**
     * ZEV WeekDeptSet processing failed as incorrect AppId passed
     */
    AppIdIncorrect(setOf("6612")),

    /**
     * ZEV WeekDeptSet processing failed as AsppId is not present
     */
    AppIdMissing(setOf("6611")),

    /**
     * ZEV WeekDeptSet processing failed as AppId not matching
     */
    AppIdNotMatching(setOf("6613")),

    /**
     * Processing of auxheat command failed
     */
    AuxheatCommandFailed(setOf("4061")),

    /**
     * Failed due to brake fluid lamp on during drive
     */
    BrakeFluid(setOf("3052")),

    /**
     * Failed due to manual cancellation inside vehicle
     */
    CancelledManuallyInVehicle(setOf("4227")),

    /**
     * Failed due to manual cancellation inside vehicle on front left window
     */
    CancelledManuallyInVehicleFrontLeftWindow(setOf("4228")),

    /**
     * Failed due to manual cancellation inside vehicle on front right window
     */
    CancelledManuallyInVehicleFrontRightWindow(setOf("4229")),

    /**
     * Failed due to manual cancellation inside vehicle on front roof roller blind
     */
    CancelledManuallyInVehicleFrontRoofRollerBlind(setOf("4235")),

    /**
     * Failed due to manual cancellation inside vehicle on rear left roller blind
     */
    CancelledManuallyInVehicleRearLeftRollerBlind(setOf("4232")),

    /**
     * Failed due to manual cancellation inside vehicle on rear left window
     */
    CancelledManuallyInVehicleRearLeftWindow(setOf("4230")),

    /**
     * Failed due to manual cancellation inside vehicle on rear right roller blind
     */
    CancelledManuallyInVehicleRearRightRollerBlind(setOf("4233")),

    /**
     * Failed due to manual cancellation inside vehicle on rear right window
     */
    CancelledManuallyInVehicleRearRightWindow(setOf("4231")),

    /**
     * Failed due to position not reached within timeout on rear roller blind
     */
    CancelledManuallyInVehicleRearRollerBlind(setOf("4237")),

    /**
     * Failed due to manual cancellation inside vehicle on rear roof roller blind
     */
    CancelledManuallyInVehicleRearRoofRollerBlind(setOf("4236")),

    /**
     * Failed due to manual cancellation inside vehicle on sunroof
     */
    CancelledManuallyInVehicleSunroof(setOf("4234")),

    /**
     * Failed due to not locked car
     */
    CarNotLocked(setOf("2024")),

    /**
     * Failed due to central locking disabled
     */
    CentralLockingDisabled(setOf("4148")),

    /**
     * Charge cable is plugged
     */
    ChargeCablePlugged(setOf("6826")),

    /**
     * Charge Configuration failed
     */
    ChargeConfigurationFailed(setOf("7401")),

    /**
     * Charge Configuration failed because passed max soc value is below vehicle threshold
     */
    ChargeConfigurationFailedSocBelowTreshold(setOf("7402")),

    /**
     * Charge Configuration not authorized
     */
    ChargeConfigurationNotAuthorized(setOf("7403")),

    /**
     * Charge Configuration not possible since INSTANT CHARGING is already activated
     */
    ChargeConfigurationNotPossibleSinceInstantChargingIsActive(setOf("7404")),

    /**
     * Charge Control failed
     */
    ChargeControlConfigFaild(setOf("8501")),

    /**
     * Charge Control not authorized
     */
    ChargeControlNotAuthorized(setOf("8502")),

    /**
     * Failed due to charge coupler still locked
     */
    ChargeCouplerStillLocked(setOf("4413")),

    /**
     * Charge optimization failed
     */
    ChargeOptimizationFailed(setOf("5015")),

    /**
     * Charge optimization not authorized
     */
    ChargeOptimizationNotAuthorized(setOf("5017")),

    /**
     * Charge optimization not possible since either INSTANT CHARGING is already activated or INSTANT CHARGING ACP command is currently in progress
     */
    ChargeOptimizationNotPossible(setOf("5018")),

    /**
     * Charge optimization overwritten
     */
    ChargeOptimizationOverwritten(setOf("5016")),

    /**
     * Charge programs not supported by vehicle
     */
    ChargeProgramsNotSupportedByVehicle(setOf("7405")),

    /**
     * Failed due to charging cable plugged
     */
    ChargingCablePlugged(setOf("2025")),

    /**
     * PreConditioning not possible, charging not finished
     */
    ChargingNotFinished(setOf("4053")),

    /**
     * Failed due to charging system not awake
     */
    ChargingSystemNotAwake(setOf("4404", "4414")),

    /**
     * Check engine light is on
     */
    CheckEngineLightOn(setOf("6832")),

    /**
     * Command failed. Normally, there should be additional business errors detailing what exactly went wrong
     */
    CommandFailed(setOf("CMD_FAILED")),

    /**
     * The command is not available for the specified vehicle
     */
    CommandUnavailable(setOf("COMMAND_UNAVAILABLE")),

    /**
     * a general error and the `message` field of the VehicleAPIError struct should be checked for more information
     */
    CouldNotSendCommand(setOf("RIS_COULD_NOT_SEND_COMMAND")),

    /**
     * cryptologic error
     */
    CryptoError(setOf("6821", "7324")),

    /**
     * Failed due to decklid not closed
     */
    DecklidNotClosed(setOf("4133")),

    /**
     * Failed due to decklid not locked
     */
    DecklidNotLocked(setOf("4112")),

    /**
     * Failed due to front left door not closed
     */
    DoorFrontLeftNotClosed(setOf("4129")),

    /**
     * Failed due to front left door not locked
     */
    DoorFrontLeftNotLocked(setOf("4108")),

    /**
     * Failed due to front right door not closed
     */
    DoorFrontRightNotClosed(setOf("4130")),

    /**
     * Failed due to front right door not locked
     */
    DoorFrontRightNotLocked(setOf("4109")),

    /**
     * Failed due to one or more doors not closed
     */
    DoorNotClosed(setOf("4128")),

    /**
     * Failed due to door is open
     */
    DoorOpen(setOf("2016", "4002")),

    /**
     * Failed due to rear left door not closed
     */
    DoorRearLeftNotClosed(setOf("4131")),

    /**
     * Failed due to rear left door not locked
     */
    DoorRearLeftNotLocked(setOf("4110")),

    /**
     * Failed due to rear right door not closed
     */
    DoorRearRightNotClosed(setOf("4132")),

    /**
     * Failed due to rear right door not locked
     */
    DoorRearRightNotLocked(setOf("4111")),

    /**
     * Command failed: Doors failed
     */
    DoorsFailed(setOf("7308")),

    /**
     * Failed due to one or more doors not locked
     */
    DoorsNotLocked(setOf("4107", "6813")),

    /**
     * Doors open
     */
    DoorsOpen(setOf("6814")),

    /**
     * doors were opened
     */
    DoorsOpened(setOf("6804")),

    /**
     * Failed due to drive motor overheated
     */
    DriveMotorOverheated(setOf("4242")),

    /**
     * Failed due to drive motor overheated on front left window
     */
    DriveMotorOverheatedFrontLeftWindow(setOf("4243")),

    /**
     * Failed due to drive motor overheated on front right window
     */
    DriveMotorOverheatedFrontRightWindow(setOf("4244")),

    /**
     * Failed due to drive motor overheated on front roof roller blind
     */
    DriveMotorOverheatedFrontRoofRollerBlind(setOf("4250")),

    /**
     * Failed due to drive motor overheated on rear left roller blind
     */
    DriveMotorOverheatedRearLeftRollerBlind(setOf("4247")),

    /**
     * Failed due to drive motor overheated on rear left window
     */
    DriveMotorOverheatedRearLeftWindow(setOf("4245")),

    /**
     * Failed due to drive motor overheated on rear right roller blind
     */
    DriveMotorOverheatedRearRightRollerBlind(setOf("4248")),

    /**
     * Failed due to drive motor overheated on rear right window
     */
    DriveMotorOverheatedRearRightWindow(setOf("4246")),

    /**
     * Failed due to drive motor overheated on rear roof roller blind
     */
    DriveMotorOverheatedRearRoofRollerBlind(setOf("4251")),

    /**
     * Failed due to drive motor overheated on sunroof
     */
    DriveMotorOverheatedSunroof(setOf("4249")),

    /**
     * Failed due to driver door open
     */
    DriverDoorOpen(setOf("4115")),

    /**
     * Failed due to driver in vehicle
     */
    DriverIsInVehicle(setOf("4103")),

    /**
     * Engine control module unexpecedely shuts off
     */
    EngineControlShutsOff(setOf("6810")),

    /**
     * Engine Hood open
     */
    EngineHoodOpen(setOf("6816")),

    /**
     * engine hood was opened
     */
    EngineHoodOpened(setOf("6805")),

    /**
     * engine unexpected shut off
     */
    EngineShutOff(setOf("6823")),

    /**
     * engine shut off - doors became unlocked
     */
    EngineShutOffByDoorsUnlocked(setOf("6803")),

    /**
     * engine shut off - either by timeout or by user request
     */
    EngineShutOffByTimeoutOrUser(setOf("6802")),

    /**
     * engine successfully started
     */
    EngineSuccessfullyStarted(setOf("6801")),

    /**
     * Failed due to error in horn control system
     */
    ErrorInHornControlSystem(setOf("6408")),

    /**
     * Failed due to error in light control system
     */
    ErrorInLightControlSystem(setOf("6409")),

    /**
     * Failed due to error in light or horn control system
     */
    ErrorInLightOrHornControlSystem(setOf("6407")),

    /**
     * Command expired
     */
    Expired(setOf("7316")),

    /**
     * Charge Control failed due to external charging problem 1
     */
    ExternalChargingProblem1(setOf("8511")),

    /**
     * Charge Control failed due to external charging problem 10
     */
    ExternalChargingProblem10(setOf("8520")),

    /**
     * Charge Control failed due to external charging problem 11
     */
    ExternalChargingProblem11(setOf("8521")),

    /**
     * Charge Control failed due to external charging problem 12
     */
    ExternalChargingProblem12(setOf("8522")),

    /**
     * Charge Control failed due to external charging problem 13
     */
    ExternalChargingProblem13(setOf("8523")),

    /**
     * Charge Control failed due to external charging problem 14
     */
    ExternalChargingProblem14(setOf("8524")),

    /**
     * Charge Control failed due to external charging problem 2
     */
    ExternalChargingProblem2(setOf("8512")),

    /**
     * Charge Control failed due to external charging problem 3
     */
    ExternalChargingProblem3(setOf("8513")),

    /**
     * Charge Control failed due to external charging problem 4
     */
    ExternalChargingProblem4(setOf("8514")),

    /**
     * Charge Control failed due to external charging problem 5
     */
    ExternalChargingProblem5(setOf("8515")),

    /**
     * Charge Control failed due to external charging problem 6
     */
    ExternalChargingProblem6(setOf("8516")),

    /**
     * Charge Control failed due to external charging problem 7
     */
    ExternalChargingProblem7(setOf("8517")),

    /**
     * Charge Control failed due to external charging problem 8
     */
    ExternalChargingProblem8(setOf("8518")),

    /**
     * Charge Control failed due to external charging problem 9
     */
    ExternalChargingProblem9(setOf("8519")),

    /**
     * Failed due to vehicle already external locked
     */
    ExternallyLocked(setOf("4120")),

    /**
     * Failed
     */
    Failed(setOf("4001", "4011", "4400", "4410", "6501")),

    /**
     * failedCANCom
     */
    FailedCanCom(setOf("6502")),

    /**
     * failedIgnOn
     */
    FailedIgnOn(setOf("6503")),

    /**
     * Fastpath timeout
     */
    FastpathTimeout(setOf("42")),

    /**
     * Failed due to feature not available on front left window
     */
    FeatureNotAvailableFrontLeftWindow(setOf("4327")),

    /**
     * Failed due to feature not available on front right window
     */
    FeatureNotAvailableFrontRightWindow(setOf("4329")),

    /**
     * Failed due to feature not available on front roof roller blind
     */
    FeatureNotAvailableFrontRoofRollerBlind(setOf("4341")),

    /**
     * Failed due to feature not available on rear left roller blind
     */
    FeatureNotAvailableRearLeftRollerBlind(setOf("4337")),

    /**
     * Failed due to feature not available on rear left window
     */
    FeatureNotAvailableRearLeftWindow(setOf("4331")),

    /**
     * Failed due to feature not available on rear right roller blind
     */
    FeatureNotAvailableRearRightRollerBlind(setOf("4339")),

    /**
     * Failed due to feature not available on rear right window
     */
    FeatureNotAvailableRearRightWindow(setOf("4333")),

    /**
     * Failed due to feature not available on rear roller blind
     */
    FeatureNotAvailableRearRollerBlind(setOf("4263")),

    /**
     * Failed due to feature not available on rear roof roller blind
     */
    FeatureNotAvailableRearRoofRollerBlind(setOf("4343")),

    /**
     * Failed due to feature not available on sunroof
     */
    FeatureNotAvailableSunroof(setOf("4335")),

    /**
     * Failed due to flip window not closed
     */
    FlipWindowNotClosed(setOf("4134")),

    /**
     * Failed due to flip window not locked
     */
    FlipWindowNotLocked(setOf("4113")),

    /**
     * Failed due to front roof roller blind in motion
     */
    FrontRoofRollerBlindInMotion(setOf("4240")),

    /**
     * FBS general error for challengeResponse generation
     */
    FsbChallengeResponseError(setOf("6830")),

    /**
     * FBS is not able to create a valid challengeResponse for the given VIN
     */
    FsbUnableToCreateChallengeResponse(setOf("6827")),

    /**
     * FBS is not reachable due to maintenance
     */
    FsbUnreachable(setOf("6828")),

    /**
     * Failed due to fuel flap not closed
     */
    FuelFlapNotClosed(setOf("4137")),

    /**
     * Failed due to fuel flap not locked
     */
    FuelFlapNotLocked(setOf("4114")),

    /**
     * fuel got low
     */
    FuelLow(setOf("6807")),

    /**
     * Fuel tank too low (less than 25% volume)
     */
    FuelTankTooLow(setOf("6818")),

    /**
     * Command failed: Function disabled.
     */
    FunctionDisabled(setOf("7328")),

    /**
     * Command failed: Function not authorized.
     */
    FunctionNotAuthorized(setOf("7329")),

    /**
     * Failed due to gas alarm active
     */
    GasAlarmActive(setOf("4149")),

    /**
     * gas pedal was pressed
     */
    GasPedalPressed(setOf("6808")),

    /**
     * The gear is not in Parking position
     */
    GearNotInPark(setOf("2014", "6812")),

    /**
     * Failed due to general error in charge coupler system
     */
    GeneralErrorChargeCoupler(setOf("4127")),

    /**
     * Failed due to general error in charge flap system
     */
    GeneralErrorChargeFlap(setOf("4125")),

    /**
     * Failed due to general error in locking system
     */
    GeneralErrorLocking(setOf("4122")),

    /**
     * Failed due to HOLD-function active
     */
    HoldActive(setOf("4119")),

    /**
     * Failed due to hood is open
     */
    HoodOpen(setOf("2018")),

    /**
     * Failed due to ignition state active
     */
    IgnitionActive(setOf("4116")),

    /**
     * Command failed: Ignition failed
     */
    IgnitionFailed(setOf("7303")),

    /**
     * Failed due to ignition transition
     */
    IgnitionInTransition(setOf("4015")),

    /**
     * The ignition is not switched off
     */
    IgnitionNotSwitchedOff(setOf("7323")),

    /**
     * Failed due to ignition is on
     */
    IgnitionOn(setOf("2013", "4003", "4101", "4202", "4401", "4411", "7310")),

    /**
     * Command failed
     */
    ImmobilizerCommandFailed(setOf("7301")),

    /**
     * Command failed: Immobilizer failed
     */
    ImmobilizerFailed(setOf("7307")),

    /**
     * Get Challenge Failed: General Error
     */
    ImmobilizerGetChallengeFailed(setOf("7325")),

    /**
     * Command failed: Incompatible response from vehicle
     */
    ImmobilizerIncompatibleResponseFromVehicle(setOf("7317")),

    /**
     * Command failed: Vehicle states
     */
    ImmobilizerParkingBrakeNotSet(setOf("7309")),

    /**
     * returned if the service(s) for the requested command are not active
     */
    InactiveServices(setOf("RIS_INACTIVE_SERVICES")),

    /**
     * Incomplete values
     */
    IncompleteValues(setOf("115")),

    /**
     * not possible since either INSTANT CHARGING is already activated or INSTANT CHARGING command is currently in progress
     */
    InstantChargingActiveOrInProgress(setOf("4054", "8504")),

    /**
     * Failed due to internal system error
     */
    InternalSystemError(setOf("4325")),

    /**
     * Received an invalid condition
     */
    InvalidCondition(setOf("CMD_INVALID_CONDITION")),

    /**
     * Failed due to invalid horn repeat
     */
    InvalidHornRepeat(setOf("6406")),

    /**
     * Failed due to invalid ignition state
     */
    InvalidIgnitionState(setOf("4287")),

    /**
     * Failed due to invalid ignition state on front left window
     */
    InvalidIgnitionStateFrontLeftWindow(setOf("4288")),

    /**
     * Failed due to invalid ignition state on front right window
     */
    InvalidIgnitionStateFrontRightWindow(setOf("4289")),

    /**
     * Failed due to invalid ignition state on front roof roller blind
     */
    InvalidIgnitionStateFrontRoofRollerBlind(setOf("4295")),

    /**
     * Failed due to invalid ignition state on rear left roller blind
     */
    InvalidIgnitionStateRearLeftRollerBlind(setOf("4292")),

    /**
     * Failed due to invalid ignition state on rear left window
     */
    InvalidIgnitionStateRearLeftWindow(setOf("4290")),

    /**
     * Failed due to invalid ignition state on rear right roller blind
     */
    InvalidIgnitionStateRearRightRollerBlind(setOf("4293")),

    /**
     * Failed due to invalid ignition state on rear right window
     */
    InvalidIgnitionStateRearRightWindow(setOf("4291")),

    /**
     * Failed due to invalid ignition state on rear roof roller blind
     */
    InvalidIgnitionStateRearRoofRollerBlind(setOf("4296")),

    /**
     * Failed due to invalid ignition state on sunroof
     */
    InvalidIgnitionStateSunroof(setOf("4294")),

    /**
     * Failed due to invalid number on front left window
     */
    InvalidNumberFrontLeftWindow(setOf("4326")),

    /**
     * Failed due to invalid number on front right window
     */
    InvalidNumberFrontRightWindow(setOf("4328")),

    /**
     * Failed due to invalid number on front roof roller blind
     */
    InvalidNumberFrontRoofRollerBlind(setOf("4340")),

    /**
     * Failed due to invalid number on rear left roller blind
     */
    InvalidNumberRearLeftRollerBlind(setOf("4336")),

    /**
     * Failed due to invalid number on rear left window
     */
    InvalidNumberRearLeftWindow(setOf("4330")),

    /**
     * Failed due to invalid number on rear right roller blind
     */
    InvalidNumberRearRightRollerBlind(setOf("4338")),

    /**
     * Failed due to invalid number on rear right window
     */
    InvalidNumberRearRightWindow(setOf("4332")),

    /**
     * Failed due to invalid number on rear roof roller blind
     */
    InvalidNumberRearRoofRollerBlind(setOf("4342")),

    /**
     * Failed due to invalid number on sunroof
     */
    InvalidNumberSunroof(setOf("4334")),

    /**
     * Failed due to invalid position on front left window
     */
    InvalidPositionFrontLeftWindow(setOf("4346")),

    /**
     * Failed due to invalid position on front right window
     */
    InvalidPositionFrontRightWindow(setOf("4348")),

    /**
     * Failed due to invalid position on front roof roller blind
     */
    InvalidPositionFrontRoofRollerBlind(setOf("4360")),

    /**
     * Failed due to invalid position on rear left roller blind
     */
    InvalidPositionRearLeftRollerBlind(setOf("4354")),

    /**
     * Failed due to invalid position on rear left window
     */
    InvalidPositionRearLeftWindow(setOf("4350")),

    /**
     * Failed due to invalid position on rear right roller blind
     */
    InvalidPositionRearRightRollerBlind(setOf("4356")),

    /**
     * Failed due to invalid position on rear right window
     */
    InvalidPositionRearRightWindow(setOf("4352")),

    /**
     * Failed due to invalid position on rear roller blind
     */
    InvalidPositionRearRollerBlind(setOf("4364")),

    /**
     * Failed due to invalid position on rear roof roller blind
     */
    InvalidPositionRearRoofRollerBlind(setOf("4362")),

    /**
     * Failed due to invalid position on sunroof
     */
    InvalidPositionSunroof(setOf("4358")),

    /**
     * Failed due to invalid power status
     */
    InvalidPowerStatus(setOf("4265")),

    /**
     * Failed due to invalid power status on front left window
     */
    InvalidPowerStatusFrontLeftWindow(setOf("4266")),

    /**
     * Failed due to invalid power status on front right window
     */
    InvalidPowerStatusFrontRightWindow(setOf("4267")),

    /**
     * Failed due to invalid power status on front roof roller blind
     */
    InvalidPowerStatusFrontRoofRollerBlind(setOf("4273")),

    /**
     * Failed due to invalid power status on rear left roller blind
     */
    InvalidPowerStatusRearLeftRollerBlind(setOf("4270")),

    /**
     * Failed due to invalid power status on rear left window
     */
    InvalidPowerStatusRearLeftWindow(setOf("4268")),

    /**
     * Failed due to invalid power status on rear right roller blind
     */
    InvalidPowerStatusRearRightRollerBlind(setOf("4271")),

    /**
     * Failed due to invalid power status on rear right window
     */
    InvalidPowerStatusRearRightWindow(setOf("4269")),

    /**
     * Failed due to low or high voltage on rear roller blind
     */
    InvalidPowerStatusRearRollerBlind(setOf("4275")),

    /**
     * Failed due to invalid power status on rear roof roller blind
     */
    InvalidPowerStatusRearRoofRollerBlind(setOf("4274")),

    /**
     * Failed due to invalid power status on sunroof
     */
    InvalidPowerStatusSunroof(setOf("4272")),

    /**
     * Failed due to invalid SMS time
     */
    InvalidSmsTime(setOf("4013")),

    /**
     * Should never happen due to migration guide
     */
    InvalidStatus(setOf("CMD_INVALID_STATUS")),

    /**
     * Failed due to key button pressed during drive
     */
    KeyButtonPressed(setOf("3053")),

    /**
     * vehicle key plugged in the ignition mechanism
     */
    KeyPluggedIn(setOf("6811")),

    /**
     * Vehicle key plugged in while engine is running
     */
    KeyPluggedInWhileEngineIsRunning(setOf("6809")),

    /**
     * Lock request not authorized
     */
    LockNotAuthorized(setOf("3054", "4004")),

    /**
     * Failed due to request to central locking system cancelled
     */
    LockingRequestCancelled(setOf("4138")),

    /**
     * Energy level in Battery is too low
     */
    LowBatteryLevel(setOf("21", "4052")),

    /**
     * Failed due to low battery level 1
     */
    LowBatteryLevel1(setOf("4105", "4204", "6405")),

    /**
     * Failed due to low battery level 2
     */
    LowBatteryLevel2(setOf("4106", "4203", "6404")),

    /**
     * Maintance planned
     */
    Maintenance(setOf("3003")),

    /**
     * Max. SOC setting not possible since VVR value of either maxSocLowerLimit or maxSocUpperLimit is missing
     */
    MaxSocLimitMissing(setOf("7406")),

    /**
     * Max. SOC setting not possible since maxSOC value is not in range of maxSocLowerLimit & maxSocUpperLimit
     */
    MaxSocNotInRange(setOf("7407")),

    /**
     * Failed due to mechanical problem on rear roller blind
     */
    MechanicalProblemRearRollerBlind(setOf("4286")),

    /**
     * Min. SOC not possible since VVR value of either minSocLowerLimit or minSocUpperLimit is missing
     */
    MinSocLimitMissing(setOf("8505")),

    /**
     * Min. SOC setting not possible since minSOC value is not in range of minSocLowerLimit & minSocUpperLimit
     */
    MinSocNotInRange(setOf("8506")),

    /**
     * Failed due to mounted roof box
     */
    MountedRoofBox(setOf("4264")),

    /**
     * Failed due to multiple anti-trap protection activations
     */
    MultiAntiTrapProtections(setOf("4216")),

    /**
     * Failed due to multiple anti-trap protection activations on front left window
     */
    MultiAntiTrapProtectionsFrontLeftWindow(setOf("4217")),

    /**
     * Failed due to multiple anti-trap protection activations on front right window
     */
    MultiAntiTrapProtectionsFrontRightWindow(setOf("4218")),

    /**
     * Failed due to multiple anti-trap protection activations on front roof roller blind
     */
    MultiAntiTrapProtectionsFrontRoofRollerBlind(setOf("4224")),

    /**
     * Failed due to multiple anti-trap protection activations on rear left roller blind
     */
    MultiAntiTrapProtectionsRearLeftRollerBlind(setOf("4221")),

    /**
     * Failed due to multiple anti-trap protection activations on rear left window
     */
    MultiAntiTrapProtectionsRearLeftWindow(setOf("4219")),

    /**
     * Failed due to multiple anti-trap protection activations on rear right roller blind
     */
    MultiAntiTrapProtectionsRearRightRollerBlind(setOf("4222")),

    /**
     * Failed due to multiple anti-trap protection activations on rear right window
     */
    MultiAntiTrapProtectionsRearRightWindow(setOf("4220")),

    /**
     * Failed due to multiple anti-trap protection activations on rear roof roller blind
     */
    MultiAntiTrapProtectionsRearRoofRollerBlind(setOf("4225")),

    /**
     * Failed due to multiple anti-trap protection activations on sunroof
     */
    MultiAntiTrapProtectionsSunroof(setOf("4223")),

    /**
     * new RS requested within operational timewindow (default 15 min.)
     */
    NewRsRequested(setOf("6820")),

    /**
     * DaiVB does not receive asynchronous callback within MAX_RES_CALLBACK_TIME
     */
    NoCallbackReceived(setOf("6829")),

    /**
     * The client does not have an internet connection and therefore the command could not be sent.
     */
    NoInternetConnection(setOf("NO_INTERNET_CONNECTION")),

    /**
     * Failed due to no user acceptence
     */
    NoUserAcceptance(setOf("3056")),

    /**
     * No vehicle was selected
     */
    NoVehicleSelected(setOf("NO_VEHICLE_SELECTED")),

    /**
     * Request is not authorized
     */
    NotAuthorized(setOf("4407", "4417", "6504")),

    /**
     * Failed due to vehicle not external locked
     */
    NotExternallyLocked(setOf("4104")),

    /**
     * Failed due to vehicle not on drop off zone
     */
    NotInDropOffZone(setOf("3001")),

    /**
     * Failed due to vehicle not in parking gear selection
     */
    NotInPark(setOf("4123")),

    /**
     * Failed due to vehicle not in parking gear selection
     */
    NotInParkingGear(setOf("4402")),

    /**
     * NULL/INF values
     */
    NullOrInfiniteValues(setOf("125")),

    /**
     * Failed due to open load on rear roller blind
     */
    OpenLoadRearRollerBlind(setOf("4302")),

    /**
     * Command was overwritten in queue
     */
    Overwritten(setOf("CMD_OVERWRITTEN")),

    /**
     * Failed due to parallel request to central locking system
     */
    ParallelRequestToLocking(setOf("4117")),

    /**
     * Failed due to parameter not allowed
     */
    ParameterNotAllowed(setOf("4145")),

    /**
     * Failed due to someone detected inside vehicle
     */
    PersonDetected(setOf("2012", "3051")),

    /**
     * The user cancelled the PIN input. The command is therefore not transmitted and not executed.
     */
    PinInputCancelled(setOf("PIN_INPUT_CANCELLED")),

    /**
     * returned if the given PIN does not match the one saved in CPD
     */
    PinInvalid(setOf("RIS_PIN_INVALID")),

    /**
     * The pin provider was not configured, but a PIN is needed for this command.
     */
    PinProviderMissing(setOf("PIN_PROVIDER_MISSING")),

    /**
     * returned if the user tried to send a sensitive command that requires a PIN but didn't provide one
     */
    PinRequired(setOf("RIS_PIN_REQUIRED")),

    /**
     * PreConditioning not possible, General error
     */
    PreConditionGeneralError(setOf("4055")),

    /**
     * Processing of zev command failed
     */
    ProcessingFailed(setOf("4051")),

    /**
     * Failed due to RDL inactive
     */
    RdlInactive(setOf("4141")),

    /**
     * Failed due to RDU decklid inactive
     */
    RduDecklidInactive(setOf("4142")),

    /**
     * Failed due to RDU fuel flap inactive
     */
    RduFuelFlapInactive(setOf("4143")),

    /**
     * Failed due to RDU global inactive
     */
    RduGlobalInactive(setOf("4139")),

    /**
     * Failed due to RDU selective inactive
     */
    RduSelectionInactive(setOf("4140")),

    /**
     * Failed due to rear charge flap not closed
     */
    RearChargeFlapNotClosed(setOf("4136")),

    /**
     * Failed due to rear roof roller blind in motion
     */
    RearRoofRollerBlindInMotion(setOf("4241")),

    /**
     * Command was rejected due to a blocked command queue. This can happen if another user is executing a similar command.
     */
    RejectedByQueue(setOf("CMD_REJECTED_BY_QUEUE")),

    /**
     * Failed due to remote engine start is active
     */
    RemoteEngineStartIsActive(setOf("4102", "4201")),

    /**
     * Remote start is blocked due to parallel FBS workflow
     */
    RemoteStartBlocked(setOf("6831")),

    /**
     * Remote VTA failed
     */
    RemoteVtaFailed(setOf("5301")),

    /**
     * Remote VTA ignition not locked
     */
    RemoteVtaIgnitionLocked(setOf("5303")),

    /**
     * Remote VTA VVR not allowed
     */
    RemoteVtaNotAllowed(setOf("5305")),

    /**
     * Remote VTA service not authorized
     */
    RemoteVtaNotAuthorized(setOf("5302")),

    /**
     * Remote VTA VVR value not set
     */
    RemoteVtaValueNotSet(setOf("5304")),

    /**
     * Failed due to request not allowed
     */
    RequestNotAllowed(setOf("4144")),

    /**
     * request received and processed twice by EIS, within the same IGN cycle rsAbortedRequestRefus
     */
    RequestReceivedTwice(setOf("6822")),

    /**
     * Failed due to reservation already used
     */
    ReservationAlreadyUsed(setOf("2005")),

    /**
     * Failed due to reservation in the past
     */
    ReservationInPast(setOf("2006")),

    /**
     * Failed due to restricted info parameter
     */
    RestrictedInfoParameter(setOf("4146")),

    /**
     * Failed due to roof in motion
     */
    RoofInMotion(setOf("4239")),

    /**
     * Failed due to roof or roller blind in motion
     */
    RoofOrRollerBlindInMotion(setOf("4238")),

    /**
     * RVF (sigpos) failed
     */
    RvfFailed(setOf("6401")),

    /**
     * RVF (sigpos) failed due to not authorized
     */
    RvfFailedNotAuthorized(setOf("6403")),

    /**
     * RVF (sigpos) failed due to ignition is on
     */
    RvfFailedVehicleStageInIgn(setOf("6402")),

    /**
     * Failed due to hall sensor signal problem on rear roller blind
     */
    SensorProblemRearRollerBlind(setOf("4226")),

    /**
     * Service not authorized
     */
    ServiceNotAuthorized(setOf("2004", "4062", "4100", "4200")),

    /**
     * Service currently not available
     */
    ServiceUnavailable(setOf("3004")),

    /**
     * Failed due to side charge flap not closed
     */
    SideChargeFlapNotClosed(setOf("4135")),

    /**
     * Failed due to detection of snow chains
     */
    SnowChainsDetected(setOf("2020")),

    /**
     * Speed alert not authorized
     */
    SpeedAlertNotAuthorized(setOf("6102")),

    /**
     * Failed due to sunroof is open
     */
    SunroofOpen(setOf("2010")),

    /**
     * Syntax error
     */
    SyntaxError(setOf("120")),

    /**
     * Failed due to system is blocked on rear roller blind
     */
    SystemBlockedRearRollerBlind(setOf("4215")),

    /**
     * Failed due to system could not be normed
     */
    SystemCouldNotBeNormed(setOf("4303")),

    /**
     * Failed due to system could not be normed on front left window
     */
    SystemCouldNotBeNormedFrontLeftWindow(setOf("4304")),

    /**
     * Failed due to system could not be normed on front right window
     */
    SystemCouldNotBeNormedFrontRightWindow(setOf("4305")),

    /**
     * Failed due to system could not be normed on front roof roller blind
     */
    SystemCouldNotBeNormedFrontRoofRollerBlind(setOf("4311")),

    /**
     * Failed due to system could not be normed on rear left roller blind
     */
    SystemCouldNotBeNormedRearLeftRollerBlind(setOf("4308")),

    /**
     * Failed due to system could not be normed on rear left window
     */
    SystemCouldNotBeNormedRearLeftWindow(setOf("4306")),

    /**
     * Failed due to system could not be normed on rear right roller blind
     */
    SystemCouldNotBeNormedRearRightRollerBlind(setOf("4309")),

    /**
     * Failed due to system could not be normed on rear right window
     */
    SystemCouldNotBeNormedRearRightWindow(setOf("4307")),

    /**
     * Failed due to system could not be normed on rear roof roller blind
     */
    SystemCouldNotBeNormedRearRoofRollerBlind(setOf("4312")),

    /**
     * Failed due to system could not be normed on sunroof
     */
    SystemCouldNotBeNormedSunroof(setOf("4310")),

    /**
     * Failed due to system malfunction
     */
    SystemMalfunction(setOf("4314")),

    /**
     * Failed due to system malfunction on front left window
     */
    SystemMalfunctionFrontLeftWindow(setOf("4315")),

    /**
     * Failed due to system malfunction on front right window
     */
    SystemMalfunctionFrontRightWindow(setOf("4316")),

    /**
     * Failed due to system malfunction on front roof roller blind
     */
    SystemMalfunctionFrontRoofRollerBlind(setOf("4322")),

    /**
     * Failed due to system malfunction on rear left roller blind
     */
    SystemMalfunctionRearLeftRollerBlind(setOf("4319")),

    /**
     * Failed due to system malfunction on rear left window
     */
    SystemMalfunctionRearLeftWindow(setOf("4317")),

    /**
     * Failed due to system malfunction on rear right roller blind
     */
    SystemMalfunctionRearRightRollerBlind(setOf("4320")),

    /**
     * Failed due to system malfunction on rear right window
     */
    SystemMalfunctionRearRightWindow(setOf("4318")),

    /**
     * Failed due to system malfunction on rear roller blind
     */
    SystemMalfunctionRearRollerBlind(setOf("4324")),

    /**
     * Failed due to system malfunction on rear roof roller blind
     */
    SystemMalfunctionRearRoofRollerBlind(setOf("4323")),

    /**
     * Failed due to system malfunction on sunroof
     */
    SystemMalfunctionSunroof(setOf("4321")),

    /**
     * Failed due to system not normed
     */
    SystemNotNormed(setOf("4253")),

    /**
     * Failed due to system not normed  on front left window
     */
    SystemNotNormedFrontLeftWindow(setOf("4254")),

    /**
     * Failed due to system not normed  on front right window
     */
    SystemNotNormedFrontRightWindow(setOf("4255")),

    /**
     * Failed due to system not normed on front roof roller blind
     */
    SystemNotNormedFrontRoofRollerBlind(setOf("4261")),

    /**
     * Failed due to system not normed on rear left roller blind
     */
    SystemNotNormedRearLeftRollerBlind(setOf("4258")),

    /**
     * Failed due to system not normed  on rear left window
     */
    SystemNotNormedRearLeftWindow(setOf("4256")),

    /**
     * Failed due to system not normed on rear right roller blind
     */
    SystemNotNormedRearRightRollerBlind(setOf("4259")),

    /**
     * Failed due to system not normed  on rear right window
     */
    SystemNotNormedRearRightWindow(setOf("4257")),

    /**
     * Failed due to system not normed on rear roof roller blind
     */
    SystemNotNormedRearRoofRollerBlind(setOf("4262")),

    /**
     * Failed due to system not normed on sunroof
     */
    SystemNotNormedSunroof(setOf("4260")),

    /**
     * Failed due to tank level too low
     */
    TankLevelLow(setOf("2026")),

    /**
     * Failed due to too low tank level during drive
     */
    TankLevelLowDrive(setOf("3055")),

    /**
     * TCU exhausted all retries on CAN and did not get a valid response from EIS
     */
    TcuCanError(setOf("6824")),

    /**
     * TCU has remote start service deauthorized
     */
    TcuNoRemoteService(setOf("6825")),

    /**
     * Technical error, retry possible
     */
    TechnicalError(setOf("2001")),

    /**
     * Severe technical error, no retries
     */
    TechnicalErrorNoRetry(setOf("2002")),

    /**
     * Failed due to temperature too low on rear roller blind
     */
    TemperatureTooLowRearRollerBlind(setOf("4313")),

    /**
     * Command was forcefully terminated
     */
    Terminated(setOf("CMD_TERMINATED")),

    /**
     * Failed due to thermal protection active on rear roller blind
     */
    ThermalProtectionActiveRearRollerBlind(setOf("4297")),

    /**
     * Failed due to timeout
     */
    Timeout(setOf("4012", "4406", "4416", "CMD_TIMEOUT")),

    /**
     * Failed due to too low tire pressure
     */
    TirePressureLow(setOf("2022")),

    /**
     * Failed due to too many requests to horn control system
     */
    TooManyRequestsToHornControlSystem(setOf("6410")),

    /**
     * Failed due to too many requests to central locking system
     */
    TooManyRequestsToLocking(setOf("4118")),

    /**
     * Failed due to convertible top is open
     */
    TopOpen(setOf("2011")),

    /**
     * Failed due to detection of trailor
     */
    TrailerDetected(setOf("2015")),

    /**
     * Failed due to detection of trailer hitch
     */
    TrailerHitchDetected(setOf("2021")),

    /**
     * Failed due to transport mode active
     */
    TransportModeActive(setOf("4147")),

    /**
     * Failed due to trunk lid is open
     */
    TrunkOpen(setOf("2017")),

    /**
     * Failed due to UI handler not available on front left window
     */
    UnavailableUiHandlerFrontLeftWindow(setOf("4347")),

    /**
     * Failed due to UI handler not available on front right window
     */
    UnavailableUiHandlerFrontRightWindow(setOf("4349")),

    /**
     * Failed due to UI handler not available on front roof roller blind
     */
    UnavailableUiHandlerFrontRoofRollerBlind(setOf("4361")),

    /**
     * Failed due to UI handler not available on rear left roller blind
     */
    UnavailableUiHandlerRearLeftRollerBlind(setOf("4355")),

    /**
     * Failed due to UI handler not available on rear left window
     */
    UnavailableUiHandlerRearLeftWindow(setOf("4351")),

    /**
     * Failed due to UI handler not available on rear right roller blind
     */
    UnavailableUiHandlerRearRightRollerBlind(setOf("4357")),

    /**
     * Failed due to UI handler not available on rear right window
     */
    UnavailableUiHandlerRearRightWindow(setOf("4353")),

    /**
     * Failed due to UI handler not available on rear roller blind
     */
    UnavailableUiHandlerRearRollerBlind(setOf("4365")),

    /**
     * Failed due to UI handler not available on rear roof roller blind
     */
    UnavailableUiHandlerRearRoofRollerBlind(setOf("4363")),

    /**
     * Failed due to UI handler not available on sunroof
     */
    UnavailableUiHandlerSunroof(setOf("4359")),

    /**
     * Unexpected respons
     */
    UnexpectedResponse(setOf("6101")),

    /**
     * Failed due to unknown state of charging system
     */
    UnknownChargingSystemState(setOf("4405", "4412", "4415")),

    /**
     * returned if an unknown error has occurred. Should never happen, so let us know if you see this error
     */
    UnknownError(setOf("RIS_UNKNOWN_ERROR")),

    /**
     * Failed due to unknown error on rear roller blind
     */
    UnknownErrorRearRollerBlind(setOf("4344")),

    /**
     * Failed due to unknown reason
     */
    UnknownReason(setOf("4150")),

    /**
     * The status of the command is unknown. returned if the state of a given command could not be polled. When polling for the state of a command only the last running or currently running command status is returned. If the app is interested in the status of a previous command for any reason and the state cannot be determined this error is returned
     */
    UnknownStatus(setOf("RIS_EMPTY_VEHICLE_API_QUEUE")),

    /**
     * is returned if there was an error in polling the command state. E.g. 4xx/5xx response codes from the vehicleAPI
     */
    UnknownStatusDueToPollError(setOf("RIS_VEHICLE_API_POLLING")),

    /**
     * Failed due to unlock error in charge coupler system
     */
    UnlockErrorChargeCoupler(setOf("4126")),

    /**
     * Unlock request not authorized
     */
    UnlockNotAuthorized(setOf("4016")),

    /**
     * returned if the command request contains a command type that is not yet supported by the AppTwin
     */
    UnsupportedCommand(setOf("RIS_UNSUPPORTED_COMMAND")),

    /**
     * command is not supported by the currently selected environment
     */
    UnsupportedStage(setOf("RIS_UNSUPPORTED_ENVIRONMENT")),

    /**
     * returned if the CIAM ID is currently blocked from sending sensitive commands e.g. Doors Unlock due to too many PIN attempts
     */
    UserBlocked(setOf("RIS_CIAM_ID_BLOCKED")),

    /**
     * Failed due to valet parking active
     */
    ValetParkingActive(setOf("4121")),

    /**
     * Returned if the input parameters of the command did not pass validation. The payload should indicate what went wrong
     */
    ValidationFailed(setOf("RIS_VALIDATION_FAILED")),

    /**
     * Value out of range
     */
    ValueOutOfRange(setOf("100")),

    /**
     * Value overflow
     */
    ValueOverflow(setOf("110")),

    /**
     * Failed because vehicle is in motion
     */
    VehicleInMotion(setOf("4014", "4298", "4299", "4300", "4301")),

    /**
     * Failed due to vehicle in ready state
     */
    VehicleInReadyState(setOf("4403")),

    /**
     * Failed due to vehicle movement
     */
    VehicleMovement(setOf("2023")),

    /**
     * returned if a command request is received for a VIN that is not assigned to the ciam id of the current user
     */
    VehicleNotAssigned(setOf("RIS_FORBIDDEN_VIN")),

    /**
     * Failed due to vehicle in ready state
     */
    VehicleReady(setOf("4124")),

    /**
     * Failed due to window is open
     */
    WindowOpen(setOf("2019")),

    /**
     * Remote window/roof command failed
     */
    WindowRoofCommandFailed(setOf("6901")),

    /**
     * Remote window/roof command failed (vehicle state in IGN)
     */
    WindowRoofCommandFailedIgnState(setOf("6902")),

    /**
     * Remote window/roof command failed (service not activated in HERMES)
     */
    WindowRoofCommandServiceNotActive(setOf("6904")),

    /**
     * Remote window/roof command failed (window not normed)
     */
    WindowRoofCommandWindowNotNormed(setOf("6903")),

    /**
     * Windows and/or roof open
     */
    WindowsOrRoofOpen(setOf("6815")),

    /**
     * Wrong data type
     */
    WrongDataType(setOf("105")),

    /**
     * ZEV WeekDeptSet not authorized
     */
    ZevWeekDeptSetNotAuthorized(setOf("6602")),

    /**
     * ZEV WeekDeptSet not possible since either INSTANT CHARGING is already activated or INSTANT CHARGING ACP command is currently in progress
     */
    ZevWeekDeptSetProcessingDeptSetNotPossible(setOf("6604")),

    /**
     * ZEV WeekDeptSet processing failed
     */
    ZevWeekDeptSetProcessingFailed(setOf("6601")),

    /**
     * ZEV WeekDeptSet processing overwritten
     */
    ZevWeekDeptSetProcessingOverwritten(setOf("6603")),

    ;
}
