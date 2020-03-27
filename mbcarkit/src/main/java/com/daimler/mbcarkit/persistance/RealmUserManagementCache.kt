package com.daimler.mbcarkit.persistance

import com.daimler.mbcarkit.business.model.vehicleusers.VehicleAssignedUser
import com.daimler.mbcarkit.business.model.vehicleusers.VehicleAssignedUserStatus
import com.daimler.mbcarkit.business.model.vehicleusers.VehicleLocalProfile
import com.daimler.mbcarkit.business.model.vehicleusers.VehicleUserManagement
import com.daimler.mbcarkit.persistance.model.RealmVehicleAssignedUser
import com.daimler.mbcarkit.persistance.model.RealmVehicleLocalProfiles
import com.daimler.mbcarkit.persistance.model.RealmVehicleUserManagement
import com.daimler.mbcarkit.persistance.model.cascadeDeleteFromRealm
import com.daimler.mbcarkit.socket.UserManagementCache
import io.realm.Realm
import io.realm.RealmList
import io.realm.kotlin.delete
import io.realm.kotlin.where
import java.util.Date

class RealmUserManagementCache constructor(private val realm: Realm) : UserManagementCache {

    override fun updateUserManagement(userManagement: VehicleUserManagement) {
        realm.executeTransaction { realm ->
            RealmVehicleUserManagement().also {

                it.finOrVin = userManagement.finOrVin

                it.maxProfiles = userManagement.maxProfiles

                it.occupiedProfiles = userManagement.occupiedProfiles

                userManagement.owner?.let { updateOwner ->
                    it.owner = mapVehicleAssignedUserToRealmAssignedUser(updateOwner)
                }

                userManagement.users?.let { updateUsers ->
                    it.users = RealmList<RealmVehicleAssignedUser>().apply {
                        addAll(updateUsers.map { mapVehicleAssignedUserToRealmAssignedUser(it) })
                    }
                }

                realm.copyToRealmOrUpdate(it)
            }
        }
    }

    override fun loadUserManagement(finOrVin: String): VehicleUserManagement? {
        return realm.where<RealmVehicleUserManagement>()
            .equalTo(RealmVehicleUserManagement.FIELD_ID, finOrVin)
            .findFirst()?.let {
                mapRealmUserManagementToUserManagement(it)
            }
    }

    override fun deleteUserManagement(finOrVin: String) {
        realm.executeTransaction { realm ->
            realm.where<RealmVehicleUserManagement>()
                .equalTo(RealmVehicleUserManagement.FIELD_ID, finOrVin)
                .findAll()?.forEach {
                    it.cascadeDeleteFromRealm()
                }
        }
    }

    override fun upgradeTemporaryUser(authorizationId: String) {
        realm.executeTransaction { realm ->
            realm.where<RealmVehicleAssignedUser>()
                .equalTo(RealmVehicleAssignedUser.FIELD_ID, authorizationId)
                .findFirst()
                ?.let {
                    it.status = VehicleAssignedUserStatus.VALID_USER.ordinal
                    it.validUntil = null
                    realm.copyToRealmOrUpdate(it)
                }
        }
    }

    override fun deleteAll() {
        realm.executeTransaction {
            it.delete<RealmVehicleAssignedUser>()
            it.delete<RealmVehicleLocalProfiles>()
            it.delete<RealmVehicleUserManagement>()
        }
    }

    private fun mapVehicleAssignedUserToRealmAssignedUser(vehicleAssignedUser: VehicleAssignedUser): RealmVehicleAssignedUser {
        return RealmVehicleAssignedUser().apply {
            authorizationId = vehicleAssignedUser.authorizationId
            displayName = vehicleAssignedUser.displayName
            email = vehicleAssignedUser.email
            mobileNumber = vehicleAssignedUser.mobileNumber
            userImageUrl = vehicleAssignedUser.profilePictureLink
            status = vehicleAssignedUser.status.ordinal
            validUntil = vehicleAssignedUser.validUntil?.time
        }
    }

    private fun mapRealmUserManagementToUserManagement(realmUserManagement: RealmVehicleUserManagement): VehicleUserManagement =
        VehicleUserManagement(
            realmUserManagement.finOrVin,
            realmUserManagement.maxProfiles,
            realmUserManagement.occupiedProfiles,
            mapRealmAssignedUserToAssignedUser(realmUserManagement.owner),
            mapRealmAssignedUserListToAssignedUserList(realmUserManagement.users),
            mapRealmLocalProfileListToLocalProfileList(realmUserManagement.profiles)
        )

    private fun mapRealmLocalProfileListToLocalProfileList(realmProfiles: RealmList<RealmVehicleLocalProfiles>?): List<VehicleLocalProfile> {
        return realmProfiles?.map { VehicleLocalProfile(it.id, it.name) } ?: listOf()
    }

    private fun mapRealmAssignedUserToAssignedUser(realmAssignedUser: RealmVehicleAssignedUser?): VehicleAssignedUser? {
        return realmAssignedUser?.let {
            VehicleAssignedUser(
                it.authorizationId,
                it.displayName,
                it.email,
                it.mobileNumber,
                it.userImageUrl,
                VehicleAssignedUserStatus.values().getOrElse(it.status) { VehicleAssignedUserStatus.UNKNOWN },
                it.validUntil?.let { Date(it) }
            )
        }
    }

    private fun mapRealmAssignedUserListToAssignedUserList(realmAssignedUserList: RealmList<RealmVehicleAssignedUser>?): List<VehicleAssignedUser>? =
        realmAssignedUserList?.mapNotNull { mapRealmAssignedUserToAssignedUser(it) }
}
