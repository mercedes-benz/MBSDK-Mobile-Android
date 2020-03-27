<!-- SPDX-License-Identifier: MIT -->

![MBCarKit](logo.jpg "Banner")

![License](https://img.shields.io/badge/License-MIT-green)
![Platform](https://img.shields.io/badge/Platforms-Android-blue)

## Requirements
* __Minimum Android SDK:__ MBCarKit requires a minimum API level of 23.
* __Compile Android SDK:__ MBCarKit requires you to compile against minimum API level 30.

## Intended Usage

MBCarKit provides you services and functions for communication with a vehicle. This includes vehicle commands, REST APIs and
an implemented socket connection and handling for vehicle attribute updates.

### Initialization
If you use `MBMobileSDK`, it will take care of these calls internally. The following only applies if you use MBCarKit in isolation.

Initialize MBCarKit within your application class. It requires the initialization of `MBNetworkKit`.

First, initialize `MBCarKit`:
```kotlin
MBCarKit.init(
    MBCarKitServiceConfig.Builder(
        appContext, "url_to_apis", MBNetworkKit.headerService()
    ).usePinProvider(MyPinProvider())
    .build()
)
```

Afterwards, initialize the `SocketService`:
```kotlin
SocketService.init(
    SocketServiceConfig.Builder(
        "url_to_socket",
        MBCarKit.createMycarMessageProcessor(
            MyPinCommandVehicleApiStatusCallback(),
            MBCarKit.createServiceMessageProcessor(MyMessageProcessor())
        )
    )
)
```

## Installation

Add the following maven url to your project `build.gradle`:
```gradle
allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url 'https://pkgs.dev.azure.com/daimler-ris/sdk/_packaging/release/maven/v1'
        }
    }
}
```

Add to your app's `build.gradle`:
```gradle
implementation "com.daimler.mm:MBCarKit:$mb_car_kit_version"
```

## Building
In order to build & run the sample locally you need a valid plain JWT
token to execute REST requests provided by MBCarKit.
Additionally, for vehicle related functions, you need to provide a FIN
or VIN of a vehicle.
Add the following lines to your `local.properties` file:
```gradle
TEST_TOKEN=<my_jwt_token>
TEST_VIN=<my_fin_or_vin>
```