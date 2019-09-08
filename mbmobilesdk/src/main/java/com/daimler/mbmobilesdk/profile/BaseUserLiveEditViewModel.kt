package com.daimler.mbmobilesdk.profile

import android.app.Application
import androidx.annotation.CallSuper
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.UserLocale
import com.daimler.mbmobilesdk.profile.edit.PickerEvent
import com.daimler.mbmobilesdk.profile.edit.ProfilePickerOptions
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileFieldActionHandler
import com.daimler.mbmobilesdk.profile.fields.ProfileViewable
import com.daimler.mbmobilesdk.utils.UserValuePolicy
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbingresskit.common.ProfileFieldsData
import com.daimler.mbingresskit.common.ProfileSelectableValues
import com.daimler.mbingresskit.common.User
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.utils.extensions.getString
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

internal abstract class BaseUserLiveEditViewModel<T : ProfileViewable>(
    app: Application
) : BaseProfileFieldViewModel<T>(app), ProfileFieldActionHandler {

    internal val birthdayPickerEvent = MutableLiveEvent<Long>()
    internal val showPickerEvent = MutableLiveEvent<ProfilePickerOptions>()
    internal val showCountrySelectionEvent = MutableLiveEvent<Pair<User, UserLocale>>()

    internal val inputErrorEvent = MutableLiveEvent<String>()

    protected abstract val userCreator: UserCreator
    private val collector = ProfileErrorCollector()

    fun user() = userCreator.user()

    override fun addActionCallback(profileField: ProfileField.AccountIdentifier, viewable: ProfileViewable) {
        // --
    }

    override fun addActionCallback(profileField: ProfileField.Email, viewable: ProfileViewable) {
        collector.applyCallback(viewable) { userCreator.pendingUser.email = it.orEmpty() }
    }

    override fun addActionCallback(profileField: ProfileField.Salutation, viewable: ProfileViewable) {
        collector.applyCallback(viewable) { userCreator.pendingUser.salutation = it.orEmpty() }
    }

    override fun addActionCallback(profileField: ProfileField.Title, viewable: ProfileViewable) {
        collector.applyCallback(viewable, false) { userCreator.pendingUser.title = it.orEmpty() }
    }

    override fun addActionCallback(profileField: ProfileField.FirstName, viewable: ProfileViewable) {
        collector.applyCallback(viewable) { userCreator.pendingUser.firstName = it.orEmpty() }
    }

    override fun addActionCallback(profileField: ProfileField.LastName, viewable: ProfileViewable) {
        collector.applyCallback(viewable) { userCreator.pendingUser.lastName = it.orEmpty() }
    }

    override fun addActionCallback(profileField: ProfileField.MarketCountryCode, viewable: ProfileViewable) {
        // --
    }

    override fun addActionCallback(profileField: ProfileField.LanguageCode, viewable: ProfileViewable) {
        if (profileField.externalSelection) {
            collector.applyCallback(viewable) {
                val map = HashMap<String, String>()
                showPickerEvent.sendEvent(
                    ProfilePickerOptions(
                        getString(R.string.profile_preferred_language),
                        userCreator.pendingUser.languageCode,
                        profileField.values.selectableValues.map { Pair(it.key, it.description) }.toMap(map),
                        PickerEvent.LANGUAGE
                    )
                )
            }
        } else {
            collector.applyCallback(viewable, false) { userCreator.pendingUser.languageCode = it.orEmpty() }
        }
    }

    override fun addActionCallback(profileField: ProfileField.Birthday, viewable: ProfileViewable) {
        collector.applyCallback(viewable) { birthdayPickerEvent.sendEvent(userCreator.pendingUser.birthday.toUserBirthday().time) }
    }

    override fun addActionCallback(profileField: ProfileField.MobilePhone, viewable: ProfileViewable) {
        collector.applyCallback(viewable) { userCreator.pendingUser.mobilePhone = it.orEmpty() }
    }

    override fun addActionCallback(profileField: ProfileField.LandlinePhone, viewable: ProfileViewable) {
        collector.applyCallback(viewable) { userCreator.pendingUser.landlinePhone = it.orEmpty() }
    }

    override fun addActionCallback(profileField: ProfileField.AddressCountryCode, viewable: ProfileViewable) {
        collector.applyCallback(viewable, false) {
            val user = user()
            val language = user.languageCode.split("-").takeIf {
                it.isNotEmpty()
            }?.first()
            showCountrySelectionEvent.sendEvent(user to UserLocale(user.countryCode, language.orEmpty()))
        }
    }

    override fun addActionCallback(profileField: ProfileField.AddressStreet, viewable: ProfileViewable) {
        collector.applyCallback(viewable) { userCreator.pendingUser.address.street = it }
    }

    override fun addActionCallback(profileField: ProfileField.AddressStreetType, viewable: ProfileViewable) {
        collector.applyCallback(viewable) { userCreator.pendingUser.address.streetType = it }
    }

    override fun addActionCallback(profileField: ProfileField.AddressHouseName, viewable: ProfileViewable) {
        collector.applyCallback(viewable) { userCreator.pendingUser.address.houseName = it }
    }

    override fun addActionCallback(profileField: ProfileField.AddressState, viewable: ProfileViewable) {
        handleSelectableOrText(viewable, profileField.externalSelection, profileField.values,
            PickerEvent.ADDRESS_STATE, getString(R.string.profile_state),
            { userCreator.pendingUser.address.state }, { userCreator.pendingUser.address.state = it })
    }

    override fun addActionCallback(profileField: ProfileField.AddressProvince, viewable: ProfileViewable) {
        handleSelectableOrText(viewable, profileField.externalSelection, profileField.values,
            PickerEvent.ADDRESS_PROVINCE, getString(R.string.profile_province),
            { userCreator.pendingUser.address.province }, { userCreator.pendingUser.address.province = it })
    }

    override fun addActionCallback(profileField: ProfileField.AddressFloorNumber, viewable: ProfileViewable) {
        collector.applyCallback(viewable) { userCreator.pendingUser.address.floorNumber = it }
    }

    override fun addActionCallback(profileField: ProfileField.AddressDoorNumber, viewable: ProfileViewable) {
        collector.applyCallback(viewable) { userCreator.pendingUser.address.doorNumber = it }
    }

    override fun addActionCallback(profileField: ProfileField.AddressHouseNumber, viewable: ProfileViewable) {
        collector.applyCallback(viewable) { userCreator.pendingUser.address.houseNumber = it }
    }

    override fun addActionCallback(profileField: ProfileField.AddressZipCode, viewable: ProfileViewable) {
        collector.applyCallback(viewable) { userCreator.pendingUser.address.zipCode = it }
    }

    override fun addActionCallback(profileField: ProfileField.AddressCity, viewable: ProfileViewable) {
        handleSelectableOrText(viewable, profileField.externalSelection, profileField.values,
            PickerEvent.ADDRESS_CITY, getString(R.string.profile_city),
            { userCreator.pendingUser.address.city }, { userCreator.pendingUser.address.city = it })
    }

    override fun addActionCallback(profileField: ProfileField.AddressLine1, viewable: ProfileViewable) {
        collector.applyCallback(viewable) { userCreator.pendingUser.address.addressLine1 = it }
    }

    override fun addActionCallback(profileField: ProfileField.AddressPostOfficeBox, viewable: ProfileViewable) {
        collector.applyCallback(viewable) { userCreator.pendingUser.address.postOfficeBox = it }
    }

    override fun addActionCallback(profileField: ProfileField.TaxNumber, viewable: ProfileViewable) {
        collector.applyCallback(viewable) { userCreator.pendingUser.taxNumber = it.orEmpty() }
    }

    override fun addActionCallback(profileField: ProfileField.BodyHeight, viewable: ProfileViewable) {
        collector.applyCallback(viewable) {
            userCreator.pendingUser.updateBodyHeight(
                it,
                userCreator.pendingUser.bodyHeight?.preAdjustment
            )
        }
    }

    override fun addActionCallback(profileField: ProfileField.PreAdjustment, viewable: ProfileViewable) {
        collector.applyCallback(viewable) {
            userCreator.pendingUser.updateBodyHeight(
                userCreator.pendingUser.bodyHeight?.bodyHeight.toString(),
                it != null
            )
        }
    }

    override fun addActionCallback(profileField: ProfileField.ContactPerMail, viewable: ProfileViewable) {
        collector.applyCallback(viewable) {
            userCreator.pendingUser.communicationPreference.contactByMail = it != null
        }
    }

    override fun addActionCallback(profileField: ProfileField.ContactPerPhone, viewable: ProfileViewable) {
        collector.applyCallback(viewable) {
            userCreator.pendingUser.communicationPreference.contactByPhone = it != null
        }
    }

    override fun addActionCallback(profileField: ProfileField.ContactPerSms, viewable: ProfileViewable) {
        collector.applyCallback(viewable) {
            userCreator.pendingUser.communicationPreference.contactBySms = it != null
        }
    }

    override fun addActionCallback(profileField: ProfileField.ContactPerLetter, viewable: ProfileViewable) {
        collector.applyCallback(viewable) {
            userCreator.pendingUser.communicationPreference.contactByLetter = it != null
        }
    }

    override fun addActionCallback(profileField: ProfileField.Communication, viewable: ProfileViewable) {
        // --
    }

    override fun addActionCallback(profileField: ProfileField.MePin, viewable: ProfileViewable) {
        // --
    }

    override fun addActionCallback(profileField: ProfileField.Address, viewable: ProfileViewable) {
        // --
    }

    override fun addActionCallback(profileField: ProfileField.Name, viewable: ProfileViewable) {
        // --
    }

    override fun addActionCallback(profileField: ProfileField.AdaptionValues, viewable: ProfileViewable) {
        // --
    }

    private fun handleSelectableOrText(
        viewable: ProfileViewable,
        externalSelection: Boolean,
        values: ProfileSelectableValues?,
        event: PickerEvent,
        title: String,
        getter: () -> String?,
        setter: (String?) -> Unit
    ) {
        values?.let { selectableValues ->
            if (externalSelection) {
                collector.applyCallback(viewable) {
                    val map = HashMap<String, String>()
                    showPickerEvent.sendEvent(
                        ProfilePickerOptions(
                            title,
                            getter(),
                            selectableValues.selectableValues.map { it.key to it.description }.toMap(map),
                            event
                        )
                    )
                }
            } else {
                collector.applyCallback(viewable) { setter.invoke(it) }
            }
        } ?: run {
            collector.applyCallback(viewable) { setter.invoke(it) }
        }
    }

    protected fun handleInput(): Boolean {
        val error = collector.findFirstError(getApplication())
        return error?.let {
            inputErrorEvent.sendEvent(it)
            false
        } ?: true
    }

    override fun onProfileFieldsLoaded(fieldsData: ProfileFieldsData) {
        super.onProfileFieldsLoaded(fieldsData)
        collector.clear()
    }

    override fun onProfileFieldsLoadingFailed(error: ResponseError<out RequestError>?) {
        super.onProfileFieldsLoadingFailed(error)
        collector.clear()
        inputErrorEvent.sendEvent(defaultErrorMessage(error))
    }

    fun loadFields() {
        loadProfileFields(userCreator.user())
    }

    @CallSuper
    internal open fun birthdaySet(birthday: Long) {
        val userBirthday = Date(birthday).toUserBirthdayString()
        userCreator.pendingUser.birthday = userBirthday
        collector.collectManual<ProfileField.Birthday>(userBirthday)
    }

    @CallSuper
    internal open fun birthdayCancel() {
        collector.collectManual<ProfileField.Birthday>(userCreator.pendingUser.birthday)
    }

    @CallSuper
    internal open fun languageCodeSet(languageCode: String) {
        userCreator.pendingUser.languageCode = languageCode
        collector.collectManual<ProfileField.LanguageCode>(languageCode)
    }

    @CallSuper
    internal open fun addressCountryCodeSet(countryCode: String) {
        userCreator.pendingUser.countryCode = countryCode
        userCreator.pendingUser.address.countryCode = countryCode
        collector.collectManual<ProfileField.AddressCountryCode>(countryCode)
    }

    @CallSuper
    internal open fun addressStateSet(key: String, value: String) {
        val state = getProfileField<ProfileField.AddressState>()?.values?.getKeyOrValue(key, value)
            ?: key
        userCreator.pendingUser.address.state = state
        collector.collectManual<ProfileField.AddressState>(state)
    }

    @CallSuper
    internal open fun addressProvinceSet(key: String, value: String) {
        val province = getProfileField<ProfileField.AddressProvince>()?.values?.getKeyOrValue(key, value)
            ?: key
        userCreator.pendingUser.address.province = province
        collector.collectManual<ProfileField.AddressProvince>(province)
    }

    @CallSuper
    internal open fun addressCitySet(key: String, value: String) {
        val city = getProfileField<ProfileField.AddressCity>()?.values?.getKeyOrValue(key, value)
            ?: key
        userCreator.pendingUser.address.city = city
        collector.collectManual<ProfileField.AddressCity>(city)
    }

    private fun ProfileSelectableValues.getKeyOrValue(key: String, value: String): String =
        if (matchSelectableValueByKey) key else value

    private fun Date.toUserBirthdayString() =
        SimpleDateFormat(UserValuePolicy.BIRTHDAY_DATE_PATTERN, Locale.getDefault()).format(this)

    private fun String.toUserBirthday() =
        try {
            SimpleDateFormat(UserValuePolicy.BIRTHDAY_DATE_PATTERN, Locale.getDefault()).parse(this)
        } catch (e: Exception) {
            Calendar.getInstance().apply {
                add(Calendar.YEAR, -BIRTHDAY_DATE_OFFSET_YEARS)
            }.time
        }

    private companion object {
        private const val BIRTHDAY_DATE_OFFSET_YEARS = 30
    }
}