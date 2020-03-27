package com.daimler.mbcarkit.persistance

import com.daimler.mbcarkit.business.model.merchants.Address
import com.daimler.mbcarkit.business.model.merchants.Day
import com.daimler.mbcarkit.business.model.merchants.Merchant
import com.daimler.mbcarkit.business.model.merchants.OpeningHours
import com.daimler.mbcarkit.business.model.merchants.Period
import com.daimler.mbcarkit.business.model.toEnum
import com.daimler.mbcarkit.business.model.vehicle.AssignmentPendingState
import com.daimler.mbcarkit.business.model.vehicle.DataCollectorVersion
import com.daimler.mbcarkit.business.model.vehicle.DoorsCount
import com.daimler.mbcarkit.business.model.vehicle.FuelType
import com.daimler.mbcarkit.business.model.vehicle.HandDrive
import com.daimler.mbcarkit.business.model.vehicle.NormalizedProfileControlSupport
import com.daimler.mbcarkit.business.model.vehicle.ProfileSyncSupport
import com.daimler.mbcarkit.business.model.vehicle.RoofType
import com.daimler.mbcarkit.business.model.vehicle.StarArchitecture
import com.daimler.mbcarkit.business.model.vehicle.TcuHardwareVersion
import com.daimler.mbcarkit.business.model.vehicle.TcuSoftwareVersion
import com.daimler.mbcarkit.business.model.vehicle.TirePressureMonitoringState
import com.daimler.mbcarkit.business.model.vehicle.VehicleAmenity
import com.daimler.mbcarkit.business.model.vehicle.VehicleConnectivity
import com.daimler.mbcarkit.business.model.vehicle.VehicleDealer
import com.daimler.mbcarkit.business.model.vehicle.VehicleInfo
import com.daimler.mbcarkit.business.model.vehicle.VehicleSegment
import com.daimler.mbcarkit.business.model.vehicle.Vehicles
import com.daimler.mbcarkit.business.model.vehicle.WindowsLiftCount
import com.daimler.mbcarkit.persistance.RealmUtil.Companion.FIELD_VIN_OR_FIN
import com.daimler.mbcarkit.persistance.model.RealmDayPeriod
import com.daimler.mbcarkit.persistance.model.RealmMerchant
import com.daimler.mbcarkit.persistance.model.RealmMerchantAddress
import com.daimler.mbcarkit.persistance.model.RealmMerchantOpeningHours
import com.daimler.mbcarkit.persistance.model.RealmOpeningDay
import com.daimler.mbcarkit.persistance.model.RealmVehicle
import com.daimler.mbcarkit.persistance.model.RealmVehicleDealer
import com.daimler.mbcarkit.persistance.model.cascadeDeleteFromRealm
import com.daimler.mbcarkit.persistance.model.fromVehicleInfoNoDealers
import com.daimler.mbcarkit.socket.VehicleCache
import com.daimler.mbloggerkit.MBLoggerKit
import io.realm.Realm
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.kotlin.createObject
import io.realm.kotlin.delete
import io.realm.kotlin.where
import java.util.Date

class RealmVehicleCache constructor(private val realm: Realm) : VehicleCache {

    override fun updateVehicles(vehicles: Vehicles) {
        val realmVehiclesByVin = realm.where<RealmVehicle>().findAll()
            .filter { it.finOrVin.isNotEmpty() }
            .map { it.finOrVin to it }
            .toMap()
        realm.executeTransaction {
            vehicles.forEach { vehicle ->
                var realmVehicle = realmVehiclesByVin[vehicle.finOrVin]
                if (realmVehicle == null) {
                    MBLoggerKit.d("Create $vehicle in Realm vehicle cache")
                    realmVehicle = realm.createObject(vehicle.finOrVin)
                } else {
                    MBLoggerKit.d("Update $vehicle in Realm vehicle cache")
                }
                realmVehicle.fromVehicleInfoNoDealers(vehicle)
                realmVehicle.dealers = createOrUpdateVehicleDealers(realmVehicle.dealers, vehicle.dealers)
                realm.copyToRealmOrUpdate(realmVehicle)
            }
        }
    }

