import org.gradle.api.JavaVersion

object Config {
    const val minSdk = 23
    const val compileSdk = 30
    const val targetSdk = 30
    const val buildTools = "29.0.2"
    val javaVersion = JavaVersion.VERSION_1_8
}

object Versions {
    const val kotlin = "1.4.0"
    const val androidx_ext_junit = "1.1.2"
    const val androidx_espresso = "3.3.0"
    const val androidx_browser = "1.2.0"
    const val androidx_annotations = "1.1.0"
    const val androidx_appcompat = "1.2.0"
    const val androidx_constraintlayout = "2.0.1"
    const val androidx_lifecycle = "2.2.0"
    const val androidx_core = "1.3.1"
    const val androidx_mulitdex = "2.0.1"
    const val androidx_databinding = "4.0.1"
    const val google_material = "1.2.1"
    const val google_protobuf = "3.4.0"
    const val android_auth = "2.0.0"
    const val microsoft_appcenter = "3.2.1"
    const val mock_webserver = "4.4.0"
    const val mockk = "1.10.0"
    const val junit = "4.12"
    const val assertj = "3.16.1"
    const val gradle_plugin = "4.0.1"
    const val dokka = "0.9.17"
    const val protobuf_plugin = "0.8.11"
    const val artificatory = "4.13.0"
    const val realm = "7.0.2"
    const val jacoco = "0.2"
    const val gson = "2.8.6"
    const val retrofit = "2.9.0"
    const val http3_interceptor = "3.11.0"
    const val kotlin_reflect = "1.4.0"
    const val kotlin_coroutines = "1.3.9"
    const val recycler_view = "1.1.0"
    const val jupiter_params = "5.6.0"
    const val jupiter_api = "5.6.2"
    const val jupiter_engine = "5.6.2"
    const val vintage_engine = "5.6.0"
    const val guava = "29.0-jre"
    const val ktlint = "0.39.0"
}

object Deps {
    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val androidx_browser = "androidx.browser:browser:${Versions.androidx_browser}"
    const val androidx_annotations =
        "androidx.annotation:annotation:${Versions.androidx_annotations}"
    const val androidx_appcompat = "androidx.appcompat:appcompat:${Versions.androidx_appcompat}"
    const val androidx_constraintlayout =
        "androidx.constraintlayout:constraintlayout:${Versions.androidx_constraintlayout}"
    const val androidx_lifecycle_extensions =
        "androidx.lifecycle:lifecycle-extensions:${Versions.androidx_lifecycle}"
    const val androidx_lifecycle_runtime =
        "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.androidx_lifecycle}"
    const val androidx_lifecycle_viewmodel =
        "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidx_lifecycle}"
    const val androidx_lifecycle_livedata =
        "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.androidx_lifecycle}"
    const val androidx_core = "androidx.core:core-ktx:${Versions.androidx_core}"
    const val androidx_mulitdex = "androidx.multidex:multidex:${Versions.androidx_mulitdex}"
    const val androidx_databinding =
        "androidx.databinding:databinding-compiler:${Versions.androidx_databinding}"
    const val androidx_recyclerview = "androidx.recyclerview:recyclerview:${Versions.recycler_view}"
    const val android_material = "com.google.android.material:material:${Versions.google_material}"
    const val android_auth = "com.auth0.android:jwtdecode:${Versions.android_auth}"
    const val google_protobuf = "com.google.protobuf:protobuf-java:${Versions.google_protobuf}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson_converter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val scalars_converter = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
    const val logging_interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.http3_interceptor}"
    const val kotlin_reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin_reflect}"
    const val kotlin_coroutines =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.kotlin_coroutines}"
    const val kotlin_coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.kotlin_coroutines}"
    const val guava = "com.google.guava:guava:${Versions.guava}"


    // Deployment
    const val appcenter =
        "com.microsoft.appcenter:appcenter-crashes:${Versions.microsoft_appcenter}"

    // Testing
    const val androidx_test_junit = "androidx.test.ext:junit:${Versions.androidx_ext_junit}"
    const val androidx_test_espresso =
        "androidx.test.espresso:espresso-core:${Versions.androidx_espresso}"
    const val mockserver = "com.squareup.okhttp3:mockwebserver:${Versions.mock_webserver}"
    const val mockk = "io.mockk:mockk:${Versions.mockk}"
    const val junit = "junit:junit:${Versions.junit}"
    const val assertj = "org.assertj:assertj-core:${Versions.assertj}"
    const val jupiter_params = "org.junit.jupiter:junit-jupiter-params:${Versions.jupiter_params}"
    const val jupiter_api = "org.junit.jupiter:junit-jupiter-api:${Versions.jupiter_api}"
    const val jupiter_engine = "org.junit.jupiter:junit-jupiter-engine:${Versions.jupiter_engine}"
    const val vintage_engine = "org.junit.vintage:junit-vintage-engine:${Versions.vintage_engine}"
    const val kotlin_coroutines_test =
        "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.kotlin_coroutines}"

    // Tools
    const val tool_gradle = "com.android.tools.build:gradle:${Versions.gradle_plugin}"
    const val tool_kotlin_gradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val tool_dokka_gradle =
        "org.jetbrains.dokka:dokka-android-gradle-plugin:${Versions.dokka}"
    const val tool_protobuf_gradle =
        "com.google.protobuf:protobuf-gradle-plugin:${Versions.protobuf_plugin}"
    const val tool_jfrog_buildinfo =
        "org.jfrog.buildinfo:build-info-extractor-gradle:${Versions.artificatory}"
    const val tool_realm_gradle = "io.realm:realm-gradle-plugin:${Versions.realm}"
    const val tool_jacoco_plugin = "com.hiya:jacoco-android:${Versions.jacoco}"
}
