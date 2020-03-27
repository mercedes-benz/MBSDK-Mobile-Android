<!-- SPDX-License-Identifier: MIT -->

![MBLoggerKit](logo.jpg "Banner")


![License](https://img.shields.io/badge/Licence-MIT-green)
![Platform](https://img.shields.io/badge/Platforms-Android-blue)

## Requirements
* __Minimum Android SDK:__ MBLoggerKit requires a minimum API level of 23.
* __Compile Android SDK:__ MBLoggerKit requires you to compile against minimum API level 30.

## Intended Usage

This module contains the Logger used by all other Mercedes-Benz Mobile SDK modules.
Initialize it the following way in your `Application` subclass:
```kotlin
MBLoggerKit.usePrinterConfig(
    PrinterConfig.Builder()
        .addAdapter(AndroidLogAdapter.Builder()
            .setLoggingEnabled(loggingEnabled)
            .build()
        )
        .build()
)
```
Log your statements as you would with standard Android logs but use the class `MBLoggerKit` instead of `Log`,
e.g. `MBLoggerKit.d("My debug log statement.")`.

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
implementation "com.daimler.mm:MBLoggerKit:$mb_logger_kit_version"
```