    override fun deleteVehicle(finOrVin: String) {
        realm.executeTransaction {
            val realmVehicle = realm.where<RealmVehicle>()
                .equalTo(FIELD_VIN_OR_FIN, finOrVin)
                .findFirst()
            val deleted = realmVehicle?.let { vehicleToDelete ->
                vehicleToDelete.dealers?.toList()?.forEach {
                    it.cascadeDeleteFromRealm()
                }
                vehicleToDelete.dealers?.deleteAllFromRealm()
                vehicleToDelete.deleteFromRealm()
                true
            } ?: false
            if (deleted) {
                MBLoggerKit.d("Deleted $finOrVin from Realm Vehicle Cache.")
            }
        }
    }

    override fun deleteVehicles(vehicles: Vehicles) {
        realm.executeTransaction {
            vehicles.forEach {
                val realmVehiclesForRemove = realm.where<RealmVehicle>()
                    .equalTo(FIELD_VIN_OR_FIN, it.finOrVin)
                    .findAll()
                realmVehiclesForRemove.forEach { rv ->
                    rv.cascadeDeleteFromRealm()
                }
                realmVehiclesForRemove.deleteAllFromRealm()
                MBLoggerKit.d("Deleted ${it.finOrVin} from Realm vehicle cache")
            }
        }
    }

    override fun loadVehicles(): Vehicles {
        val cachedVehicles = mutableListOf<VehicleInfo>()
        val realmVehicles = realm.where<RealmVehicle>().findAll()
        realmVehicles?.forEach {
            cachedVehicles.add(mapRealmVehicleToVehicleInfo(it))
        }
        return Vehicles(cachedVehicles)
    }

    override fun clearVehicles() {
        realm.executeTransaction { realm ->
            realm.delete<RealmDayPeriod>()
            realm.delete<RealmOpeningDay>()
            realm.delete<RealmMerchantOpeningHours>()
            realm.delete<RealmMerchantAddress>()
            realm.delete<RealmMerchant>()
            realm.delete<RealmVehicleDealer>()
            realm.delete<RealmVehicle>()
            MBLoggerKit.d("Deleted vehicles from Realm vehicle cache")
        }
    }

    override fun loadVehicleByVin(finOrVin: String): VehicleInfo? {
        val realmVehicle = realm.where<RealmVehicle>()
            .equalTo(FIELD_VIN_OR_FIN, finOrVin)
            .findFirst()
        return realmVehicle?.let { mapRealmVehicleToVehicleInfo(it) }
    }

    private fun mapRealmVehicleToVehicleInfo(vehicle: RealmVehicle) =
        VehicleInfo(
            vehicle.vin,
            vehicle.fin,
            vehicle.licensePlate ?: "",
            vehicle.model ?: "",
            vehicle.assignmentState.toEnum<AssignmentPendingState>(),
            vehicle.trustLevel ?: 0,
            vehicle.baumuster ?: "",
            vehicle.tirePressureMonitoringState.toEnum<TirePressureMonitoringState>(),
            mapRealmDealersToVehicleDealers(vehicle.dealers) ?: emptyList(),
            vehicle.carline,
            vehicle.dataCollectionVersion.toEnum<DataCollectorVersion>(),
            vehicle.doorsCount.toEnum<DoorsCount>(),
            vehicle.fuelType.toEnum<FuelType>(),
            vehicle.handDrive.toEnum<HandDrive>(),
            vehicle.hasAuxHeat ?: false,
            vehicle.mopf ?: false,
            vehicle.roofType.toEnum<RoofType>(),
            vehicle.starArchitecture.toEnum<StarArchitecture>(),
            vehicle.tcuHardwareVersion.toEnum<TcuHardwareVersion>(),
            vehicle.windowsLiftCount.toEnum<WindowsLiftCount>(),
            vehicle.vehicleConnectivity.toEnum<VehicleConnectivity>(),
            vehicle.tcuSoftwareVersion.toEnum<TcuSoftwareVersion>(),
            vehicle.vehicleSegment.toEnum<VehicleSegment>(),
            vehicle.isOwner ?: false,
            vehicle.indicatorImageUrl,
            vehicle.temporarilyAccessible ?: false,
            vehicle.accessibleUntil?.let { Date(it) },
            vehicle.profileSyncSupport.toEnum<ProfileSyncSupport>(),
            vehicle.normalizedProfileControlSupport.toEnum<NormalizedProfileControlSupport>(),
            vehicle.paintCode?.let { VehicleAmenity(it, vehicle.paintDescription) },
            vehicle.upholsteryCode?.let { VehicleAmenity(it, vehicle.upholsteryDescription) },
            vehicle.lineCode?.let { VehicleAmenity(it, vehicle.lineDescription) }
        )

