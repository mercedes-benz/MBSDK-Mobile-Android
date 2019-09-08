package com.daimler.mbmobilesdk.utils.extensions

import com.daimler.mbuikit.utils.connectors.EditTextConnector

inline fun EditTextConnector.ifValidElse(ifValid: (String) -> Unit, ifNotValid: (String?) -> Unit) =
    if (isValid()) ifValid(value ?: "") else ifNotValid(value)

inline fun EditTextConnector.ifValid(ifValid: (String) -> Unit) = ifValidElse(ifValid, {})