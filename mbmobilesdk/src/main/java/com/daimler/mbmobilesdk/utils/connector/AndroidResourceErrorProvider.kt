package com.daimler.mbmobilesdk.utils.connector

import android.content.Context
import androidx.annotation.StringRes

internal class AndroidResourceErrorProvider(
    private val context: Context,
    @StringRes private val resId: Int
) : ConnectorErrorProvider {

    override fun createErrorMessage(): String = context.getString(resId)
}