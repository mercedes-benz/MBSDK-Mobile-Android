package com.daimler.mbmobilesdk.utils

import com.daimler.mbmobilesdk.utils.bindings.matches
import java.util.regex.Pattern

sealed class UserValuePolicy(private val validator: Validator) {

    fun isValid(value: String?) = validator(value)

    object Forename : UserValuePolicy(DEFAULT_VALIDATOR)
    object Surname : UserValuePolicy(DEFAULT_VALIDATOR)
    object Mail : UserValuePolicy({ validateWithPattern(PATTERN_MAIL, it?.trim()) })
    object Phone : UserValuePolicy({ validateWithPattern(PATTERN_PHONE, it?.trim()) })
    object Street : UserValuePolicy(DEFAULT_VALIDATOR)
    object HouseNumber : UserValuePolicy(DEFAULT_VALIDATOR)
    object Postcode : UserValuePolicy(DEFAULT_VALIDATOR)
    object City : UserValuePolicy(DEFAULT_VALIDATOR)
    object Country : UserValuePolicy(DEFAULT_VALIDATOR)
    object Pin : UserValuePolicy({ validateWithPattern(PATTERN_PIN, it?.trim()) })

    companion object {

        internal const val USER_PIN_DIGITS = 4
        internal const val BIRTHDAY_DATE_PATTERN = "yyyy-MM-dd"
        internal const val BIRTHDAY_DATE_DISPLAY_PATTERN = "dd.MM.yyyy"
        internal const val MIN_AGE = 10

        private val DEFAULT_VALIDATOR: Validator = { !it.isNullOrBlank() }
        private val PATTERN_MAIL = "[A-Z0-9a-z._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}".toPattern()
        private val PATTERN_PIN = "[0-9]{$USER_PIN_DIGITS}".toPattern()
        private val PATTERN_PHONE = "\\+*[0-9]{7,}".toPattern()

        private fun validateWithPattern(pattern: Pattern, value: String?): Boolean =
            !value.isNullOrBlank() && pattern.matches(value)
    }
}

typealias Validator = (String?) -> Boolean