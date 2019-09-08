package com.daimler.mbmobilesdk.countryselection

import com.daimler.mbuikit.eventbus.Event

data class CountrySelectionEvent(
    val index: Int,
    val country: String,
    val countryCode: String,
    val isSelected: Boolean
) : Event