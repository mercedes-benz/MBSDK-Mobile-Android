package com.daimler.mbmobilesdk.serviceactivation.precondition

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.logic.UserTask
import com.daimler.mbmobilesdk.serviceactivation.views.FieldResolvable
import com.daimler.mbmobilesdk.serviceactivation.views.MissingFieldCallback
import com.daimler.mbmobilesdk.utils.UserValuePolicy
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbmobilesdk.utils.extensions.userInputErrorMessage
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.User
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf

internal abstract class BaseServicePreconditionViewModel(
    app: Application,
    private val finOrVin: String
) : AndroidViewModel(app), PreconditionInputResolver {

    val progressVisible = mutableLiveDataOf(false)
    abstract val title: String

    val successEvent = MutableLiveEvent<String>()
    val errorEvent = MutableLiveEvent<String>()

    val backEvent = MutableLiveUnitEvent()
    val preconditionResolvedEvent = MutableLiveEvent<ServicePreconditionType>()

    val signUserAgreementsEvent = MutableLiveEvent<ServicePreconditionType.SignUserAgreement>()
    val selectMerchantEvent = MutableLiveUnitEvent()
    val purchaseLicenseEvent = MutableLiveEvent<Service>()
    val confirmIdentityEvent = MutableLiveUnitEvent()

    private val preconditionsCollected = MutableLiveData<List<ServicePreconditionType>>()

    abstract fun serviceForId(id: Int): Service?

    protected abstract fun collectPreconditions(result: MutableLiveData<List<ServicePreconditionType>>)

    private fun createResolvables(
        preconditions: List<ServicePreconditionType>,
        creator: MissingFieldResolvableCreator
    ) = preconditions.forEach { it.create(creator) }

    fun onBackClicked() {
        backEvent.sendEvent()
    }

    fun preparePreconditions(creator: MissingFieldResolvableCreator) {
        @Suppress("ConstantConditionIf")
        if (USE_MOCK_SERVICE_PREPARATION) {
            val preconditions = MockServicePreparation().preconditions()
            createResolvables(preconditions, creator)
        } else {
            val observer = object : Observer<List<ServicePreconditionType>> {
                override fun onChanged(t: List<ServicePreconditionType>?) {
                    t?.let {
                        createResolvables(it, creator)
                        preconditionsCollected.removeObserver(this)
                    }
                }
            }
            preconditionsCollected.observeForever(observer)
            collectPreconditions(preconditionsCollected)
        }
    }

    fun onAgreementsUpdated(type: ServicePreconditionType.SignUserAgreement) {
        removePrecondition(type)
    }

    override fun resolve(preconditionType: ServicePreconditionType.SignUserAgreement, resolvable: FieldResolvable) {
        MBLoggerKit.d("Resolve SignUserAgreement")
        resolvable.withAction { signUserAgreementsEvent.sendEvent(preconditionType) }
    }

    override fun resolve(preconditionType: ServicePreconditionType.PurchaseLicense, resolvable: FieldResolvable) {
        MBLoggerKit.d("Resolve PurchaseLicense")
        resolvable.withAction {
            serviceForId(preconditionType.serviceId)?.let { service ->
                purchaseLicenseEvent.sendEvent(service)
            }
        }
    }

    override fun resolve(preconditionType: ServicePreconditionType.UpdateTrustLevel, resolvable: FieldResolvable) {
        MBLoggerKit.d("Resolve UpdateTrustLevel")
        resolvable.withAction { confirmIdentityEvent.sendEvent() }
    }

    override fun resolve(preconditionType: ServicePreconditionType.UserMail, resolvable: FieldResolvable) {
        resolvable.withAction {
            if (UserValuePolicy.Mail.isValid(it)) {
                executeWithUser { token, user ->
                    val newUser = user.copy(email = it!!)
                    MBIngressKit.userService()
                        .updateUser(token, newUser)
                        .onComplete { removePrecondition(preconditionType) }
                        .onFailure { error -> errorEvent.sendEvent(userInputErrorMessage(error)) }
                }
            }
        }
    }

    override fun resolve(preconditionType: ServicePreconditionType.UserPhone, resolvable: FieldResolvable) {
        resolvable.withAction {
            if (UserValuePolicy.Phone.isValid(it)) {
                executeWithUser { token, user ->
                    val newUser = user.copy(mobilePhone = it!!)
                    MBIngressKit.userService()
                        .updateUser(token, newUser)
                        .onComplete { removePrecondition(preconditionType) }
                        .onFailure { error -> errorEvent.sendEvent(userInputErrorMessage(error)) }
                }
            }
        }
    }

    override fun resolve(preconditionType: ServicePreconditionType.UserMailVerified, resolvable: FieldResolvable) {
        MBLoggerKit.d("Resolve UserMailVerified")
    }

    override fun resolve(preconditionType: ServicePreconditionType.UserPhoneVerified, resolvable: FieldResolvable) {
        MBLoggerKit.d("Resolve UserPhoneVerified")
    }

    override fun resolve(preconditionType: ServicePreconditionType.LicensePlate, resolvable: FieldResolvable) {
        MBLoggerKit.d("Resolve LicensePlate")
        resolvable.withAction { licensePlate ->
            if (!licensePlate.isNullOrBlank()) {
                executeAuthenticated {
                    MBCarKit.vehicleService().updateLicensePlate(
                        it,
                        MBMobileSDK.userLocale().countryCode,
                        finOrVin,
                        licensePlate
                    ).onComplete { removePrecondition(preconditionType) }
                }
            }
        }
    }

    override fun resolve(preconditionType: ServicePreconditionType.VehicleServiceDealer, resolvable: FieldResolvable) {
        MBLoggerKit.d("Resolve VehicleServiceDealer")
        resolvable.withAction {
            selectMerchantEvent.sendEvent()
        }
    }

    override fun resolve(preconditionType: ServicePreconditionType.UserContactMethod, resolvable: FieldResolvable) {
        MBLoggerKit.d("Resolve UserContactMethod")
        // Done at registration.
    }

    override fun resolve(preconditionType: ServicePreconditionType.CpIncredit, resolvable: FieldResolvable) {
        MBLoggerKit.d("Resolve CpIncredit")
    }

    private fun FieldResolvable.withAction(action: (String?) -> Unit) {
        applyCallback(object : MissingFieldCallback {
            override fun onValueChanged(value: String?) {
                action(value)
            }
        })
    }

    private fun executeAuthenticated(action: (String) -> FutureTask<*, *>) {
        progressVisible.postValue(true)
        MBIngressKit.refreshTokenIfRequired()
            .onComplete {
                action(it.jwtToken.plainToken)
                    .onAlways { _, _, _ -> progressVisible.postValue(false) }
            }
            .onFailure {
                errorEvent.sendEvent(defaultErrorMessage(it))
                progressVisible.postValue(false)
            }
    }

    private fun executeWithUser(action: (String, User) -> FutureTask<*, *>) {
        progressVisible.postValue(true)
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                UserTask()
                    .fetchUser()
                    .onComplete {
                        action(token.jwtToken.plainToken, it.user)
                            .onAlways { _, _, _ -> progressVisible.postValue(false) }
                    }
                    .onFailure {
                        errorEvent.sendEvent(defaultErrorMessage(it))
                        progressVisible.postValue(false)
                    }
            }.onFailure {
                errorEvent.sendEvent(defaultErrorMessage(it))
                progressVisible.postValue(false)
            }
    }

    private fun removePrecondition(precondition: ServicePreconditionType) {
        preconditionResolvedEvent.sendEvent(precondition)
    }

    private companion object {
        private const val USE_MOCK_SERVICE_PREPARATION = false
    }
}