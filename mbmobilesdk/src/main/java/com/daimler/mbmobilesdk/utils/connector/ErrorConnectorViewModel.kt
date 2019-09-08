package com.daimler.mbmobilesdk.utils.connector

import android.app.Application
import androidx.lifecycle.AndroidViewModel

abstract class ErrorConnectorViewModel(app: Application) : AndroidViewModel(app) {

    private val errorConnectors = mutableListOf<ErrorEditTextConnector>()

    override fun onCleared() {
        super.onCleared()
        errorConnectors.clear()
    }

    protected fun ErrorEditTextConnector.register(): ErrorEditTextConnector {
        if (!errorConnectors.contains(this)) errorConnectors.add(this)
        return this
    }

    protected fun getFirstError(): String? = errorConnectors.firstOrNull { !it.isValid() }?.error()

    protected fun getAllErrors(): String? =
        errorConnectors.filter { !it.isValid() }.joinToString(separator = "\n") { it.error() }
}