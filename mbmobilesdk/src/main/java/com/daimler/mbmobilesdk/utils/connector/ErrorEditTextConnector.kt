package com.daimler.mbmobilesdk.utils.connector

import com.daimler.mbuikit.utils.connectors.EditTextConnector

/**
 * [EditTextConnector] subclass that provides the possibility to provide an [ConnectorErrorProvider]
 * to receive error messages for the input of this connector.
 */
class ErrorEditTextConnector(
    initialValue: String = "",
    isMandatory: Boolean = false,
    private val errorProvider: ConnectorErrorProvider? = null,
    validator: (String?) -> Boolean = { true }
) : EditTextConnector(initialValue, isMandatory, validator) {

    fun error() = errorProvider?.createErrorMessage()
        ?: throw IllegalArgumentException("Cannot create error message when no ErrorProvider was given.")
}