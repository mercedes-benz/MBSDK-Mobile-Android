package com.daimler.mbingresskit.common

class AuthScope @JvmOverloads constructor(
    /**
     * Additional scopes. Should be a string that contains multiple space-delimited scopes.
     */
    additionalScopes: String = "",
    /**
     * Number of predefined scopes.
     */
    vararg scope: Scope
) {

    val formattedString = asFormatedString(scope, additionalScopes)

    override fun toString(): String = formattedString

    private fun asFormatedString(scope: Array<out Scope>, additionalScopes: String): String = scope
        .map { it.value }
        .plus(additionalScopes)
        .filter { it.isNotEmpty() }
        .distinct()
        .joinToString(" ")
}
