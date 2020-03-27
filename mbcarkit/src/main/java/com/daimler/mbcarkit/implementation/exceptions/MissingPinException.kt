package com.daimler.mbcarkit.implementation.exceptions

import java.lang.RuntimeException

/**
 * Exception signaling that a pin is required for a command but was not provided.
 */
class MissingPinException() : RuntimeException()
