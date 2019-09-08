package com.daimler.mbmobilesdk.profile.fields

sealed class ProfileFieldResolutionStrategy {

    class All(
        val exclusions: List<Class<out ProfileField>> = emptyList()
    ) : ProfileFieldResolutionStrategy()

    class Mandatory(
        val inclusions: List<Class<out ProfileField>> = emptyList(),
        val exclusions: List<Class<out ProfileField>> = emptyList()
    ) : ProfileFieldResolutionStrategy()

    class Only(
        val inclusions: List<Class<out ProfileField>> = emptyList()
    ) : ProfileFieldResolutionStrategy()
}

class ResolutionMandatoryBuilder : ResolutionStrategyBuilder() {

    inline fun <reified T : ProfileField> include(): ResolutionMandatoryBuilder {
        includeField<T>()
        return this
    }

    inline fun <reified T : ProfileField> exclude(): ResolutionMandatoryBuilder {
        excludeField<T>()
        return this
    }

    fun build() =
        ProfileFieldResolutionStrategy.Mandatory(inclusions, exclusions)
}

class ResolutionAllBuilder : ResolutionStrategyBuilder() {

    inline fun <reified T : ProfileField> exclude(): ResolutionAllBuilder {
        excludeField<T>()
        return this
    }

    fun build() =
        ProfileFieldResolutionStrategy.All(exclusions)
}

class ResolutionOnlyBuilder : ResolutionStrategyBuilder() {

    inline fun <reified T : ProfileField> include(): ResolutionOnlyBuilder {
        includeField<T>()
        return this
    }

    fun build() =
        ProfileFieldResolutionStrategy.Only(inclusions)
}

abstract class ResolutionStrategyBuilder {

    val inclusions = mutableListOf<Class<out ProfileField>>()
    val exclusions = mutableListOf<Class<out ProfileField>>()

    inline fun <reified T : ProfileField> includeField() {
        inclusions.add(T::class.java)
    }

    inline fun <reified T : ProfileField> excludeField() {
        exclusions.add(T::class.java)
    }
}