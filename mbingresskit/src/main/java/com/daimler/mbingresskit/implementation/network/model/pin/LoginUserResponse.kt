package com.daimler.mbingresskit.implementation.network.model.pin

import com.daimler.mbingresskit.common.LoginUser
import com.daimler.mbnetworkkit.common.Mappable
import com.google.gson.annotations.SerializedName

internal data class LoginUserResponse(
    @SerializedName("username") val userName: String,
    @SerializedName("isEmail") val isEmail: Boolean
) : Mappable<LoginUser> {

    override fun map(): LoginUser = toLoginUser()
}

internal fun LoginUserResponse.toLoginUser() = LoginUser(
    userName = userName,
    isMail = isEmail
)
