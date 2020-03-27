package com.daimler.mbingresskit.common

/**
 * Some pre-defined scopes which are supported by CIAM.
 */
enum class Scope(val value: String) {
    OPEN_ID("openid"),
    PROFILE("profile"),
    EMAIL("email"),
    PHONE("phone"),
    CIAM("ciam-uid");
}
