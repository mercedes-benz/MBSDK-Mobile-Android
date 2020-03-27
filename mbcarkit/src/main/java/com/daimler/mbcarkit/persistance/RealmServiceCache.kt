package com.daimler.mbcarkit.persistance

import com.daimler.mbcarkit.business.ServiceCache
import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import com.daimler.mbcarkit.business.model.services.MissingAccountLinkage
import com.daimler.mbcarkit.business.model.services.MissingServiceData
import com.daimler.mbcarkit.business.model.services.PrerequisiteCheck
import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbcarkit.business.model.services.ServiceAction
import com.daimler.mbcarkit.business.model.services.ServiceMissingField
import com.daimler.mbcarkit.business.model.services.ServiceResolvedPrecondition
import com.daimler.mbcarkit.business.model.services.ServiceRight
import com.daimler.mbcarkit.business.model.services.ServiceStatus
import com.daimler.mbcarkit.business.model.services.ServiceUpdate
import com.daimler.mbcarkit.persistance.model.RealmMissingAccountLinkage
import com.daimler.mbcarkit.persistance.model.RealmMissingServiceData
import com.daimler.mbcarkit.persistance.model.RealmPrerequisiteCheck
import com.daimler.mbcarkit.persistance.model.RealmService
import com.daimler.mbcarkit.persistance.model.applyFrom
import com.daimler.mbcarkit.persistance.model.applyFromMissingAccountLinkage
import com.daimler.mbcarkit.persistance.model.cascadeDeleteFromRealm
import com.daimler.mbcarkit.persistance.model.isSameAccountType
import com.daimler.mbcarkit.persistance.model.toMissingServiceData
import com.daimler.mbloggerkit.MBLoggerKit
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.createObject
import io.realm.kotlin.delete
import io.realm.kotlin.where

