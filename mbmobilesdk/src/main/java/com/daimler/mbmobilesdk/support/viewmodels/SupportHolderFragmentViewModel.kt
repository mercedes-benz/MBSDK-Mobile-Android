package com.daimler.mbmobilesdk.support.viewmodels

import androidx.lifecycle.ViewModel
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent

class SupportHolderFragmentViewModel : ViewModel() {

    val clickEvent = MutableLiveEvent<Int>()

    fun onCancelClicked() = clickEvent.sendEvent(CANCEL)

    fun onMmeLogoClicked() = clickEvent.sendEvent(IGNORE)

    fun onLauncherIconClicked() = clickEvent.sendEvent(IGNORE)

    companion object {
        const val WIDGETS = 0
        const val MENU = 1
        const val RECYCLER = 2
        const val VIEWS = 3
        const val DIALOGS = 4
        const val CANCEL = 5
        const val IGNORE = 6
    }
}
