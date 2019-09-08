package com.daimler.mbmobilesdk.countryselection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.LineDividerDecorator
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList
import com.daimler.mbuikit.eventbus.EventBus
import com.daimler.mbuikit.eventbus.Observes
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.getColor

class LocaleChooserViewModel(
    app: Application,
    private val currentCountryCode: String?
) : AndroidViewModel(app) {

    internal val countrySelectedEvent = MutableLiveEvent<Pair<String, String>>()
    internal val countrySelectionCancelledEvent = MutableLiveUnitEvent()

    internal val countryLoadingError = MutableLiveEvent<String>()

    val items = MutableLiveArrayList<CountrySelectionItem>()
    val progressVisible = MutableLiveData<Boolean>()
    val selected = MutableLiveData<Boolean>()
    val scrollPosition = MutableLiveData<Int>()

    private var selectedIndex = NO_SELECTION
    private var selectedCountry: String? = null
    private var selectedCountryCode: String? = null

    init {
        EventBus.createStation(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.dismount(this)
    }

    fun getDecorator(): RecyclerView.ItemDecoration = LineDividerDecorator(getColor(R.color.mb_white))

    fun onReadyClicked() {
        val country = selectedCountry
        val code = selectedCountryCode
        if (country != null && code != null) {
            countrySelectedEvent.sendEvent(Pair(country, code))
        }
    }

    fun onCloseClicked() {
        selectedIndex = NO_SELECTION
        selectedCountry = null
        selectedCountryCode = null
        countrySelectionCancelledEvent.sendEvent()
    }

    fun loadCountries() {
        progressVisible.postValue(true)
        items.value.clear()
        MBIngressKit.userService().fetchCountries()
            .onComplete { countries ->
                MBLoggerKit.d("Current country code = $currentCountryCode")
                items.addAllAndDispatch(
                    countries.countries
                        .sortedBy { it.countryName }
                        .mapIndexed { index, country ->
                            CountrySelectionItem(index, country.countryName, country.countryCode)
                        }
                )
                findInitialSelection()
            }.onFailure {
                MBLoggerKit.re("Failed to load countries.", it)
                countryLoadingError.sendEvent(defaultErrorMessage(it))
            }.onAlways { _, _, _ -> progressVisible.postValue(false) }
    }

    private fun findInitialSelection() {
        if (!currentCountryCode.isNullOrBlank()) {
            items.value.firstOrNull { item ->
                item.countryCode == currentCountryCode
            }?.let { item ->
                item.selectAndNotify()
                scrollPosition.postValue(item.index)
            }
        }
    }

    @Observes
    @Suppress("UNUSED")
    private fun onSelectionEvent(event: CountrySelectionEvent) {
        if (event.isSelected) {
            if (selectedIndex != NO_SELECTION) items.value[selectedIndex].deselect()
            selectedIndex = event.index
            selectedCountry = event.country
            selectedCountryCode = event.countryCode
        } else {
            selectedIndex = NO_SELECTION
            selectedCountry = null
            selectedCountryCode = null
        }
        selected.value = event.isSelected
    }

    private companion object {
        private const val NO_SELECTION = -1
    }
}