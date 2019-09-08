package com.daimler.mbmobilesdk.vehicleassignment

import android.app.Application
import android.os.Handler
import android.text.Editable
import androidx.lifecycle.AndroidViewModel
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.merchants.Address
import com.daimler.mbcarkit.business.model.merchants.MerchantResponse
import com.daimler.mbcarkit.business.model.merchants.Merchants
import com.daimler.mbcarkit.business.model.merchants.OpeningHours
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent
import com.daimler.mbuikit.utils.extensions.getString
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf
import java.util.*

class SearchMerchantViewModel(app: Application) : AndroidViewModel(app), MerchantSearchListener {

    val isSelected = mutableLiveDataOf(false)
    val showProgress = mutableLiveDataOf(false)
    val resultsVisible = mutableLiveDataOf(false)
    val noResultsHintVisible = mutableLiveDataOf(false)

    val items = MutableLiveArrayList<MerchantChooserItem>()

    val onBackClickedEvent = MutableLiveUnitEvent()
    val cancelClickedEvent = MutableLiveUnitEvent()
    val closeClickedEvent = MutableLiveUnitEvent()
    val onPhoneClickedEvent = MutableLiveEvent<String>()
    val onDirectionsClickedEvent = MutableLiveEvent<Address>()
    val onDealerActionDialogShowEvent = MutableLiveEvent<MerchantResponse>()
    val errorEvent = MutableLiveEvent<String>()

    private val handler = Handler()

    override fun onCleared() {
        super.onCleared()
        handler.removeCallbacksAndMessages(null)
    }

    fun onCloseClicked() {
        closeClickedEvent.sendEvent()
    }

    fun onCancelClicked() {
        showEmptyState()
        cancelClickedEvent.sendEvent()
    }

    fun onBackClicked() {
        onBackClickedEvent.sendEvent()
    }

    fun onMerchantTextChanged(editable: Editable) {
        handler.removeCallbacksAndMessages(null)
        val text = editable.toString()
        if (text.length >= MIN_ZIP_LENGTH) {
            isSelected.postValue(true)
            handler.postDelayed({ fetchDealers(text) }, SEARCH_DELAY_MILLIS)
        }
    }

    override fun onPhoneSelected(phoneNumber: String?) {
        phoneNumber?.let { onPhoneClickedEvent.sendEvent(it) }
    }

    override fun onRouteSelected(address: Address?) {
        address?.let { onDirectionsClickedEvent.sendEvent(it) }
    }

    override fun onShowDealerActionDialog(merchant: MerchantResponse) {
        onDealerActionDialogShowEvent.sendEvent(merchant)
    }

    private fun fetchDealers(input: String?) {
        showProgress.postValue(true)
        MBCarKit.merchantService().fetchOutlets(input?.trim(), null, null)
            .onComplete {
                val items = mapMerchants(it)
                if (items.isNotEmpty()) {
                    showResults(items)
                } else {
                    showNoResultsHint()
                }
            }
            .onFailure {
                MBLoggerKit.re("Failed to fetch outlets.", it)
                if (items.value.isEmpty()) {
                    errorEvent.sendEvent(defaultErrorMessage(it))
                }
            }.onAlways { _, _, _ -> showProgress.postValue(false) }
    }

    private fun mapMerchants(merchants: Merchants): List<MerchantChooserItem> {
        return merchants.merchants.map {
            MerchantChooserItem(it, parseWorkingHours(it.openingHours), this)
        }
    }

    private fun showEmptyState() {
        isSelected.postValue(false)
        items.clearAndDispatch()
        resultsVisible.postValue(false)
        noResultsHintVisible.postValue(false)
    }

    private fun showResults(items: List<MerchantChooserItem>) {
        noResultsHintVisible.postValue(false)
        resultsVisible.postValue(true)
        this.items.apply {
            value.clear()
            value.addAll(items)
            dispatchChange()
        }
    }

    private fun showNoResultsHint() {
        this.items.clearAndDispatch()
        noResultsHintVisible.postValue(true)
        resultsVisible.postValue(false)
    }

    private fun parseWorkingHours(openingHours: OpeningHours?): String {
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)

        val open = getString(R.string.dealer_search_open)
        val closed = getString(R.string.dealer_search_closed)
        val openUntil = getString(R.string.dealer_search_open_extend)

        var resultString = ""
        val day = when (dayOfWeek) {
            Calendar.MONDAY -> openingHours?.monday
            Calendar.TUESDAY -> openingHours?.tuesday
            Calendar.WEDNESDAY -> openingHours?.wednesday
            Calendar.THURSDAY -> openingHours?.thursday
            Calendar.FRIDAY -> openingHours?.friday
            Calendar.SATURDAY -> openingHours?.saturday
            Calendar.SUNDAY -> openingHours?.sunday
            else -> null
        }
        if (day != null) {
            if (day.status == "OPEN") {
                resultString += "$open "
                day.periods?.let { periods ->
                    if (periods.isNotEmpty()) {
                        periods.last().until?.let {
                            val closingHours = it.substringBefore(":")
                            resultString += String.format(openUntil, closingHours)
                        }
                    }
                }
            } else {
                resultString = closed
            }
        }
        return resultString
    }

    private companion object {
        const val MIN_ZIP_LENGTH = 3
        const val SEARCH_DELAY_MILLIS = 750L
    }
}