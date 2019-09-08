<!-- SPDX-License-Identifier: MIT -->

![MBMobileSDK](logo.jpg "Banner")

![License](https://img.shields.io/badge/License-MIT-green)
![Platform](https://img.shields.io/badge/Platforms-Android-blue)
![Version](https://img.shields.io/badge/Azure%20Artifacts-1.0--iaa-orange)

## Requirements
* __Minimum Android SDK:__ MBMobileSDK requires a minimum API level of 21. 
* __Compile Android SDK:__ MBMobileSDK requires you to compile against
  minimum API level 28.

## Intended Usage
The MBMobileSDK module includes all other MBSDK modules and provides
common features such as a build in login view, a registration view, a
view to handle the profile or a side menu. It also contains provider for
general functionality, e.g. to handle the pin, the biometric, the token
or the encryptiions.

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
implementation "com.daimler.mm:MBMobileSDK:$mb_mobile_sdk_version"
```

## Building
In order to build & run the sample locally you need valid token and
secrets for Jumio and LaunchDarkly. Add the following lines to your
`local.properties` file:
```gradle
# LaunchDarkly
LD_API_DEV=<enter dev token>
LD_API_PROD=<enter prod token>

# Jumio
JUMIO_API_TOKEN=<enter dev token>
JUMIO_API_SECRET=<enter prod token>
```

## Contributing

We welcome any contributions.
If you want to contribute to this project, please read the [contributing guide](CONTRIBUTING.md).

## Code of Conduct

Please read our [Code of Conduct](https://github.com/Daimler/daimler-foss/blob/master/CODE_OF_CONDUCT.md) as it is our base for interaction.

## License

This project is licensed under the [MIT LICENSE](LICENSE).

## Provider Information

Please visit <https://mbition.io/en/home/index.html> for information on the provider.

Notice: Before you use the program in productive use, please take all
necessary precautions, e.g. testing and verifying the program with
regard to your specific use. The program was tested solely for our own
use cases, which might differ from yours.