package com.daimler.mbingresskit.common

enum class QueryParam(val value: String) {
    TOKEN("token"),
    PROMPT("prompt"),
    EMAIL("email"),
    LANGUAGE("lang"),
    CALLBACK_URI("callback_uri"),
    APP_ID("app-id"),
    CLIENT_ID("client_id"),
    LOGOUT_REDIRECT("post_logout_redirect_uri")
}
