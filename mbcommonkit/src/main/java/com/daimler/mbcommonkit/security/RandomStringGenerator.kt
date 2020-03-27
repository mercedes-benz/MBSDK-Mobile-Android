package com.daimler.mbcommonkit.security

import java.util.Random

class RandomStringGenerator {

    fun generateString(length: Int): String {
        val minLength = 1
        if (length < minLength) throw IllegalArgumentException("Min length is $minLength")
        var generated = ""
        val random = Random()
        for (i in 1..length) {
            generated += DEFAULT_CHARSET[random.nextInt(DEFAULT_CHARSET.length)]
        }
        return generated
    }

    private companion object {
        const val DEFAULT_CHARSET =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQURSTUWXYZ!?=%&{[]}"
    }
}
