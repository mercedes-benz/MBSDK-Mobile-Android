package com.daimler.mbmobilesdk.serviceactivation.precondition

import com.daimler.mbmobilesdk.serviceactivation.views.FieldResolvable

sealed class ServicePreconditionType :
    MissingFieldCreatable,
    PreconditionInputResolvable {

    class SignUserAgreement : ServicePreconditionType() {

        override fun resolve(resolver: PreconditionInputResolver, resolvable: FieldResolvable) {
            resolver.resolve(this, resolvable)
        }

        override fun create(resolvableCreator: MissingFieldResolvableCreator) {
            resolvableCreator.createResolvable(this)
        }
    }

    class PurchaseLicense(val serviceId: Int) : ServicePreconditionType() {

        override fun resolve(resolver: PreconditionInputResolver, resolvable: FieldResolvable) {
            resolver.resolve(this, resolvable)
        }

        override fun create(resolvableCreator: MissingFieldResolvableCreator) {
            resolvableCreator.createResolvable(this)
        }
    }

    class UpdateTrustLevel : ServicePreconditionType() {

        override fun resolve(resolver: PreconditionInputResolver, resolvable: FieldResolvable) {
            resolver.resolve(this, resolvable)
        }

        override fun create(resolvableCreator: MissingFieldResolvableCreator) {
            resolvableCreator.createResolvable(this)
        }
    }

    class UserMail : ServicePreconditionType() {

        override fun resolve(resolver: PreconditionInputResolver, resolvable: FieldResolvable) {
            resolver.resolve(this, resolvable)
        }

        override fun create(resolvableCreator: MissingFieldResolvableCreator) {
            resolvableCreator.createResolvable(this)
        }
    }

    class UserMailVerified : ServicePreconditionType() {

        override fun resolve(resolver: PreconditionInputResolver, resolvable: FieldResolvable) {
            resolver.resolve(this, resolvable)
        }

        override fun create(resolvableCreator: MissingFieldResolvableCreator) {
            resolvableCreator.createResolvable(this)
        }
    }

    class UserPhone : ServicePreconditionType() {

        override fun resolve(resolver: PreconditionInputResolver, resolvable: FieldResolvable) {
            resolver.resolve(this, resolvable)
        }

        override fun create(resolvableCreator: MissingFieldResolvableCreator) {
            resolvableCreator.createResolvable(this)
        }
    }

    class UserPhoneVerified : ServicePreconditionType() {

        override fun resolve(resolver: PreconditionInputResolver, resolvable: FieldResolvable) {
            resolver.resolve(this, resolvable)
        }

        override fun create(resolvableCreator: MissingFieldResolvableCreator) {
            resolvableCreator.createResolvable(this)
        }
    }

    class LicensePlate : ServicePreconditionType() {

        override fun resolve(resolver: PreconditionInputResolver, resolvable: FieldResolvable) {
            resolver.resolve(this, resolvable)
        }

        override fun create(resolvableCreator: MissingFieldResolvableCreator) {
            resolvableCreator.createResolvable(this)
        }
    }

    class VehicleServiceDealer : ServicePreconditionType() {

        override fun resolve(resolver: PreconditionInputResolver, resolvable: FieldResolvable) {
            resolver.resolve(this, resolvable)
        }

        override fun create(resolvableCreator: MissingFieldResolvableCreator) {
            resolvableCreator.createResolvable(this)
        }
    }

    class UserContactMethod : ServicePreconditionType() {

        override fun resolve(resolver: PreconditionInputResolver, resolvable: FieldResolvable) {
            resolver.resolve(this, resolvable)
        }

        override fun create(resolvableCreator: MissingFieldResolvableCreator) {
            resolvableCreator.createResolvable(this)
        }
    }

    class CpIncredit : ServicePreconditionType() {

        override fun resolve(resolver: PreconditionInputResolver, resolvable: FieldResolvable) {
            resolver.resolve(this, resolvable)
        }

        override fun create(resolvableCreator: MissingFieldResolvableCreator) {
            resolvableCreator.createResolvable(this)
        }
    }
}