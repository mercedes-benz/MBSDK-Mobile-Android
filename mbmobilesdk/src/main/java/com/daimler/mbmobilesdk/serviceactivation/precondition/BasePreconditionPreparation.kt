package com.daimler.mbmobilesdk.serviceactivation.precondition

import com.daimler.mbcarkit.business.model.services.ServiceAction
import com.daimler.mbcarkit.business.model.services.ServiceMissingField

internal abstract class BasePreconditionPreparation : ServicePreconditionPreparation {

    protected fun mapServiceInformationToPreconditions(
        serviceId: Int,
        actions: List<ServiceAction>,
        missingFields: List<ServiceMissingField>
    ): List<ServicePreconditionType> {
        val list = mutableListOf<ServicePreconditionType>()
        actions.filter { it.isPrecondition() }.mapNotNullTo(list) {
            mapServiceActionToPrecondition(it, serviceId)
        }
        list.addAll(mapMissingFieldsToServicePreconditionTypes(missingFields))
        return list
    }

    private fun mapMissingFieldsToServicePreconditionTypes(missingFields: List<ServiceMissingField>) =
        missingFields.mapNotNull { mapMissingFieldToServicePreconditionType(it) }

    private fun mapServiceActionToPrecondition(
        action: ServiceAction,
        serviceId: Int
    ): ServicePreconditionType? =
        when (action) {
            ServiceAction.SIGN_USER_AGREEMENT -> ServicePreconditionType.SignUserAgreement()
            ServiceAction.UPDATE_TRUST_LEVEL -> ServicePreconditionType.UpdateTrustLevel()
            ServiceAction.PURCHASE_LICENSE -> ServicePreconditionType.PurchaseLicense(serviceId)
            else -> null
        }

    private fun mapMissingFieldToServicePreconditionType(
        missingField: ServiceMissingField
    ): ServicePreconditionType? =
        when (missingField) {
            ServiceMissingField.LICENSE_PLATE -> ServicePreconditionType.LicensePlate()
            ServiceMissingField.VEHICLE_SERVICE_DEALER -> ServicePreconditionType.VehicleServiceDealer()
            ServiceMissingField.USER_CONTACT_BY_MAIL -> ServicePreconditionType.UserContactMethod()
            ServiceMissingField.USER_CONTACT_BY_PHONE -> ServicePreconditionType.UserContactMethod()
            ServiceMissingField.USER_CONTACT_BY_SMS -> ServicePreconditionType.UserContactMethod()
            ServiceMissingField.USER_CONTACT_BY_LETTER -> ServicePreconditionType.UserContactMethod()
            ServiceMissingField.CP_INCREDIT -> null
            ServiceMissingField.USER_MOBILE_PHONE -> ServicePreconditionType.UserPhone()
            ServiceMissingField.USER_MOBILE_PHONE_VERIFIED -> ServicePreconditionType.UserPhoneVerified()
            ServiceMissingField.USER_MAIL -> ServicePreconditionType.UserMail()
            ServiceMissingField.USER_MAIL_VERIFIED -> ServicePreconditionType.UserMailVerified()
            ServiceMissingField.UNKNOWN -> null
        }

    private fun ServiceAction.isPrecondition() = this in handleableServiceActions()

    // TODO add a flag to the enum
    private fun handleableServiceActions() =
        listOf(ServiceAction.SIGN_USER_AGREEMENT, ServiceAction.EDIT_USER_PROFILE,
            ServiceAction.UPDATE_TRUST_LEVEL, ServiceAction.SET_CUSTOM_PROPERTY,
            ServiceAction.PURCHASE_LICENSE, ServiceAction.REMOVE_FUSEBOX_ENTRY)
}