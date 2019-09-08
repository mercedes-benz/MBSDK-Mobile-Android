package com.daimler.mbmobilesdk.serviceactivation.precondition

internal interface MissingFieldCreatable {

    fun create(resolvableCreator: MissingFieldResolvableCreator)
}