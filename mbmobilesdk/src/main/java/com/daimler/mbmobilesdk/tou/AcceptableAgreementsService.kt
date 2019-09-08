package com.daimler.mbmobilesdk.tou

import com.daimler.mbmobilesdk.utils.extensions.emptyUserAgreements
import com.daimler.mbmobilesdk.utils.extensions.isAccepted
import com.daimler.mbingresskit.common.UserAgreement
import com.daimler.mbingresskit.common.UserAgreementUpdate
import com.daimler.mbingresskit.common.UserAgreementUpdates
import com.daimler.mbingresskit.common.UserAgreements
import com.daimler.mbingresskit.login.UserService
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

internal abstract class AcceptableAgreementsService<T : UserAgreement>(
    private val userService: UserService
) {

    private var agreements = emptyUserAgreements<T>()
    private val originalAcceptanceStates = mutableMapOf<String, Boolean>()
    private val acceptanceStates = mutableMapOf<String, Boolean>()

    protected abstract fun executeFetch(
        token: String,
        countryCode: String,
        userService: UserService
    ): FutureTask<UserAgreements<T>, ResponseError<out RequestError>?>

    protected abstract fun T.isMandatory(): Boolean

    fun loadAgreements(countryCode: String): FutureTask<UserAgreements<T>, ResponseError<out RequestError>?> {
        return loadAgreements("", countryCode)
    }

    fun loadAgreements(token: String, countryCode: String): FutureTask<UserAgreements<T>, ResponseError<out RequestError>?> {
        val task = TaskObject<UserAgreements<T>, ResponseError<out RequestError>?>()
        executeFetch(token, countryCode, userService)
            .onComplete { userAgreements ->
                agreements = userAgreements.copy()
                val states = userAgreements.agreements.map { it.documentId to it.isAccepted() }
                originalAcceptanceStates.clearAndFill(states)
                acceptanceStates.clearAndFill(states)
                task.complete(userAgreements)
            }.onFailure {
                originalAcceptanceStates.clear()
                acceptanceStates.clear()
                task.fail(it)
            }
        return task.futureTask()
    }

    fun sendAgreements(token: String): FutureTask<Unit, ResponseError<out RequestError>?> {
        return userService.updateSOETermsAndConditions(token, currentAgreementUpdates())
    }

    fun currentAgreementUpdates(): UserAgreementUpdates {
        synchronized(agreements) {
            val updates = acceptanceStates.map { state ->
                UserAgreementUpdate(state.key,
                    agreements.agreements.firstOrNull { it.documentId == state.key }?.documentVersion.orEmpty(),
                    state.value
                )
            }
            return UserAgreementUpdates(agreements.countryCode, updates)
        }
    }

    fun disagreeToAll() {
        acceptanceStates.apply {
            keys.forEach { put(it, false) }
        }
    }

    fun getFilePath(id: String): String? =
        agreements.agreements.firstOrNull { it.documentId == id }?.filePath

    fun didAgreementStatesChange(): Boolean {
        return originalAcceptanceStates != acceptanceStates
    }

    fun containsMandatoryAgreement(ids: List<String>): Boolean =
        agreements.agreements.filter { it.isMandatory() }.any { ids.contains(it.documentId) }

    fun mandatoryAgreementsAccepted(): Boolean =
        acceptanceStates.none { isMandatoryAgreement(it.key) && !it.value }

    fun changeAcceptanceStates(ids: List<String>, accepted: Boolean): Boolean {
        ids.forEach { acceptanceStates[it] = accepted }
        return mandatoryAgreementsAccepted()
    }

    fun agreeToAll() {
        acceptanceStates.apply {
            keys.forEach { put(it, true) }
        }
    }

    fun getRejectedMandatoryAgreements(): List<T> =
        agreements.agreements.filter {
            it.isMandatory() && acceptanceStates[it.documentId] != true
        }

    fun agreementForId(id: String): T? =
        agreements.agreements.firstOrNull { it.documentId == id }

    private fun isMandatoryAgreement(id: String): Boolean =
        agreements.agreements.firstOrNull { it.documentId == id }?.isMandatory() == true

    private fun <K, V> MutableMap<K, V>.clearAndFill(pairs: List<Pair<K, V>>) {
        clear()
        putAll(pairs)
    }
}