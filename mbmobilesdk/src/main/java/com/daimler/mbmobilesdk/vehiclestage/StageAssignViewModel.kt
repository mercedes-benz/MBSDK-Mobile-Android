package com.daimler.mbmobilesdk.vehiclestage

import android.app.Application
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.lifecycle.events.MutableLiveEvent
import com.daimler.mbuikit.utils.extensions.getString

internal class StageAssignViewModel(app: Application) : StageDescriptionViewModel(app) {

    val errorEvent = MutableLiveEvent<String>()

    override fun getHeadline(): String = getString(R.string.assign_title)

    override fun getDescription(): String = getString(R.string.assign_text)

    override fun getActionButtonText(): String = getString(R.string.assign_start)

    override fun getCancelButtonText(): String = getString(R.string.assign_later)
}