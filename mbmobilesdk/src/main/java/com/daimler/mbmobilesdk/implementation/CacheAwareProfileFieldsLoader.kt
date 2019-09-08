package com.daimler.mbmobilesdk.implementation

import android.os.Handler
import com.daimler.mbmobilesdk.business.ProfileFieldsLoader
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.ProfileFieldsData
import com.daimler.mbingresskit.login.UserService
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

internal class CacheAwareProfileFieldsLoader(
    private val userService: UserService
) : ProfileFieldsLoader {

    override fun loadFields(
        countryCode: String,
        locale: String
    ): FutureTask<ProfileFieldsData, ResponseError<out RequestError>?> {
        val cachedFields = MBIngressKit.cachedProfileFields(countryCode, locale)
        return cachedFields?.let {
            val deferredTask = TaskObject<ProfileFieldsData, ResponseError<out RequestError>?>()
            Handler().post {
                deferredTask.complete(it)
            }
            deferredTask.futureTask()
        } ?: loadFieldsFromApi(countryCode)
    }

    private fun loadFieldsFromApi(
        countryCode: String
    ): FutureTask<ProfileFieldsData, ResponseError<out RequestError>?> {
        return userService.fetchProfileFields(countryCode)
    }
}