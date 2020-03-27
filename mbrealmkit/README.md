<!-- SPDX-License-Identifier: MIT -->

![MBRealmKit](logo.jpg "Banner")

![License](https://img.shields.io/badge/License-MIT-green)
![Platform](https://img.shields.io/badge/Platforms-Android-blue)

## Requirements
* __Minimum Android SDK:__ MBRealmKit requires a minimum API level of 23.
* __Compile Android SDK:__ MBRealmKit requires you to compile against minimum API level 30.

## Intended Usage

This module contains convenient APIs for the usage of `Realm`.

### Create a database
1. Create your model class
```kotlin
open class RealmUser : RealmObject() {

    @PrimaryKey
    var id: String = ""

    var name: String? = null
}
```

2. Define a module. A module can be any class, it just needs the `@RealmModule` annotation.
```kotlin
@RealmModule(classes = [RealmUser::class])
class RealmModule
```
3. Initialize Realm with your application context
```kotlin
MBRealmKit.apply {
    // Create a plain realm.
    createRealmInstance(
        "id_my_plain_realm",
        RealmServiceConfig.Builder(appContext, 1L, RealmModule())
            .build())
    // Create an encrypted realm.
    createRealmInstance(
        "id_my_encrypted_realm",
        RealmServiceConfig.Builder(appContext, 1L, RealmModule())
            .encrypt()
            .build())
}
```
4. Access to the created realm: You can access your realms with the id you provided in `createRealmInstance()`:
```kotlin
// Get realm.
val realm = MBRealmKit.realm(REALM_ID)

//
val users = realm.where<RealmUser>().findAll()
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
implementation "com.daimler.mm:MBRealmKit:$mb_realm_kit_version"
```