internal class RealmServiceCache(
    private val realm: Realm
) : ServiceCache {

    override fun updateServices(finOrVin: String, services: List<Service>, fillPreconditions: Boolean) {
        val currentServices = realm.where<RealmService>()
            .equalTo(RealmService.FIELD_VIN, finOrVin)
            .findAll()
        realm.executeTransaction {
            services.forEach { service ->
                var realmService = currentServices.find {
                    it.id == service.id
                }
                if (realmService == null) {
                    MBLoggerKit.d("Create service ${service.id} in realm service cache.")
                    realmService = realm.createObject(generateServicePrimaryKey(finOrVin, service.id))
                } else {
                    MBLoggerKit.d("Updating service ${service.id} in realm service cache.")
                }
                realmService.apply {
                    vehicleFinOrVin = finOrVin
                    id = service.id
                    name = service.name
                    description = service.description
                    shortName = service.shortName
                    categoryName = service.categoryName
                    updateAllowedActions(allowedActions, service.allowedActions)
                    activationStatus = service.activationStatus.ordinal
                    desiredActivationStatus = service.desiredActivationStatus.ordinal
                    actualActivationStatus = service.actualActivationServiceStatus.ordinal
                    virtualActivationStatus = service.virtualActivationServiceStatus.ordinal
                    prerequisiteChecks = createOrUpdatePrerequisiteChecks(prerequisiteChecks, service.prerequisiteChecks)
                    updateServiceRights(rights, service.rights)
                    if (fillPreconditions) missingData = createOrUpdateMissingData(missingData, service.missingData)

                    it.copyToRealmOrUpdate(this)
                }
            }
        }
    }

    override fun loadServices(finOrVin: String): List<Service>? {
        return realm.where<RealmService>()
            .equalTo(RealmService.FIELD_VIN, finOrVin)
            .findAll()
            .takeIf {
                it.isNotEmpty()
            }?.map {
                mapRealmServiceToService(it)
            }
    }

    override fun loadServiceById(finOrVin: String, id: Int): Service? {
        return realm.where<RealmService>()
            .equalTo(RealmService.FIELD_ID, generateServicePrimaryKey(finOrVin, id))
            .findFirst()
            ?.let {
                mapRealmServiceToService(it)
            }
    }

    override fun loadServicesById(finOrVin: String, ids: List<Int>): List<Service>? {
        val internalIds = ids.map { generateServicePrimaryKey(finOrVin, it) }
        return realm.where<RealmService>()
            .equalTo(RealmService.FIELD_VIN, finOrVin)
            .`in`(RealmService.FIELD_ID, internalIds.toTypedArray())
            .findAll()
            ?.takeIf {
                it.isNotEmpty()
            }?.map {
                mapRealmServiceToService(it)
            }
    }

    override fun updateServiceStatus(finOrVin: String, updates: List<ServiceUpdate>) {
        val internalIds = updates.map { generateServicePrimaryKey(finOrVin, it.id) }
        realm.executeTransaction { realm ->
            realm.where<RealmService>()
                .equalTo(RealmService.FIELD_VIN, finOrVin)
                .`in`(RealmService.FIELD_ID, internalIds.toTypedArray())
                .findAll()
                ?.forEach { realmService ->
                    updates.find {
                        it.id == realmService.id
                    }?.let {
                        realmService.apply {
                            activationStatus = it.status.ordinal
                            desiredActivationStatus = ServiceStatus.UNKNOWN.ordinal
                            realm.copyToRealmOrUpdate(this)
                        }
                    }
                }
        }
    }

    override fun updatePrecondition(finOrVin: String, precondition: ServiceResolvedPrecondition) {
        realm.executeTransactionAsync { db ->
            val realmServices = db.where<RealmService>()
                .equalTo(RealmService.FIELD_VIN, finOrVin)
                .and()
                .isNotNull(RealmService.FIELD_MISSING_DATA)
                .findAll()
            realmServices
                .filter {
                    it.updatePrecondition(precondition)
                }.forEach {
                    db.copyToRealmOrUpdate(it)
                }
        }
    }

    override fun clearForFinOrVin(finOrVin: String?) {
        realm.executeTransaction { r ->
            r.where<RealmService>()
                .equalTo(RealmService.FIELD_VIN, finOrVin)
                .findAll()
                .apply {
                    toList().forEach { it.cascadeDeleteFromRealm() }
                    deleteAllFromRealm()
                }
        }
    }

    override fun clearAll() {
        realm.executeTransaction {
            it.delete<RealmPrerequisiteCheck>()
            it.delete<RealmMissingAccountLinkage>()
            it.delete<RealmMissingServiceData>()
            it.delete<RealmService>()
        }
    }

    override fun deleteServices(finOrVin: String, services: List<Service>) {
        val internalIds = services.map { generateServicePrimaryKey(finOrVin, it.id) }
        realm.executeTransaction { r ->
            r.where<RealmService>()
                .equalTo(RealmService.FIELD_VIN, finOrVin)
                .`in`(RealmService.FIELD_ID, internalIds.toTypedArray())
                .findAll()
                .apply {
                    toList().forEach { it.cascadeDeleteFromRealm() }
                    deleteAllFromRealm()
                }
        }
    }

    private fun mapRealmServiceToService(realmService: RealmService) =
        Service(
            realmService.id ?: 0,
            realmService.name.orEmpty(),
            realmService.description,
            realmService.shortName,
            realmService.categoryName,
            mapRealmAllowedActionsToAllowedActions(realmService.allowedActions),
            serviceStatusFromInt(realmService.activationStatus),
            serviceStatusFromInt(realmService.desiredActivationStatus),
            serviceStatusFromInt(realmService.actualActivationStatus),
            serviceStatusFromInt(realmService.virtualActivationStatus),
            mapRealmPrerequisitesToPrerequisites(realmService.prerequisiteChecks),
            mapRealmServiceRightsToServiceRights(realmService.rights),
            mapRealmMissingDataToMissingData(realmService.missingData)
        )

    private fun serviceStatusFromInt(value: Int?) =
        ServiceStatus.values().getOrElse(value ?: -1) { ServiceStatus.UNKNOWN }

    private fun mapRealmAllowedActionsToAllowedActions(realmAllowedActions: RealmList<Int>): List<ServiceAction> {
        val actions = ServiceAction.values()
        return realmAllowedActions.map {
            actions.getOrElse(it ?: -1) { ServiceAction.UNKNOWN }
        }
    }

    private fun mapRealmPrerequisitesToPrerequisites(realmPrerequisiteChecks: List<RealmPrerequisiteCheck>): List<PrerequisiteCheck> {
        return realmPrerequisiteChecks.map {
            PrerequisiteCheck(
                it.name,
                mapRealmMissingFieldsToMissingFields(it.missingInformation),
                mapRealmAllowedActionsToAllowedActions(it.actions)
            )
        }
    }

    private fun mapRealmMissingFieldsToMissingFields(realmMissingFields: RealmList<Int>): List<ServiceMissingField> {
        val fields = ServiceMissingField.values()
        return realmMissingFields.map {
            fields.getOrElse(it ?: -1) { ServiceMissingField.UNKNOWN }
        }
    }

    private fun mapRealmServiceRightsToServiceRights(realmServiceRights: RealmList<Int>): List<ServiceRight> {
        val rights = ServiceRight.values()
        return realmServiceRights.map {
            rights.getOrElse(it ?: -1) { ServiceRight.UNKNOWN }
        }
    }

    private fun mapRealmMissingDataToMissingData(realmMissingServiceData: RealmMissingServiceData?) =
        realmMissingServiceData?.toMissingServiceData()

    private fun createOrUpdatePrerequisiteChecks(realmChecks: RealmList<RealmPrerequisiteCheck>, checks: List<PrerequisiteCheck>): RealmList<RealmPrerequisiteCheck> {
        realmChecks.apply {
            toList().forEach { it.cascadeDeleteFromRealm() }
            deleteAllFromRealm()
            clear()
        }
        checks.forEach {
            realm.createObject<RealmPrerequisiteCheck>().apply {
                name = it.name
                updateMissingInformation(missingInformation, it.missingInformation)
                updateAllowedActions(actions, it.actions)
                realmChecks.add(this)
            }
        }
        return realmChecks
    }

    private fun createOrUpdateMissingData(
        realmMissingServiceData: RealmMissingServiceData?,
        missingServiceData: MissingServiceData?
    ): RealmMissingServiceData? =
        missingServiceData?.let {
            val realmModel = realmMissingServiceData ?: realm.createObject()
            realmModel.apply {
                applyFrom(createOrUpdateMissingAccountLinkage(missingAccountLinkage, it.missingAccountLinkage))
            }
        } ?: run {
            realmMissingServiceData?.cascadeDeleteFromRealm()
            null
        }

    private fun createOrUpdateMissingAccountLinkage(
        realmMissingAccountLinkage: RealmMissingAccountLinkage?,
        missingAccountLinkage: MissingAccountLinkage?
    ): RealmMissingAccountLinkage? =
        missingAccountLinkage?.let {
            val realmModel = realmMissingAccountLinkage ?: realm.createObject()
            realmModel.applyFromMissingAccountLinkage(it)
        } ?: run {
            realmMissingAccountLinkage?.cascadeDeleteFromRealm()
            null
        }

    private fun updateAllowedActions(realmAllowedActions: RealmList<Int>, actions: List<ServiceAction>) {
        realmAllowedActions.apply {
            clear()
            addAll(actions.map { it.ordinal })
        }
    }

    private fun updateServiceRights(realmRights: RealmList<Int>, rights: List<ServiceRight>) {
        realmRights.apply {
            clear()
            addAll(rights.map { it.ordinal })
        }
    }

    private fun updateMissingInformation(realmMissingInformation: RealmList<Int>, missingFields: List<ServiceMissingField>) {
        realmMissingInformation.apply {
            clear()
            addAll(missingFields.map { it.ordinal })
        }
    }

    private fun generateServicePrimaryKey(finOrVin: String, serviceId: Int) =
        "${serviceId}_$finOrVin"

    private fun RealmService.updatePrecondition(precondition: ServiceResolvedPrecondition): Boolean =
        when (precondition) {
            is ServiceResolvedPrecondition.AccountLinkage -> updateAccountLinkage(precondition.type)
        }

    private fun RealmService.updateAccountLinkage(type: AccountType): Boolean =
        if (missingData?.missingAccountLinkage?.isSameAccountType(type) == true) {
            missingData?.missingAccountLinkage = null
            true
        } else {
            false
        }
}
