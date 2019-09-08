package com.daimler.mbmobilesdk.serviceactivation.precondition

interface ServicePreconditionPreparation {

    fun preconditions(): List<ServicePreconditionType>
}