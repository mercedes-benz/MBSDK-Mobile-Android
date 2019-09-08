package com.daimler.mbmobilesdk.utils.extensions

import com.daimler.mbmobilesdk.utils.UserValuePolicy
import com.daimler.mbmobilesdk.utils.connector.ConnectorErrorProvider
import com.daimler.mbmobilesdk.utils.connector.ErrorEditTextConnector
import com.daimler.mbuikit.utils.connectors.EditTextConnector

/**
 * Creates an [EditTextConnector] that uses the same validation function as this [UserValuePolicy].
 */
fun UserValuePolicy.toConnector(value: String = "", isMandatory: Boolean = false) =
    EditTextConnector(value, isMandatory) { isValid(it) }

/**
 * Creates an [ErrorEditTextConnector] that uses the same validation function as this [UserValuePolicy].
 */
fun UserValuePolicy.toConnector(
    value: String = "",
    isMandatory: Boolean = false,
    errorProvider: ConnectorErrorProvider
) = ErrorEditTextConnector(value, isMandatory, errorProvider) { isValid(it) }