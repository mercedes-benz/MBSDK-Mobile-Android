package com.daimler.mbmobilesdk.utils.extensions

internal fun Int.hasFlag(flag: Int) = this.and(flag) == flag