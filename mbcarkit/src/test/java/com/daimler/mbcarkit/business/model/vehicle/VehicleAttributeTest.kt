package com.daimler.mbcarkit.business.model.vehicle

import com.daimler.mbcarkit.business.model.valetprotect.DistanceUnit
import com.daimler.mbcarkit.business.model.vehicle.unit.DisplayUnitCase
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.util.Locale

@ExtendWith(SoftAssertionsExtension::class)
class VehicleAttributeTest {

    @ParameterizedTest
    @ValueSource(strings = ["de", "it", "es", "pt"])
    fun testLocalizedDisplayValueForNonEnglish(language: String, softly: SoftAssertions) {
        val locale = Locale.forLanguageTag(language)
        softly.assertThat(getUnitWith("").localisedDisplayValue(locale)).isEqualTo("")
        softly.assertThat(getUnitWith("1").localisedDisplayValue(locale)).isEqualTo("1")
        softly.assertThat(getUnitWith("817").localisedDisplayValue(locale)).isEqualTo("817")
        softly.assertThat(getUnitWith("-1").localisedDisplayValue(locale)).isEqualTo("-1")
        softly.assertThat(getUnitWith("1.0").localisedDisplayValue(locale)).isEqualTo("1,0")
        softly.assertThat(getUnitWith("-1.0").localisedDisplayValue(locale)).isEqualTo("-1,0")
        softly.assertThat(getUnitWith("1.").localisedDisplayValue(locale)).isEqualTo("1")
        softly.assertThat(getUnitWith("-1.").localisedDisplayValue(locale)).isEqualTo("-1")
        softly.assertThat(getUnitWith("1.00000").localisedDisplayValue(locale)).isEqualTo("1,00000")
        softly.assertThat(getUnitWith("-1.00000").localisedDisplayValue(locale)).isEqualTo("-1,00000")
        softly.assertThat(getUnitWith("1000.000").localisedDisplayValue(locale)).isEqualTo("1.000,000")
        softly.assertThat(getUnitWith("-1000.000").localisedDisplayValue(locale)).isEqualTo("-1.000,000")
        softly.assertThat(getUnitWith("1000000.000").localisedDisplayValue(locale)).isEqualTo("1.000.000,000")
        softly.assertThat(getUnitWith("-1000000.000").localisedDisplayValue(locale)).isEqualTo("-1.000.000,000")
    }

    @ParameterizedTest
    @ValueSource(strings = ["fr", "nn"])
    fun testLocalizedDisplayValueWithSpaces(language: String, softly: SoftAssertions) {
        val locale = Locale.forLanguageTag(language)
        softly.assertThat(getUnitWith("").localisedDisplayValue(locale)).isEqualTo("")
        softly.assertThat(getUnitWith("1").localisedDisplayValue(locale)).isEqualTo("1")
        softly.assertThat(getUnitWith("817").localisedDisplayValue(locale)).isEqualTo("817")
        softly.assertThat(getUnitWith("-1").localisedDisplayValue(locale)).isEqualTo("-1")
        softly.assertThat(getUnitWith("1.0").localisedDisplayValue(locale)).isEqualTo("1,0")
        softly.assertThat(getUnitWith("-1.0").localisedDisplayValue(locale)).isEqualTo("-1,0")
        softly.assertThat(getUnitWith("1.").localisedDisplayValue(locale)).isEqualTo("1")
        softly.assertThat(getUnitWith("-1.").localisedDisplayValue(locale)).isEqualTo("-1")
        softly.assertThat(getUnitWith("1.00000").localisedDisplayValue(locale)).isEqualTo("1,00000")
        softly.assertThat(getUnitWith("-1.00000").localisedDisplayValue(locale)).isEqualTo("-1,00000")
        // Space character is not the same as in the IDE
        softly.assertThat(getUnitWith("-1000.000").localisedDisplayValue(locale)).isEqualTo("-1\u00A0000,000")
        softly.assertThat(getUnitWith("1000000.000").localisedDisplayValue(locale)).isEqualTo("1\u00A0000\u00A0000,000")
        softly.assertThat(getUnitWith("-1000000.000").localisedDisplayValue(locale)).isEqualTo("-1\u00A0000\u00A0000,000")
    }

    @Test
    fun testLocalizedDisplayValueForEnglish(softly: SoftAssertions) {
        val locale = Locale.forLanguageTag("en")
        softly.assertThat(getUnitWith("1").localisedDisplayValue(locale)).isEqualTo("1")
        softly.assertThat(getUnitWith("817").localisedDisplayValue(locale)).isEqualTo("817")
        softly.assertThat(getUnitWith("-1").localisedDisplayValue(locale)).isEqualTo("-1")
        softly.assertThat(getUnitWith("1.0").localisedDisplayValue(locale)).isEqualTo("1.0")
        softly.assertThat(getUnitWith("-1.0").localisedDisplayValue(locale)).isEqualTo("-1.0")
        softly.assertThat(getUnitWith("1.").localisedDisplayValue(locale)).isEqualTo("1")
        softly.assertThat(getUnitWith("-1.").localisedDisplayValue(locale)).isEqualTo("-1")
        softly.assertThat(getUnitWith("1.00000").localisedDisplayValue(locale)).isEqualTo("1.00000")
        softly.assertThat(getUnitWith("-1.00000").localisedDisplayValue(locale)).isEqualTo("-1.00000")
        softly.assertThat(getUnitWith("1000.000").localisedDisplayValue(locale)).isEqualTo("1,000.000")
        softly.assertThat(getUnitWith("-1000.000").localisedDisplayValue(locale)).isEqualTo("-1,000.000")
        softly.assertThat(getUnitWith("1000000.000").localisedDisplayValue(locale)).isEqualTo("1,000,000.000")
        softly.assertThat(getUnitWith("-1000000.000").localisedDisplayValue(locale)).isEqualTo("-1,000,000.000")
    }

    @Test
    fun testInvalidDisplayValuesForLocalization() {
        assertThrows<NumberFormatException>() {
            getUnitWith("abc").localisedDisplayValue(Locale.getDefault())
        }

        assertThrows<NumberFormatException>() {
            getUnitWith("10k").localisedDisplayValue(Locale.getDefault())
        }
    }

    private fun getUnitWith(displayValue: String) = VehicleAttribute.Unit(displayValue, DisplayUnitCase.DISTANCE_UNIT, DistanceUnit.KM)
}
