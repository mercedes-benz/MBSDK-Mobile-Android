package com.daimler.mbmobilesdk.familyapps

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbmobilesdk.utils.extensions.re
import com.daimler.mbdeeplinkkit.MBDeepLinkKit
import com.daimler.mbdeeplinkkit.common.FamilyApp
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbuikit.components.recyclerview.MutableLiveArrayList
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.utils.extensions.mutableLiveDataOf

class FamilyAppsViewModel(app: Application) : AndroidViewModel(app), AppChooserItem.Events {

    val items = MutableLiveArrayList<AppChooserItem>()
    val progressVisible = mutableLiveDataOf(false)

    val onAppClickedEvent = MutableLiveEvent<FamilyApp>()
    val onAppsErrorEvent = MutableLiveEvent<String>()

    init {
        loadApps()
    }

    override fun onAppClicked(app: FamilyApp) {
        onAppClickedEvent.sendEvent(app)
    }

    private fun mapApps(familyApps: List<FamilyApp>) {
        items.value.clear()
        items.addAllAndDispatch(familyApps.map { AppChooserItem(it, this) })
    }

    private fun loadApps() {
        progressVisible.value = true

        MBIngressKit.refreshTokenIfRequired()
            .onComplete { token ->
                val jwt = token.jwtToken.plainToken
                FamilyAppsService(MBDeepLinkKit.appService()).loadApps(jwt)
                    .onComplete {
                        mapApps(it)
                    }.onFailure {
                        MBLoggerKit.re("Failed to fetch apps.", it)
                        onAppsErrorEvent.sendEvent(defaultErrorMessage(it))
                    }.onAlways { _, _, _ -> progressVisible.postValue(false) }
            }.onFailure {
                onAppsErrorEvent.sendEvent(defaultErrorMessage(it))
                progressVisible.postValue(false)
            }
    }
}