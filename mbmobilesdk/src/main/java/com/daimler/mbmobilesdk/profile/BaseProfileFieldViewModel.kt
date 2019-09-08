package com.daimler.mbmobilesdk.profile

import android.app.Application
import com.daimler.mbmobilesdk.implementation.CacheAwareProfileFieldsLoader
import com.daimler.mbmobilesdk.implementation.UserServiceProfileFieldsLoader
import com.daimler.mbmobilesdk.profile.creator.DynamicProfileCreator
import com.daimler.mbmobilesdk.profile.creator.ProfileItemsUpdater
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileFieldActionHandler
import com.daimler.mbmobilesdk.profile.fields.ProfileFieldResolutionStrategy
import com.daimler.mbmobilesdk.profile.fields.ProfileViewable
import com.daimler.mbmobilesdk.profile.format.ProfileFieldValueFormatter
import com.daimler.mbmobilesdk.profile.format.ProfileFormatters
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.ProfileFieldsData
import com.daimler.mbingresskit.common.User
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbuikit.components.viewmodels.MBBaseToolbarViewModel

/**
 * Basic ViewModel implementation for ViewModels that need to handle profile data.
 * Call [loadProfileFields] to start the flow.
 */
internal abstract class BaseProfileFieldViewModel<T : ProfileViewable>(
    app: Application
) : MBBaseToolbarViewModel(app), ProfileFieldActionHandler {

    private val _fields = mutableListOf<ProfileField>()
    protected val fields: List<ProfileField>
        get() = _fields

    /**
     * True to allow a cached variation of the profile fields.
     */
    protected open val allowCachedProfileFields = false

    /**
     * Returns a list of [ProfileField] using the given [ProfileFieldsData] as input.
     */
    protected abstract fun prepareProfileFields(fieldsData: ProfileFieldsData): List<ProfileField>

    /**
     * Returns the [ProfileFieldResolutionStrategy] used to specify the items that will be handled.
     */
    protected abstract fun getResolutionStrategy(): ProfileFieldResolutionStrategy

    /**
     * Returns the [DynamicProfileCreator] that creates the profile items.
     */
    protected abstract fun getItemsCreator(
        app: Application,
        user: User,
        resolutionStrategy: ProfileFieldResolutionStrategy,
        formatter: ProfileFieldValueFormatter
    ): DynamicProfileCreator<T>

    /**
     * Called when the profile items are created.
     */
    protected abstract fun onItemsCreated(items: List<T>)

    /**
     * Called when the service call to retrieve the profile fields was successful.
     */
    protected open fun onProfileFieldsLoaded(fieldsData: ProfileFieldsData) = Unit

    /**
     * Called when the service call to retrieve the profile fields failed.
     */
    protected open fun onProfileFieldsLoadingFailed(error: ResponseError<out RequestError>?) = Unit

    /**
     * Loads the profile fields for the given user.
     * A cached variation of the fields is used (if available) if [allowCachedProfileFields] is
     * set to true.
     * [onProfileFieldsLoaded] or [onProfileFieldsLoadingFailed] is called when the service call
     * finished.
     * In case of a success, the following methods will be called:
     *  1. [prepareProfileFields]
     *  2. [getResolutionStrategy]
     *  3. [getItemsCreator]
     *  4. [onItemsCreated]
     */
    protected fun loadProfileFields(user: User) {
        val loader = if (allowCachedProfileFields) {
            CacheAwareProfileFieldsLoader(MBIngressKit.userService())
        } else {
            UserServiceProfileFieldsLoader(MBIngressKit.userService())
        }
        loader.loadFields(user.countryCode, user.languageCode)
            .onComplete {
                onProfileFieldsLoaded(it)
                profileDataLoaded(it, user)
            }.onFailure {
                onProfileFieldsLoadingFailed(it)
            }
    }

    /**
     * Returns a [ProfileFieldValueFormatter] used to format profile values.
     */
    protected fun formatter(user: User, viewables: List<T>) =
        ProfileFormatters.createFormatterForUser(getApplication(), user,
            viewables.mapNotNull { it.associatedField() })

    protected fun formatter(user: User) =
        ProfileFormatters.createFormatterForUser(getApplication(), user, fields)

    /**
     * Updates the content value of all items in the given list using the given user.
     */
    protected fun updateProfileItems(user: User, items: List<T>) {
        ProfileItemsUpdater(formatter(user, items)).updateViewableValues(items)
    }

    /**
     * Same as [updateProfileItems] but uses only items for which the given [filterAction] returns
     * true.
     */
    protected fun updateProfileItems(user: User, items: List<T>, filterAction: (T) -> Boolean) {
        val list = items.filter(filterAction)
        if (list.isNotEmpty()) {
            updateProfileItems(user, list)
        }
    }

    /**
     * Same as [updateProfileItems] but uses the fields returned by [prepareProfileFields].
     */
    protected fun updateForFields(user: User, items: List<T>) {
        ProfileItemsUpdater(formatter(user)).updateViewableValues(items)
    }

    protected inline fun <reified T : ProfileField> getProfileField(): T? =
        _fields.find { it is T } as? T

    private fun profileDataLoaded(fieldsData: ProfileFieldsData, user: User) {
        val fields = prepareProfileFields(fieldsData)
        _fields.clear()
        _fields.addAll(fields)
        val resolutionStrategy = getResolutionStrategy()
        val creator = getItemsCreator(getApplication(), user, resolutionStrategy,
            ProfileFormatters.createFormatterForUser(getApplication(), user, fields))
        val items = creator.createViewables(fields)
        creator.destroy()
        onItemsCreated(items)
    }
}