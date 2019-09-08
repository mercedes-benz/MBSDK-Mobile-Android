package com.daimler.mbmobilesdk.support

import android.content.Context
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.utils.extensions.blank
import com.daimler.mbmobilesdk.utils.extensions.newLine
import com.daimler.mbingresskit.common.User
import com.daimler.mbcarkit.MBCarKit

internal class SupportMailBuilder {

    val builder = StringBuilder()

    fun appendUserAndAppData(context: Context, user: User?): SupportMailBuilder {
        builder.apply {
            append(context.getString(R.string.support_app_name))
            blank()
            append(appName(context))
            newLine()

            append(context.getString(R.string.support_language))
            blank()
            append(user?.languageCode)
            newLine()

            append(context.getString(R.string.support_first_name))
            blank()
            appendIfNotNull(user?.firstName)
            newLine()

            append(context.getString(R.string.support_last_name))
            blank()
            appendIfNotNull(user?.lastName)
            newLine()

            append(context.getString(R.string.support_street_name))
            blank()
            appendIfNotNull(user?.address?.street)
            newLine()

            append(context.getString(R.string.support_street_number))
            blank()
            appendIfNotNull(user?.address?.houseNumber)
            newLine()

            append(context.getString(R.string.support_city))
            blank()
            appendIfNotNull(user?.address?.city)
            newLine()

            append(context.getString(R.string.support_country))
            blank()
            appendIfNotNull(user?.address?.countryCode)
            newLine()

            append(context.getString(R.string.support_phone))
            blank()
            appendIfNotNull(user?.mobilePhone)
            newLine()

            append(context.getString(R.string.support_mail))
            blank()
            appendIfNotNull(user?.email)
            newLine()

            append(context.getString(R.string.support_device))
            blank()
            append(android.os.Build.MODEL)
            newLine()

            append(context.getString(R.string.support_os))
            blank()
            append("Android: ${android.os.Build.VERSION.RELEASE}")
            newLine()

            append(context.getString(R.string.support_app_version))
            blank()
            append(MBMobileSDK.appVersion())
            newLine()

            append(context.getString(R.string.support_selected_vehicle))
            blank()
            appendIfNotNull(MBCarKit.selectedFinOrVin())
            newLine()

            append(context.getString(R.string.support_session))
            blank()
            append(MBMobileSDK.appSessionId())
        }
        return this
    }

    override fun toString(): String {
        return builder.toString()
    }

    fun newLine(): SupportMailBuilder {
        builder.newLine()
        return this
    }

    private fun appName(context: Context): String {
        val info = context.applicationInfo
        val resId = info.labelRes
        return if (resId == 0) info.nonLocalizedLabel.toString() else context.getString(resId)
    }

    private fun StringBuilder.appendIfNotNull(text: String?) {
        if (text != null) append(text)
    }
}