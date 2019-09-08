package com.daimler.mbmobilesdk.legal.license

import android.app.Application
import com.daimler.mbmobilesdk.legal.json.LicenseParser
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList
import com.daimler.mbuikit.components.viewmodels.MBBaseToolbarViewModel
import com.daimler.mbuikit.eventbus.EventBus
import com.daimler.mbuikit.eventbus.Observes
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent

class LicensesViewModel(app: Application) : MBBaseToolbarViewModel(app) {

    val items = MutableLiveArrayList<LicenseItem>()
    val licenseSelectedEvent = MutableLiveEvent<LicenseDetail>()

    init {
        loadLicenses()
        EventBus.createStation(this)
    }

    override fun onCleared() {
        super.onCleared()
        EventBus.dismount(this)
    }

    @Observes
    @Suppress("UNUSED")
    private fun onLicenseSelected(event: LicenseSelectedEvent) {
        licenseSelectedEvent.sendEvent(
            LicenseDetail(event.lib.name, event.lib.file)
        )
    }

    private fun loadLicenses() {
        val libs = LicenseParser().parseLibraries(getApplication())
        showLibraries(libs)
    }

    private fun showLibraries(libraries: List<Library>) {
        val tmp = mutableListOf<LicenseItem>()
        libraries.forEach { lib ->
            tmp.add(LicenseItem(lib))
        }
        items.value.clear()
        items.addAllAndDispatch(tmp)
    }
}