package com.daimler.mbmobilesdk.support

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.support.models.ImageItem
import com.daimler.mbmobilesdk.support.models.RealImage
import com.daimler.mbmobilesdk.utils.extensions.toByteArray
import com.daimler.mbmobilesdk.utils.extensions.toCacDateTimeString
import com.daimler.mbingresskit.common.User
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList
import java.util.*

/**
 * Utils class for Support.
 * It helps to request permissions or to generate Mails
 */
object SupportUtils {

    /**
     * This is to simplify the permission requests
     */
    fun requestPermissions(activity: Activity, requestCode: Int, vararg permissions: String) {
        val permissionRequests: Array<String> = permissions
                .filter { ContextCompat.checkSelfPermission(activity, it) != PackageManager.PERMISSION_GRANTED }
                .toTypedArray()

        if (permissionRequests.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity, permissionRequests, requestCode)
        }
    }

    /**
     * Returns the Mail Subject
     */
    fun getMailSubject(context: Context): String {
        val market = MBMobileSDK.userLocale().countryCode
        return "[App Family][${getAppName(context)}][$market]"
    }

    /**
     * Returns the Mail Body
     */
    fun getMailBody(context: Context, question: String, botAnswer: String, vin: String?, occurredAt: Date?, user: User?, sessionID: String?): String {
        val dateString = occurredAt?.toCacDateTimeString() ?: ""
        val ciamId = user?.ciamId ?: ""
        val userLocale = MBMobileSDK.userLocale()

        return "question: \"$question\"\n" +
                "botAnswer: \"$botAnswer\"\n" +
                "vin: \"${vin ?: ""}\"\n" +
                "userID: \"$ciamId\"\n" +
                "market: \"${userLocale.countryCode}\"\n" +
                "language: \"${userLocale.languageCode}\"\n" +
                "sessionID: \"${sessionID ?: ""}\"\n" +
                "occurredAt: \"${dateString}\"\n" +
                "appName: \"${getAppName(context)}\"\n" +
                "appVersion: \"${getAppVersion(context)}\"\n" +
                "deviceName: \"${getDeviceName()}\"\n" +
                "os: \"${getOsName()}\"\n"
    }

    fun getAppVersion(context: Context): String {
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            return pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return "unknown"
    }

    /**
     * returns manufacturer (e.g. Samsung), product (Galaxy S2), model (GT-I9100)
     */
    fun getDeviceName(): String {
        return "${Build.MANUFACTURER}:${Build.PRODUCT} (${Build.MODEL})"
    }

    /**
     * returns the OS Name
     * if we don't get one we will return the Release
     */
    fun getOsName(): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Build.VERSION.BASE_OS.isNotEmpty())
                return "${Build.VERSION.BASE_OS} ${Build.VERSION.RELEASE}"
        }
        return "Android ${Build.VERSION.RELEASE}"
    }

    fun getAppName(context: Context): String {
        val stringId = context.applicationInfo.labelRes
        if (stringId == 0)
            return context.applicationInfo.nonLocalizedLabel.toString()
        return context.getString(stringId)
    }

    /**
     * Returns a List of Base64 encoded images as Strings
     */
    fun makeAttachmentList(imageList: MutableLiveArrayList<ImageItem>): List<String> {
        return imageList.value.mapNotNull {
            if (it is RealImage) {
                Base64.encodeToString(it.realImageFile.toByteArray(90), Base64.DEFAULT)
            } else null
        }
    }
}