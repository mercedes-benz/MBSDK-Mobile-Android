package com.daimler.mbmobilesdk.services

import android.view.View
import com.daimler.mbmobilesdk.serviceactivation.precondition.*
import com.daimler.mbmobilesdk.serviceactivation.views.FieldResolvable
import com.daimler.mbmobilesdk.serviceactivation.views.MissingFieldCallback
import org.junit.Before
import org.junit.Test

class ServicePreconditionTest : MissingFieldResolvableCreator, PreconditionInputResolver {

    private var resolvable: TestResolvable? = null
    private var changeValue: String? = null
    private lateinit var preconditions: List<ServicePreconditionType>

    private val preparation = TestPreparation()

    @Before
    fun setup() {
        resolvable = null
        changeValue = null
        preconditions = preparation.preconditions()
    }

    @Test
    fun testResolvablesCreated() {
        preconditions.forEach {
            it.create(this)
            val resolvable = resolvable!!
            assert(resolvable.changeValue == it::class.java.simpleName)
            this.resolvable = null
        }
    }

    @Test
    fun testCallbackSet() {
        preconditions.forEach {
            it.create(this)
            val resolvable = resolvable!!
            it.resolve(this, resolvable)
            assert(resolvable.fieldCallback != null)
            this.resolvable = null
        }
    }

    @Test
    fun testCallbackCalled() {
        preconditions.forEach {
            it.create(this)
            val resolvable = resolvable!!
            it.resolve(this, resolvable)
            resolvable.changeValue()
            assert(changeValue == resolvable.changeValue)
            changeValue = null
            this.resolvable = null
        }
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.SignUserAgreement) {
        create(preconditionType)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.PurchaseLicense) {
        create(preconditionType)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.UpdateTrustLevel) {
        create(preconditionType)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.UserMail) {
        create(preconditionType)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.UserMailVerified) {
        create(preconditionType)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.UserPhone) {
        create(preconditionType)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.UserPhoneVerified) {
        create(preconditionType)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.LicensePlate) {
        create(preconditionType)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.VehicleServiceDealer) {
        create(preconditionType)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.UserContactMethod) {
        create(preconditionType)
    }

    override fun createResolvable(preconditionType: ServicePreconditionType.CpIncredit) {
        create(preconditionType)
    }

    private fun create(preconditionType: PreconditionInputResolvable) {
        resolvable = TestResolvable.create(preconditionType)
    }

    override fun resolve(preconditionType: ServicePreconditionType.SignUserAgreement, resolvable: FieldResolvable) {
        resolve(resolvable)
    }

    override fun resolve(preconditionType: ServicePreconditionType.PurchaseLicense, resolvable: FieldResolvable) {
        resolve(resolvable)
    }

    override fun resolve(preconditionType: ServicePreconditionType.UpdateTrustLevel, resolvable: FieldResolvable) {
        resolve(resolvable)
    }

    override fun resolve(preconditionType: ServicePreconditionType.UserMail, resolvable: FieldResolvable) {
        resolve(resolvable)
    }

    override fun resolve(preconditionType: ServicePreconditionType.UserMailVerified, resolvable: FieldResolvable) {
        resolve(resolvable)
    }

    override fun resolve(preconditionType: ServicePreconditionType.UserPhone, resolvable: FieldResolvable) {
        resolve(resolvable)
    }

    override fun resolve(preconditionType: ServicePreconditionType.UserPhoneVerified, resolvable: FieldResolvable) {
        resolve(resolvable)
    }

    override fun resolve(preconditionType: ServicePreconditionType.LicensePlate, resolvable: FieldResolvable) {
        resolve(resolvable)
    }

    override fun resolve(preconditionType: ServicePreconditionType.VehicleServiceDealer, resolvable: FieldResolvable) {
        resolve(resolvable)
    }

    override fun resolve(preconditionType: ServicePreconditionType.UserContactMethod, resolvable: FieldResolvable) {
        resolve(resolvable)
    }

    override fun resolve(preconditionType: ServicePreconditionType.CpIncredit, resolvable: FieldResolvable) {
        resolve(resolvable)
    }

    private fun resolve(resolvable: FieldResolvable) {
        val callback = object : MissingFieldCallback {
            override fun onValueChanged(value: String?) {
                changeValue = value
            }
        }
        resolvable.applyCallback(callback)
    }

    private class TestResolvable(val changeValue: String) : FieldResolvable {

        var fieldCallback: MissingFieldCallback? = null

        fun changeValue() {
            fieldCallback?.onValueChanged(changeValue)
        }

        override fun applyCallback(callback: MissingFieldCallback) {
            this.fieldCallback = callback
        }

        override fun view(): View? = null

        companion object {

            fun create(obj: Any) = TestResolvable(obj::class.java.simpleName)
        }
    }

    private class TestPreparation : ServicePreconditionPreparation {

        override fun preconditions(): List<ServicePreconditionType> {
            val list = mutableListOf<ServicePreconditionType>()
            list.add(ServicePreconditionType.SignUserAgreement())
            list.add(ServicePreconditionType.PurchaseLicense(0))
            list.add(ServicePreconditionType.UpdateTrustLevel())
            list.add(ServicePreconditionType.UserMail())
            list.add(ServicePreconditionType.UserMailVerified())
            list.add(ServicePreconditionType.UserPhone())
            list.add(ServicePreconditionType.UserPhoneVerified())
            list.add(ServicePreconditionType.UserContactMethod())
            list.add(ServicePreconditionType.LicensePlate())
            list.add(ServicePreconditionType.VehicleServiceDealer())
            list.add(ServicePreconditionType.CpIncredit())
            return list
        }
    }
}