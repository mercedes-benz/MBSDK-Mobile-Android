package com.daimler.mbmobilesdk.notificationcenter.presentation

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.notificationcenter.model.Message
import com.daimler.mbuikit.components.viewmodels.MBBaseToolbarViewModel

class ReachMeViewModel(app: Application) : MBBaseToolbarViewModel(app) {

    private val screen: MutableLiveData<ReachMeScreen> = MutableLiveData()

    init {
        screen.postValue(ReachMeScreen.Messages(false))
    }

    fun showMessages() {
        val fromBackNavigation = screen.value is ReachMeScreen.MessageDetails
        screen.postValue(ReachMeScreen.Messages(fromBackNavigation))
    }

    fun showMessage(message: Message) {
        screen.postValue(ReachMeScreen.MessageDetails(message))
    }

    fun screen(): LiveData<ReachMeScreen> = screen
}