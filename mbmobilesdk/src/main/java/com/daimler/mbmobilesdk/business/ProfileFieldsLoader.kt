package com.daimler.mbmobilesdk.business

import com.daimler.mbingresskit.common.ProfileFieldsData
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

internal interface ProfileFieldsLoader {

    fun loadFields(
        countryCode: String,
        locale: String
    ): FutureTask<ProfileFieldsData, ResponseError<out RequestError>?>
}