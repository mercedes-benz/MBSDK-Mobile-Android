package com.daimler.mbmobilesdk.implementation

import com.daimler.mbmobilesdk.business.ProfileFieldsLoader
import com.daimler.mbingresskit.common.ProfileFieldsData
import com.daimler.mbingresskit.login.UserService
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

internal class UserServiceProfileFieldsLoader(
    private val userService: UserService
) : ProfileFieldsLoader {

    override fun loadFields(countryCode: String, locale: String): FutureTask<ProfileFieldsData, ResponseError<out RequestError>?> {
        return userService.fetchProfileFields(countryCode)
    }
}