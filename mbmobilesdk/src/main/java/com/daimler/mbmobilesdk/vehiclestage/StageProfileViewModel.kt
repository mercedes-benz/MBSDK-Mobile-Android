package com.daimler.mbmobilesdk.vehiclestage

import android.app.Application
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.logic.UserTask
import com.daimler.mbmobilesdk.utils.extensions.defaultErrorMessage
import com.daimler.mbingresskit.common.User
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.utils.extensions.getString

internal class StageProfileViewModel(app: Application) : StageDescriptionViewModel(app) {

    val userLoadedEvent = MutableLiveEvent<User>()
    val errorEvent = MutableLiveEvent<String>()

    override fun getHeadline(): String = getString(R.string.activate_services_user_title)

    override fun getDescription(): String = getString(R.string.activate_services_user_description)

    override fun getActionButtonText(): String = getString(R.string.activate_services_user_start_button)

    override fun getCancelButtonText(): String = getString(R.string.activate_services_user_later_button)

    fun loadUser() {
        if (progressVisible.value == true) return
        progressVisible.postValue(true)
        UserTask().fetchUser()
            .onComplete { userLoadedEvent.sendEvent(it.user) }
            .onFailure { errorEvent.sendEvent(defaultErrorMessage(it)) }
            .onAlways { _, _, _ -> progressVisible.postValue(false) }
    }
}