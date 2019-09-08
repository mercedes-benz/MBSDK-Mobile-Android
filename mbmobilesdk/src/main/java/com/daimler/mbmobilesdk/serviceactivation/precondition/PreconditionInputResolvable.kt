package com.daimler.mbmobilesdk.serviceactivation.precondition

import com.daimler.mbmobilesdk.serviceactivation.views.FieldResolvable

internal interface PreconditionInputResolvable {

    fun resolve(resolver: PreconditionInputResolver, resolvable: FieldResolvable)
}