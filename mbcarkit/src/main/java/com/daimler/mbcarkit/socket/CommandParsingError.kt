package com.daimler.mbcarkit.socket

class CommandParsingError(message: String, cause: Throwable? = null) : IllegalArgumentException(message, cause)
