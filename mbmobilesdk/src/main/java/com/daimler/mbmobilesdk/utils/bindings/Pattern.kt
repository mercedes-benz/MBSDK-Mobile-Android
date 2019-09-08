package com.daimler.mbmobilesdk.utils.bindings

import java.util.regex.Pattern

fun Pattern.matches(value: String) = matcher(value).matches()