    private fun mapRealmDealersToVehicleDealers(realmDealers: List<RealmVehicleDealer>?) =
        realmDealers?.map { realmDealer ->
            VehicleDealer(
                realmDealer.dealerId.orEmpty(),
                VehicleDealer.dealerRoleFromInt(realmDealer.role),
                realmDealer.updatedAt?.let { Date(it) },
                realmDealer.merchant?.let { mapRealmMerchantToMerchant(it) }
            )
        }

    private fun mapRealmMerchantToMerchant(realmMerchant: RealmMerchant) =
        Merchant(
            realmMerchant.id,
            realmMerchant.legalName,
            realmMerchant.address?.let { mapRealmAddressToAddress(it) },
            null,
            null,
            null,
            null,
            realmMerchant.openingHours?.let { mapRealmOpeningHoursToOpeningHours(it) }
        )

    private fun mapRealmAddressToAddress(realmAddress: RealmMerchantAddress) =
        Address(
            realmAddress.street,
            realmAddress.addressAddition,
            realmAddress.zipCode,
            realmAddress.city,
            realmAddress.district,
            realmAddress.countryIsoCode
        )

    private fun mapRealmOpeningHoursToOpeningHours(realmHours: RealmMerchantOpeningHours) =
        OpeningHours(
            realmHours.monday?.let { mapRealmDayToDay(it) },
            realmHours.tuesday?.let { mapRealmDayToDay(it) },
            realmHours.wednesday?.let { mapRealmDayToDay(it) },
            realmHours.thursday?.let { mapRealmDayToDay(it) },
            realmHours.friday?.let { mapRealmDayToDay(it) },
            realmHours.saturday?.let { mapRealmDayToDay(it) },
            realmHours.sunday?.let { mapRealmDayToDay(it) }
        )

    private fun mapRealmDayToDay(realmDay: RealmOpeningDay) =
        Day(
            realmDay.status,
            realmDay.periods?.map { mapRealmPeriodToPeriod(it) }
        )

    private fun mapRealmPeriodToPeriod(realmPeriod: RealmDayPeriod) =
        Period(
            realmPeriod.from,
            realmPeriod.until
        )

    /* NOTE: Only call this within a realm.executeTransaction {} block. */
    private fun createOrUpdateVehicleDealers(currentRealmDealers: RealmList<RealmVehicleDealer>?, dealers: List<VehicleDealer>): RealmList<RealmVehicleDealer> {
        currentRealmDealers?.toList()?.forEach { it.cascadeDeleteFromRealm() }
        currentRealmDealers?.deleteAllFromRealm()
        val realmDealers = dealers.map {
            val primaryKey = generateRealmDealerPrimaryKey(it)
            val realmDealer = realm.where<RealmVehicleDealer>()
                .equalTo(RealmVehicleDealer.FIELD_ID, primaryKey).findFirst()
                ?: realm.createObject(primaryKey)
            realmDealer.apply {
                dealerId = it.dealerId
                role = it.role.ordinal
                updatedAt = it.updatedAt?.time
                merchant = it.merchant?.let { dealerMerchant -> createOrUpdateMerchant(merchant, dealerMerchant) }
                this@RealmVehicleCache.realm.copyToRealmOrUpdate(this)
            }
        }

        return realmDealers.toRealmList()
    }

