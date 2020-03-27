package com.daimler.mbingresskit.implementation.network.model

import com.google.gson.annotations.SerializedName

class BffJwtTokenResponse(
    @SerializedName("token") val token: String
)
