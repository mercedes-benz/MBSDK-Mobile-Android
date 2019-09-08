package com.daimler.mbmobilesdk.biometric.auth

internal class BiometricDialogConfig private constructor(
    val title: String,
    val subtitle: String?,
    val description: String?,
    val negativeButtonText: String
) {

    class Builder(private val title: String, private val negativeButtonText: String) {

        private var subtitle: String? = null
        private var description: String? = null

        fun useSubtitle(subtitle: String): Builder {
            this.subtitle = subtitle
            return this
        }

        fun useDescription(description: String): Builder {
            this.description = description
            return this
        }

        fun build() =
            BiometricDialogConfig(title, subtitle, description, negativeButtonText)
    }
}