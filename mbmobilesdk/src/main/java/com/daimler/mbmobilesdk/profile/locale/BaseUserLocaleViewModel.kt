package com.daimler.mbmobilesdk.profile.locale

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.app.UserLocale
import com.daimler.mbmobilesdk.featuretoggling.FLAG_NATCON_TERMS_OF_USE
import com.daimler.mbmobilesdk.registration.locale.JsonLocaleService
import com.daimler.mbmobilesdk.registration.locale.LocaleModel
import com.daimler.mbmobilesdk.registration.locale.LocaleService
import com.daimler.mbmobilesdk.registration.locale.LocaleType
import com.daimler.mbmobilesdk.tou.natcon.isNatconCountry
import com.daimler.mbmobilesdk.utils.ifNotNull
import com.daimler.mbmobilesdk.views.ModelNumberPicker
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList
import com.daimler.mbuikit.utils.extensions.invert
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf
import java.util.*

internal abstract class BaseUserLocaleViewModel(
    app: Application,
    countryCode: String?,
    languageCode: String?,
    val legalVisible: Boolean = false
) : AndroidViewModel(app) {

    val countriesExpanded = mutableLiveDataOf(false)
    val languagesExpanded = mutableLiveDataOf(false)

    val selectedCountry = MutableLiveData<String>()
    val selectedLanguage = MutableLiveData<String>()

    val countries = MutableLiveArrayList<LocaleModel>()
    val languages = MutableLiveArrayList<LocaleModel>()

    private val localeService: LocaleService = JsonLocaleService(app)

    private var currentCountryCode: String? = countryCode
    private var currentLanguageCode: String? = languageCode

    init {
        loadCountries()
    }

    fun onCountryClicked() {
        countriesExpanded.invert()
        languagesExpanded.invertIfTrue()
    }

    fun onLanguageClicked() {
        languagesExpanded.invert()
        countriesExpanded.invertIfTrue()
    }

    fun onNextClicked() {
        ifNotNull(currentCountryCode, currentLanguageCode) { country, language ->
            val userLocale = UserLocale(country, language)
            onUserLocaleSelected(userLocale)
        }
    }

    fun onLegalClicked() {
        onShowLegal()
    }

    fun onLocaleChanged(newVal: ModelNumberPicker.Pickable) {
        val locale = newVal as LocaleModel
        if (locale.type == LocaleType.COUNTRY) {
            currentCountryCode = locale.code
            selectedCountry.postValue(locale.displayValue())
            loadLanguages(locale.code)
        } else {
            currentLanguageCode = locale.code
            selectedLanguage.postValue(locale.displayValue())
        }
    }

    protected abstract fun onUserLocaleSelected(userLocale: UserLocale)

    protected abstract fun onShowLegal()

    protected fun shouldShowNatcon(countryCode: String): Boolean {
        return MBMobileSDK.featureToggleService().isToggleEnabled(FLAG_NATCON_TERMS_OF_USE) &&
            isNatconCountry(countryCode)
    }

    private fun loadCountries() {
        val countries = localeService.loadCountries()
        this.countries.apply {
            value.clear()
            value.addAll(countries)
            dispatchChange()
        }
        val current = findInitialCountry(countries)

        current?.let {
            currentCountryCode = it.code
            selectedCountry.postValue(it.displayValue())
            loadLanguages(it.code)
        }
    }

    private fun loadLanguages(countryCode: String) {
        val languages = localeService.loadLanguagesForCountry(countryCode)

        this.languages.apply {
            value.clear()
            value.addAll(languages)
            dispatchChange()
        }

        val current = findInitialLanguage(languages)

        current?.let {
            currentLanguageCode = it.code
            selectedLanguage.postValue(it.displayValue())
        }
    }

    private fun findInitialCountry(countries: List<LocaleModel>): LocaleModel? {
        val country = currentCountryCode ?: Locale.getDefault().country.toUpperCase()
        return countries.firstOrNull { it.code.toUpperCase() == country } ?: countries.firstOrNull()
    }

    private fun findInitialLanguage(languages: List<LocaleModel>): LocaleModel? {
        return currentLanguageCode?.let { current ->
            languages.firstOrNull { it.code == current }
        } ?: {
            val deviceLanguage = Locale.getDefault().language
            val language = languages.firstOrNull { it.code == deviceLanguage }
            language ?: languages.firstOrNull()
        }()
    }

    private fun MutableLiveData<Boolean>.invertIfTrue() {
        if (value == true) postValue(false)
    }
}