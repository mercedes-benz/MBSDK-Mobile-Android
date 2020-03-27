package com.daimler.mbcarkit.persistance

import com.daimler.mbcarkit.business.VehicleImageCache
import com.daimler.mbcarkit.business.model.vehicle.image.ImageKey
import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImage
import com.daimler.mbcarkit.business.model.vehicle.image.primaryKey
import com.daimler.mbcarkit.persistance.RealmUtil.Companion.FIELD_IMAGE_KEY
import com.daimler.mbcarkit.persistance.model.RealmVehicleImage
import com.daimler.mbloggerkit.MBLoggerKit
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.delete
import io.realm.kotlin.where

class RealmVehicleImageCache constructor(private val realm: Realm) : VehicleImageCache {

    override fun updateImage(image: VehicleImage) {
        if (isNotEmptyImage(image)) {
            val primaryKey = image.primaryKey()
            var realmImage = realm.where<RealmVehicleImage>().equalTo(FIELD_IMAGE_KEY, primaryKey).findFirst()
            realm.executeTransaction {
                if (realmImage == null) {
                    MBLoggerKit.d("Create $primaryKey in Realm")
                    realmImage = realm.createObject(image.primaryKey())
                } else {
                    MBLoggerKit.d("Update $primaryKey in Realm")
                }
                realmImage?.apply {
                    imageData = image.imageBytes
                    imageKey = image.imageKey
                    finOrVin = image.finOrVin
                }
                realmImage?.let { image -> realm.copyToRealmOrUpdate(image) }
            }
        } else {
            throw IllegalArgumentException("VehicleImage ${image.imageKey} has an empty Image")
        }
    }

    override fun loadImage(finOrVin: String, key: ImageKey): VehicleImage {
        return realm.where<RealmVehicleImage>().equalTo(FIELD_IMAGE_KEY, templateImage(finOrVin, key).primaryKey()).findFirst()?.let {
            mapRealmImageToVehicleImage(it)
        } ?: templateImage(finOrVin, key)
    }

    override fun clear() {
        realm.executeTransaction {
            it.delete<RealmVehicleImage>()
        }
    }

    private fun mapRealmImageToVehicleImage(realmVehicleImage: RealmVehicleImage): VehicleImage {
        return VehicleImage(realmVehicleImage.finOrVin, realmVehicleImage.imageKey, realmVehicleImage.imageData)
    }

    private fun templateImage(finOrVin: String, key: ImageKey): VehicleImage {
        return VehicleImage(finOrVin, key.key, null)
    }

    private fun isNotEmptyImage(image: VehicleImage): Boolean {
        return image.imageBytes != null && image.imageBytes.isNotEmpty()
    }
}
