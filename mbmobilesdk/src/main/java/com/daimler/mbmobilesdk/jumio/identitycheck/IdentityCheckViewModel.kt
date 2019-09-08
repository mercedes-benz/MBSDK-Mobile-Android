package com.daimler.mbmobilesdk.jumio.identitycheck

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.jumio.JumioConfig
import com.daimler.mbmobilesdk.jumio.JumioSelectedConfig
import com.daimler.mbmobilesdk.jumio.LocaleHelper
import com.daimler.mbmobilesdk.jumio.Variants
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList
import com.daimler.mbuikit.components.viewmodels.MBBaseToolbarViewModel
import java.util.*
import kotlin.collections.ArrayList

class IdentityCheckViewModel(app: Application) : MBBaseToolbarViewModel(app), DocumentItem.ItemEvents {
    val countries = MutableLiveArrayList<String>()
    val documentTypes = MutableLiveArrayList<DocumentItem>()
    val defaultIndex = MutableLiveData(0)
    val loading = MutableLiveData(true)

    val documentSelectedCommand = MutableLiveData<JumioSelectedConfig>()

    private var selectedCountryCode: String? = null

    private var config: JumioConfig? = null

    private fun setupDocumentList(context: Context, selectedCountry: String?) {
        if (selectedCountryCode == selectedCountry) return
        selectedCountry?.let { countryCode ->
            selectedCountryCode = countryCode
            documentTypes.value.clear()
            val items = ArrayList<DocumentItem>()
            config?.map?.get(countryCode)?.let { typeVariantsMap ->
                typeVariantsMap.forEach { entry ->
                    if (entry.value.size > 1) {
                        items.addAll(entry.value.map { variants -> DocumentItem(context, entry.key, variants, this) })
                    } else {
                        items.add(DocumentItem(context, entry.key, null, this))
                    }
                }
            }
            documentTypes.addAllAndDispatch(items)
        }
    }

    fun onSelectItem(context: Context, pos: Int) {
        setupDocumentList(context, config?.let { it.map.keys.toTypedArray()[pos] })
    }

    override fun onDocumentItemClicked(item: DocumentItem) {
        selectedCountryCode?.let {
            documentSelectedCommand.postValue(
                JumioSelectedConfig(it, item.type, item.variant
                    ?: Variants.PLASTIC)
            )
        }
    }

    fun setConfig(conf: JumioConfig) {
        config = conf
        var index = 0
        countries.value.clear()
        val items = ArrayList<String>()
        val country = selectedCountryCode ?: Locale.getDefault().isO3Country
        conf.map.keys.toTypedArray().forEachIndexed { i, countryCode ->
            if (countryCode == country) {
                index = i
            }

            LocaleHelper.getLocalizedCountryNameFromISO3(countryCode)?.let {
                items.add("$it ($countryCode)")
            } ?: items.add(countryCode)
        }
        countries.addAllAndDispatch(items)
        defaultIndex.postValue(index)
        loading.postValue(false)
    }
}