    /* NOTE: Only call this within a realm.executeTransaction {} block. */
    private fun createOrUpdateMerchant(current: RealmMerchant?, merchant: Merchant?): RealmMerchant? {
        return merchant?.let {
            val realmMerchant = current ?: getOrCreateMerchant(it.id)
            realmMerchant.apply {
                legalName = it.legalName
                address = createOrUpdateMerchantAddress(address, it.address)
                openingHours = createOrUpdateOpeningHours(openingHours, it.openingHours)

                this@RealmVehicleCache.realm.copyToRealmOrUpdate(this)
            }
        } ?: run {
            current?.cascadeDeleteFromRealm()
            null
        }
    }

    private fun getOrCreateMerchant(id: String): RealmMerchant =
        realm.where<RealmMerchant>().equalTo(RealmMerchant.FIELD_ID, id).findFirst()
            ?: realm.createObject(id)

    /* NOTE: Only call this within a realm.executeTransaction {} block. */
    private fun createOrUpdateMerchantAddress(current: RealmMerchantAddress?, address: Address?): RealmMerchantAddress? {
        current?.deleteFromRealm()
        return address?.let {
            realm.createObject<RealmMerchantAddress>().apply {
                street = it.street
                addressAddition = it.addressAddition
                zipCode = it.zipCode
                city = it.city
                district = it.district
                countryIsoCode = it.countryIsoCode
            }
        }
    }

    /* NOTE: Only call this within a realm.executeTransaction {} block. */
    private fun createOrUpdateOpeningHours(current: RealmMerchantOpeningHours?, openingHours: OpeningHours?): RealmMerchantOpeningHours? {
        return openingHours?.let {
            realm.createObject<RealmMerchantOpeningHours>().apply {
                monday = createOrUpdateOpeningDay(monday, it.monday)
                tuesday = createOrUpdateOpeningDay(tuesday, it.tuesday)
                wednesday = createOrUpdateOpeningDay(wednesday, it.wednesday)
                thursday = createOrUpdateOpeningDay(thursday, it.thursday)
                friday = createOrUpdateOpeningDay(friday, it.friday)
                saturday = createOrUpdateOpeningDay(saturday, it.saturday)
                sunday = createOrUpdateOpeningDay(sunday, it.sunday)
            }
        } ?: run {
            current?.cascadeDeleteFromRealm()
            current?.deleteFromRealm()
            null
        }
    }

    /* NOTE: Only call this within a realm.executeTransaction {} block. */
    private fun createOrUpdateOpeningDay(current: RealmOpeningDay?, day: Day?): RealmOpeningDay? {
        return day?.let {
            val realmDay = current ?: realm.createObject()
            realmDay.apply {
                status = it.status
                periods = createOrUpdatePeriods(periods, it.periods)
            }
        } ?: run {
            current?.cascadeDeleteFromRealm()
            null
        }
    }

    /* NOTE: Only call this within a realm.executeTransaction {} block. */
    private fun createOrUpdatePeriods(current: RealmList<RealmDayPeriod>?, periods: List<Period>?): RealmList<RealmDayPeriod>? {
        current?.deleteAllFromRealm()
        return periods?.map {
            realm.createObject<RealmDayPeriod>().apply {
                from = it.from
                until = it.until
            }
        }?.toRealmList()
    }

    private fun generateRealmDealerPrimaryKey(dealer: VehicleDealer) =
        "${dealer.dealerId}-${dealer.role.ordinal}"

    private fun <T : RealmObject> List<T>.toRealmList(): RealmList<T> {
        return RealmList<T>().apply {
            this@toRealmList.forEach { add(it) }
        }
    }
}
