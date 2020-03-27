package com.daimler.mbingresskit.implementation.network.model

import com.google.gson.annotations.SerializedName

internal enum class ApiKeycloakError {
    /** Request is missing a parameter or contains an unsupported parameter of repeats parameters. */
    @SerializedName("invalid_request")
    INVALID_REQUEST,

    /** Client authentication fails due to an invalid client id or secret. */
    @SerializedName("invalid_client")
    INVALID_CLIENT,

    /** Authorization code or password is invalid or expired. */
    @SerializedName("invalid_grant")
    INVALID_GRANT,

    /** Invalid scope for the given access token. */
    @SerializedName("invalid_scope")
    INVALID_SCOPE,

    /** Client is not authorized for requested grant type. */
    @SerializedName("unauthorized_client")
    UNAUTHORIZED_CLIENT,

    /** Given grant type is unsupported. */
    @SerializedName("unsupported_grant_type")
    UNSUPPORTED_GRANT_TYPE
}
