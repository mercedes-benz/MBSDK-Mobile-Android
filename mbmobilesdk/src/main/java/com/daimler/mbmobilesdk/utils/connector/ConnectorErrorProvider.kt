package com.daimler.mbmobilesdk.utils.connector

/**
 * Used as a provider for error messages for the input of EditTextConnectors.
 */
interface ConnectorErrorProvider {

    /**
     * Returns the error message.
     */
    fun createErrorMessage(): String
}