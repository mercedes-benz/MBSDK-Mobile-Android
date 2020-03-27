package com.daimler.mbingresskit.login

import com.daimler.mbingresskit.common.Countries
import com.daimler.mbingresskit.common.ProfileFieldsData
import com.daimler.mbingresskit.common.UnitPreferences
import com.daimler.mbingresskit.common.User
import com.daimler.mbingresskit.common.UserAdaptionValues
import com.daimler.mbingresskit.common.UserBodyHeight
import com.daimler.mbingresskit.common.UserPinStatus
import com.daimler.mbingresskit.implementation.etag.ETagProvider
import com.daimler.mbingresskit.implementation.etag.profilePicture
import com.daimler.mbingresskit.persistence.CountryCache
import com.daimler.mbingresskit.persistence.ProfileFieldsCache
import com.daimler.mbingresskit.persistence.UserCache
import com.daimler.mbnetworkkit.header.HeaderService
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

/**
 * This class extends [UserService] to add possibility to load a cached user if required. All other
 * calls will directly be delegated to the passed [userServiceDelegate]
 */
internal class CachedUserService(
    private val userServiceDelegate: UserService,
    private val userCache: UserCache,
    private val profileFieldsCache: ProfileFieldsCache,
    private val countryCache: CountryCache,
    private val headerService: HeaderService,
    private val eTagProvider: ETagProvider
) : UserService by userServiceDelegate {

    override fun loadUser(jwtToken: String): FutureTask<User, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<User, ResponseError<out RequestError>?>()
        userServiceDelegate.loadUser(jwtToken)
            .onComplete {
                userCache.createOrUpdateUser(it)
                deferredTask.complete(it)
            }.onFailure { error ->
                userCache.loadUser()?.let {
                    deferredTask.complete(it)
                } ?: deferredTask.fail(error)
            }
        return deferredTask.futureTask()
    }

    override fun updateUser(jwtToken: String, user: User): FutureTask<User, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<User, ResponseError<out RequestError>?>()
        userServiceDelegate.updateUser(jwtToken, user)
            .onComplete {
                userCache.createOrUpdateUser(it)
                deferredTask.complete(it)
            }.onFailure {
                deferredTask.fail(it)
            }
        return deferredTask.futureTask()
    }

    override fun deleteUser(jwtToken: String, user: User): FutureTask<Unit, ResponseError<out RequestError>?> {
        return userServiceDelegate.deleteUser(jwtToken, user)
    }

    override fun updateProfilePicture(jwtToken: String, bitmapByteArray: ByteArray, mediaType: String): FutureTask<Unit, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<Unit, ResponseError<out RequestError>?>()
        userServiceDelegate.updateProfilePicture(jwtToken, bitmapByteArray, mediaType)
            .onComplete {
                userCache.updateUserImage(bitmapByteArray)
                deferredTask.complete(it)
            }.onFailure {
                deferredTask.fail(it)
            }
        return deferredTask.futureTask()
    }

    override fun fetchProfilePictureBytes(jwtToken: String): FutureTask<ByteArray, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<ByteArray, ResponseError<out RequestError>?>()
        val cachedImage = userCache.loadUserImage()
        if (cachedImage == null) {
            // delete remaining e-tag to ensure a re-fetch
            eTagProvider.profilePicture = null
        }
        userServiceDelegate.fetchProfilePictureBytes(jwtToken)
            .onComplete {
                userCache.updateUserImage(it)
                deferredTask.complete(it)
            }.onFailure { error ->
                cachedImage?.let {
                    deferredTask.complete(it)
                } ?: deferredTask.fail(error)
            }
        return deferredTask.futureTask()
    }

    override fun fetchCountries(): FutureTask<Countries, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<Countries, ResponseError<out RequestError>?>()
        userServiceDelegate.fetchCountries()
            .onComplete {
                countryCache.overwriteCache(it.countries, headerService.currentNetworkLocale())
                deferredTask.complete(it)
            }.onFailure { error ->
                countryCache.loadCountries(headerService.currentNetworkLocale())?.let {
                    deferredTask.complete(Countries(it))
                } ?: deferredTask.fail(error)
            }
        return deferredTask.futureTask()
    }

    override fun setPin(jwtToken: String, pin: String): FutureTask<Unit, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<Unit, ResponseError<out RequestError>?>()
        userServiceDelegate.setPin(jwtToken, pin)
            .onComplete { unit ->
                userCache.updateUser { it.copy(pinStatus = UserPinStatus.SET) }
                deferredTask.complete(unit)
            }.onFailure {
                deferredTask.fail(it)
            }
        return deferredTask.futureTask()
    }

    override fun updateUnitPreferences(jwtToken: String, unitPreferences: UnitPreferences): FutureTask<Unit, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<Unit, ResponseError<out RequestError>?>()
        userServiceDelegate.updateUnitPreferences(jwtToken, unitPreferences)
            .onComplete { unit ->
                userCache.updateUser { it.copy(unitPreferences = unitPreferences) }
                deferredTask.complete(unit)
            }.onFailure {
                deferredTask.fail(it)
            }
        return deferredTask.futureTask()
    }

    override fun updateAdaptionValues(jwtToken: String, bodyHeight: UserBodyHeight): FutureTask<Unit, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<Unit, ResponseError<out RequestError>?>()
        userServiceDelegate.updateAdaptionValues(jwtToken, bodyHeight)
            .onComplete { unit ->
                userCache.updateUser { it.copy(bodyHeight = bodyHeight) }
                deferredTask.complete(unit)
            }.onFailure {
                deferredTask.fail(it)
            }
        return deferredTask.futureTask()
    }

    override fun updateAdaptionValues(jwtToken: String, userAdaptionValues: UserAdaptionValues): FutureTask<Unit, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<Unit, ResponseError<out RequestError>?>()
        userServiceDelegate.updateAdaptionValues(jwtToken, userAdaptionValues)
            .onComplete { unit ->
                userCache.updateUser { it.copy(adaptionValues = userAdaptionValues) }
                deferredTask.complete(unit)
            }.onFailure {
                deferredTask.fail(it)
            }
        return deferredTask.futureTask()
    }

    override fun fetchProfileFields(countryCode: String, locale: String?): FutureTask<ProfileFieldsData, ResponseError<out RequestError>?> {
        val deferredTask = TaskObject<ProfileFieldsData, ResponseError<out RequestError>?>()
        val localeToUse = locale ?: headerService.currentNetworkLocale()
        userServiceDelegate.fetchProfileFields(countryCode, localeToUse)
            .onComplete {
                profileFieldsCache.createOrUpdateProfileFields(countryCode, localeToUse, it)
                deferredTask.complete(it)
            }.onFailure { error ->
                profileFieldsCache.loadProfileFields(countryCode, localeToUse)?.let {
                    deferredTask.complete(it)
                } ?: deferredTask.fail(error)
            }
        return deferredTask.futureTask()
    }
}
