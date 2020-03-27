# Getting Started

The MBMobileSDK allows third party apps to connect into the Daimler eco system and communicate with vehicles. 
This guide will describe first steps for developers. As such it will cover how to integrate the SDK into your application, configure the SDK and how to fetch data or send commands.

*Please be aware that most of these steps depend on the previous section. It is not recommended to skip any parts of this guide.*

We provide an example app, that has a completely functional SDK integration with all the above mentioned features. It can be found in this repository within the `app` folder.


## Table of Contents
[Organisational](#organisational)

[Integrate the MBMobileSDK](#integrate-the-mbmobilesdk)

[Setup](#setup)

[Login](#login)

[Selecting a vehicle](#selecting-a-vehicle)

[Connecting to the vehicle](#connecting-to-the-vehicle)

[Retrieving a value once](#retrieving-a-value-once)

[Observing a value](#observing-a-value)

[Send car commands](#send-car-commands)

[Logout](#logout)

## Organisational
Register as a developer on the [Mercedes-Benz Developers Portal](https://developer.mercedes-benz.com).
Here you have to create an app to create app id's and get your application approved for production usage. More information about the approval process is available on the 
developer site. However, for testing against mock and simulated vehicles, no approval process is necessary. In the developer portal it is possible to skip this step for now and
continue with the guide.    

This registration is also valid vor the car simulator, which will be a huge help during the development and testing process. The simulator can be found [here](https://developer.mercedes-benz.com/car-simulator).

The simulator allows you to test your code on a simulated car. After login you should have multiple mock vehicles available. After selecting one of the cars you should see an image of the car in the center of the screen. On the right hand side there is a menu with two items.

On the right side the menu `Capabilities` allows the user to change vehicle values, such as unlocking doors or staring the engine. `Trip Simulator` allows for creation of trips. During a trip vehicle values are changed dynamically over time. This can be useful for testing the continuous observation of values.

## Integrate the MBMobileSDK

The mobile SDK is available using gradle. First add the following to the projects root `build.gradle`. This will add the Daimler Azure repository for loading dependencies.

```gradle
allprojects {
    repositories {
        google()
        jcenter()
        // ... [other dependencies]
        maven {
            url 'https://pkgs.dev.azure.com/daimler-ris/sdk/_packaging/release/maven/v1'
        }
    }
}
```

Next add the dependency to you Applications `build.gradle`:

```gradle
implementation "com.daimler.mm:MBMobileSDK:$mb_mobile_sdk_version"
```
With `$mb_mobile_sdk_version` being the current version of the SDK.  

After a gradle sync the MBMobileSDK should now be available to use. 

## Setup

The SDK has to be configured with some dynamic parameters which have to be provided by the SDK consumer. A builder is provided to ease the initialization process.

The following parameters can be configured:

 | Parameter     | Type        | Optional | Description |
 | ------------- |-------------|----------|-------------|
 | appIdentifier| String | no | The Identifier created in the [Mercedes-Benz Developers Portal](https://developer.mercedes-benz.com)|
 | ingressKeyStoreAlias | String| no | Alias for the keystore used for user authentication purposes. |
 | preferredAuthenticationType | Enum | yes | The preferred authentication type (`AuthenticationType.KEYCLOAK` (default) or `AuthenticationType.CIAM`) |
 | authenticationConfigurations | `List<AuthenticationConfiguration>` | no | List of authentication configurations containing an object with authentication type and its client ID. Please find more information on [Mercedes-Benz Developers Portal](https://developer.mercedes-benz.com) |

Non optional parameters have to be passed to the constructor.

Create the builder:

```kotlin
val configurationBuilder = MBMobileSDKConfiguration.Builder(
    application = application,
    appIdentifier = "appIdentifier",
    ingressKeyStoreAlias = "com.yourapp.keystore.alias",
    authenticationConfigurations = listOf(
        AuthenticationConfiguration(
            AuthenticationType.KEYCLOAK,
            "keycloakClientId"
        ),
        AuthenticationConfiguration(
            AuthenticationType.CIAM,
            "ciamClientId"
        )
    )
)
```
The variable `application` is the Android Application.
 
After configuration you call `build()` on the builder to create the configuration object.
With the smallest configuration you will end up with something like this:
 
 ```kotlin
val configuration = configurationBuilder.defaultRegionAndStage(Region.ECE, Stage.MOCK).build()
```

With the built configuration you can now setup the SDK with:
```kotlin
MBMobileSDK.setup(configuration)
```
## Login

The SDK provides services for a login with the Daimler eco system. This login has to be used by third party applications to access any vehicle information. 

First you need to request a TAN code using the user's mail or phone number.
```kotlin
MBIngressKit.userService().sendTan(user, countryCode)
    .onComplete { loginUser ->
        // check loginUser.userName: if it is NOT empty, 
        // the given user is already registered and a TAN was sent
    }.onFailure {
        // request failed
    }
```

If this function finishes with success, an 6-digit one-time password (OTP) was sent via email or SMS to that user. You must use that OTP (TAN) to login.

```kotlin
MBIngressKit.login(UserCredentials(user, tan))
    .onComplete {
        // login successful
    }.onFailure {
        // login failed
    }
```

## Selecting a vehicle
For most of the functionality either a Vehicle Identification Number (VIN) has to be passed to the calls or the vehicle has to be selected within the SDK.
First we retrieve a list of vehicles from which we will choose one to interact with. The argument passed has to be a valid access token, thus it needs to be refreshed before.

```kotlin
MBIngressKit.refreshTokenIfRequired()
    .onComplete { token ->
        MBCarKit.vehicleService().fetchVehicles(token.accessToken)
            .onComplete { vehicles ->
                // show vehicle selection
            }.onFailure {
                // fetching vehicles failed
            }
    }.onFailure {
        // token refresh failed
    }
}
```

Now a vehicle has to be selected. In your application you should probably show some UI to the use for the selection process. We just assume 
that a vehicle was chosen. The `vehicle` object in this code block is one of the objects of the above vehicles list.

```kotlin
MBCarKit.selectVehicle(vehicle)
// or
MBCarKit.selectVehicle(vehicle.finOrVin)
``` 

## Connecting to the vehicle

This will demonstrate how to get and observe values using the door state. This value describes if the car doors are locked or unlocked.
First thing you have to do is connecting to the car. For this you have to implement the `SocketConnectionListener` interface. This interface provides callbacks to inform about changes in the connection state.

```kotlin
class MyClass : SocketConnectionListener {
    override fun connectionStateChanged(connectionState: ConnectionState) {
    }
}
```

Now we have to create the connection and register the listener. For ease of use we declare a `connect` function in `MyClass`. This can be called whenever you want to establish the connection to the car.

```kotlin
fun connect() {
    MBIngressKit.refreshTokenIfRequired().onComplete { token ->
        SocketService.connectToWebSocket(
            token.accessToken,
            this
        )
    }.onFailure {
        // token refresh failed
    }
}
```

If everything works as intended the listener should be called with `ConnectionState.Connected`. The state `ConnectionState.Disconnected` is called when the connection is lost.

```kotlin
override fun connectionStateChanged(connectionState: ConnectionState) {
    when (connectionState) {
        is ConnectionState.Connected -> {
            Log.i("MyLogTag", "Connected!")
        }
        is ConnectionState.Disconnected -> {
            Log.i("MyLogTag", "Disconnected!")
        }
    }
}
```

## Retrieving a value once

At this point a reminder to please follow these topics one by one. At least make sure you did [select a vehicle](#selecting-a-vehicle) and [connect to the vehicle](#connecting-to-the-vehicle).
A car has to be selected and connected for the following steps to work out.
  
Now we have a look at how to fetch a value. All the vehicle properties can be fetched in a similar manner. As stated above you can check if the car doors including the decklid are (un)locked.

```kotlin
MBCarKit.selectedFinOrVin()?.let {
    val doorState = MBCarKit.loadCurrentVehicleState(it).doors.lockStateOverall
}
```
`selectedFinOrVin()` will return the previously selected car.

Other vehicle attributes can be fetched in a similar manner.

## Observing a value

A lot of the values of a car can continuously change. A good example is the fuel level. It is desirable to be informed about a change in these values from the SDK without having to actively keep polling. This is possible by observing the value. As an example we will continue with the lock state of the doors to keep consistent.

First you need a `VehicleObserver` which we will create in a member variable in `MyClass`. We also need a member `observables` to be able to do some cleanup when observing is no longer needed.

```kotlin
private val doorsObserver = VehicleObserver.Doors { onUpdate(it) }
private var observables: Observables? = null
```

This observer is initialized with a function that is called when the observed value changes. This function has one parameter of the type of the observed value.

```kotlin
private fun onUpdate(doors: Doors) {
    // do something with the changed doors
}
```

Now we still need to start and stop observing. Best place to do this is when our connection was established. To this end we update the `connectionStateChanged` function from earlier.

```kotlin
override fun connectionStateChanged(connectionState: ConnectionState) {
    when (connectionState) {
        is ConnectionState.Connected -> {
            observables = connectionState.observables
            observables?.observe(doorsObserver)
        }
        is ConnectionState.Disconnected -> {
            observables?.dispose(doorsObserver)
            observables = null
        }
    }
}
```

The initial case is called once with the current value of the observed object. When the state changes a call with state update will be received.

After you finished observing you have to remove the observer. We add a close function that can be called to this cleanup. In it the same steps as in `ConnectionState.Disconnected` within `connectionStateChanged` are necessary.

```kotlin
fun close(){
    observables?.dispose(doorsObserver)
    observables = null
}
```

In the following you will find the full example. You may also take a look into `CarKitRepository` in the example app.

```kotlin
class MyClass : SocketConnectionListener {

    private val doorsObserver = VehicleObserver.Doors { onUpdate(it) }
    private var observables: Observables? = null

    fun connect() {
        MBIngressKit.refreshTokenIfRequired().onComplete { token ->
            SocketService.connectToWebSocket(
                token.accessToken,
                this
            )
        }.onFailure {
            // token refresh failed
        }
    }

    fun close() {
        observables?.dispose(doorsObserver)
        observables = null
    }

    private fun onUpdate(doors: Doors) {
        // do something with the changed doors
    }

    override fun connectionStateChanged(connectionState: ConnectionState) {
        when (connectionState) {
            is ConnectionState.Connected -> {
                observables = connectionState.observables
                observables?.observe(doorsObserver)
            }
            is ConnectionState.Disconnected -> {
                close()
            }
        }
    }
}
```

Make sure you call `connect` and `close` appropriately. Good lifecycle indicators to make these calls would be `onResume()` and `onPause()` within a Fragment or Activity.

## Send car commands

This topic explains how to send commands. A command is a call to the car which is executed by the vehicle. 
Locking doors is a very good use case to demonstrate this functionality.

Commands are called VehicleCommand in the SDK. This is how a command is created and sent to the car. Again make sure you followed [Selecting a vehicle](#selecting-a-vehicle) and [Connecting to the vehicle](#connecting-to-the-vehicle). Sending the following command will lock the selected vehicles doors.

```kotlin
fun lockDoors() {
    MBCarKit.selectedFinOrVin()?.let {
        val vehicleCommand: VehicleCommand.DoorsLock =
            VehicleCommand.DoorsLock(it)

        MBCarKit.sendVehicleCommand(
            vehicleCommand,
            object : VehicleCommandCallback<DoorsLockError> {
                override fun onError(timestamp: Long?, errors: List<DoorsLockError>) {
                    MBLoggerKit.e(errors.toString())
                }

                override fun onSuccess(timestamp: Long?) {
                    MBLoggerKit.i("DoorsLockedEvent successful")
                }

                override fun onUpdate(status: VehicleCommandStatusUpdate) {
                    MBLoggerKit.i("DoorsLockedEvent updated")
                }
            }
        )
    }
}
```
Other commands can be executed in a similar fashion.

## Logout
The SDK provides a convenience method for a logout.

```kotlin
MBMobileSDK.logoutAndCleanUp { 
    // completion
}
```
During the processing of the completion block the internal state of the SDK is still authorized. After processing the completion block the internal state will be updated.
