package com.daimler.mbcarkit.persistance

import com.daimler.mbcarkit.business.CommandCapabilitiesCache
import com.daimler.mbcarkit.business.model.command.capabilities.AllowedBools
import com.daimler.mbcarkit.business.model.command.capabilities.AllowedEnums
import com.daimler.mbcarkit.business.model.command.capabilities.Command
import com.daimler.mbcarkit.business.model.command.capabilities.CommandCapabilities
import com.daimler.mbcarkit.business.model.command.capabilities.CommandName
import com.daimler.mbcarkit.business.model.command.capabilities.CommandParameter
import com.daimler.mbcarkit.business.model.command.capabilities.CommandParameterName
import com.daimler.mbcarkit.persistance.model.RealmCommand
import com.daimler.mbcarkit.persistance.model.RealmCommandParameter
import com.daimler.mbcarkit.persistance.model.cascadeDeleteFromRealm
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.createObject
import io.realm.kotlin.delete
import io.realm.kotlin.where

internal class RealmCommandCapabilitiesCache(
    private val realm: Realm
) : CommandCapabilitiesCache {

    override fun writeCache(finOrVin: String, capabilities: CommandCapabilities) {
        realm.executeTransaction { r ->
            realmCommandsForFinOrVin(finOrVin)
                .forEach { it.cascadeDeleteFromRealm() }
            capabilities.commands.forEach { command ->
                createOrUpdateCommand(r, finOrVin, command)
            }
        }
    }

    override fun loadFromCache(finOrVin: String): CommandCapabilities? {
        return realmCommandsForFinOrVin(finOrVin)
            .takeIf { it.isNotEmpty() }
            ?.map { mapRealmCommandToCommand(it) }
            ?.let { CommandCapabilities(it) }
    }

    override fun deleteForFinOrVin(finOrVin: String) {
        realm.executeTransaction {
            realmCommandsForFinOrVin(finOrVin)
                .forEach { it.cascadeDeleteFromRealm() }
        }
    }

    override fun deleteAll() {
        realm.executeTransaction {
            it.delete<RealmCommandParameter>()
            it.delete<RealmCommand>()
        }
    }

    private fun mapRealmCommandToCommand(realmCommand: RealmCommand) =
        Command(
            name = CommandName.values().getOrElse(realmCommand.name ?: -1) { CommandName.UNKNOWN },
            available = realmCommand.available ?: false,
            parameters = mapRealmParametersToParameters(realmCommand.parameters),
            additionalInformation = realmCommand.additionalInformation?.toList() ?: emptyList()
        )

    private fun mapRealmParametersToParameters(realmParameters: RealmList<RealmCommandParameter>): List<CommandParameter> {
        val parameterNames = CommandParameterName.values()
        val allowedEnums = AllowedEnums.values()
        val allowedBools = AllowedBools.values()

        return realmParameters.map { parameter ->
            CommandParameter(
                name = parameterNames.getOrDefault(parameter.name) { CommandParameterName.UNKNOWN },
                minValue = parameter.minValue ?: 0.0,
                maxValue = parameter.maxValue ?: 0.0,
                steps = parameter.steps ?: 0.0,
                allowedEnums = parameter.allowedEnums?.map { e ->
                    allowedEnums.getOrDefault(e) { AllowedEnums.UNKNOWN }
                } ?: emptyList(),
                allowedBools = allowedBools.getOrDefault(parameter.allowedBools) { AllowedBools.UNKNOWN }
            )
        }
    }

    private fun realmCommandsForFinOrVin(finOrVin: String) =
        realm.where<RealmCommand>()
            .equalTo(RealmCommand.FIELD_VIN, finOrVin)
            .findAll()

    private fun createOrUpdateCommand(realm: Realm, finOrVin: String, command: Command) {
        realm.createObject<RealmCommand>(generateCommandKey(command, finOrVin)).apply {
            name = command.name.ordinal
            available = command.available
            parameters = createOrUpdateCommandParameters(parameters, command.parameters)
            additionalInformation = RealmList<String>().apply { addAll(command.additionalInformation) }
            this.finOrVin = finOrVin

            realm.copyToRealmOrUpdate(this)
        }
    }

    private fun createOrUpdateCommandParameters(
        realmParameters: RealmList<RealmCommandParameter>,
        parameters: List<CommandParameter>
    ): RealmList<RealmCommandParameter> {
        realmParameters.forEach { it.cascadeDeleteFromRealm() }
        realmParameters.deleteAllFromRealm()
        realmParameters.clear()

        return realmParameters.apply {
            parameters.forEach {
                add(createOrUpdateCommandParameter(it))
            }
        }
    }

    private fun createOrUpdateCommandParameter(commandParameter: CommandParameter): RealmCommandParameter {
        return realm.createObject<RealmCommandParameter>().apply {
            name = commandParameter.name.ordinal
            minValue = commandParameter.minValue
            maxValue = commandParameter.maxValue
            steps = commandParameter.steps
            allowedEnums = createOrUpdateAllowedEnums(RealmList(), commandParameter.allowedEnums)
            allowedBools = commandParameter.allowedBools.ordinal
        }
    }

    private fun createOrUpdateAllowedEnums(realmEnums: RealmList<Int>, enums: List<AllowedEnums>): RealmList<Int> {
        return realmEnums.apply {
            clear()
            enums.forEach {
                add(it.ordinal)
            }
        }
    }

    private fun generateCommandKey(command: Command, finOrVin: String) =
        "${command.name.name}_$finOrVin"

    private fun <T> Array<T>.getOrDefault(index: Int?, defaultValue: (Int) -> T) =
        getOrElse(index ?: -1, defaultValue)
}
