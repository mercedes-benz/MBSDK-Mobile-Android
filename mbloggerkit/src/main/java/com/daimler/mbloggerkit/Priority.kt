package com.daimler.mbloggerkit

enum class Priority(val level: Int) {
    VERBOSE(LEVEL_VERBOSE),
    DEBUG(LEVEL_DEBUG),
    INFO(LEVEL_INFO),
    WARN(LEVEL_WARN),
    ERROR(LEVEL_ERROR),
    WTF(LEVEL_WTF);
}

const val LEVEL_VERBOSE = 2
const val LEVEL_DEBUG = 3
const val LEVEL_INFO = 4
const val LEVEL_WARN = 5
const val LEVEL_ERROR = 6
const val LEVEL_WTF = 7
