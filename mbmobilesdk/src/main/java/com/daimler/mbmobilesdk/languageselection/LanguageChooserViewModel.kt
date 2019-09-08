package com.daimler.mbmobilesdk.languageselection

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.LineDividerDecorator
import com.daimler.mbmobilesdk.utils.bindings.matches
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbmobilesdk.utils.extensions.format
import com.daimler.mbmobilesdk.utils.extensions.formatLanguage
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbmobilesdk.utils.post
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList
import com.daimler.mbuikit.eventbus.EventBus
import com.daimler.mbuikit.eventbus.Observes
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.getColor
import java.util.*
import java.util.regex.Pattern

class LanguageChooserViewModel(
    app: Application,
    private val currentLanguageCode: String?
) : AndroidViewModel(app) {

    private class Language(val code: String, val displayValue: String)

    internal val languageSelectedEvent = MutableLiveEvent<String>()
    internal val languageSelectionCancelledEvent = MutableLiveUnitEvent()

    internal val languageLoadingError = MutableLiveEvent<String>()

    val items = MutableLiveArrayList<LanguageSelectionItem>()
    val progressVisible = MutableLiveData<Boolean>()
    val selected = MutableLiveData<Boolean>()
    val scrollPosition = MutableLiveData<Int>()

    private var selectedIndex = NO_SELECTION
    private var selectedLanguageCode: String? = null

    init {
        EventBus.createStation(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.dismount(this)
    }

    fun getDecorator(): RecyclerView.ItemDecoration = LineDividerDecorator(getColor(R.color.mb_white))

    fun onReadyClicked() {
        val code = selectedLanguageCode
        if (code != null) {
            languageSelectedEvent.sendEvent(code)
        }
    }

    fun onCloseClicked() {
        selectedIndex = NO_SELECTION
        selectedLanguageCode = null
        languageSelectionCancelledEvent.sendEvent()
    }

    fun loadLanguages() {
        progressVisible.postValue(true)
        items.value.clear()
        fetchLanguages()
            .onComplete { languages ->
                MBLoggerKit.d("Current language code = $currentLanguageCode")
                items.addAllAndDispatch(
                    languages
                        .sortedBy { it.displayValue }
                        .mapIndexed { index, language ->
                            LanguageSelectionItem(index, language.displayValue, language.code)
                        }
                )
                findInitialSelection()
            }.onFailure {
                MBLoggerKit.re("Failed to load languages.", it)
                languageLoadingError.sendEvent(defaultErrorMessage(it))
            }.onAlways { _, _, _ -> progressVisible.postValue(false) }
    }

    private fun fetchLanguages(): FutureTask<List<Language>, ResponseError<out RequestError>?> {
        val task = TaskObject<List<Language>, ResponseError<out RequestError>?>()
        val codeFormatPattern = Pattern.compile("[a-z]{2}-[A-Z]{2}")
        val languages = Locale.getAvailableLocales()
            .filter { it.country.isNotEmpty() }
            .map {
                Language(it.format(), it.formatLanguage())
            }
            .filter { codeFormatPattern.matches(it.code) }

        post({
            task.complete(languages)
        })
        return task
    }

    private fun findInitialSelection() {
        if (!currentLanguageCode.isNullOrBlank()) {
            items.value.firstOrNull { item ->
                item.languageCode == currentLanguageCode
            }?.let { item ->
                item.selectAndNotify()
                scrollPosition.postValue(item.index)
            }
        }
    }

    @Observes
    @Suppress("UNUSED")
    private fun onSelectionEvent(event: LanguageSelectionEvent) {
        if (event.isSelected) {
            if (selectedIndex != NO_SELECTION) items.value[selectedIndex].deselect()
            selectedIndex = event.index
            selectedLanguageCode = event.languageCode
        } else {
            selectedIndex = NO_SELECTION
            selectedLanguageCode = null
        }
        selected.value = event.isSelected
    }

    private companion object {
        private const val NO_SELECTION = -1
    }
}