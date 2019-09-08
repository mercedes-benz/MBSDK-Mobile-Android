package com.daimler.mbmobilesdk.profile.format

import android.content.Context
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbingresskit.common.User

internal object ProfileFormatters {

    fun createFormatterForUser(context: Context, user: User, fields: List<ProfileField>): ProfileFieldValueFormatter {
        val valueReceiver = ProfileFieldValueReceiverImpl(fields)
        return UserAwareProfileFieldValueFormatter(context, valueReceiver, user)
    }
}