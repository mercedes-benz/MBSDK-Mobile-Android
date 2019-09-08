package com.daimler.mbmobilesdk.languageselection

import com.daimler.mbuikit.eventbus.Event

data class LanguageSelectionEvent(
    val index: Int,
    val languageCode: String,
    val isSelected: Boolean
) : Event