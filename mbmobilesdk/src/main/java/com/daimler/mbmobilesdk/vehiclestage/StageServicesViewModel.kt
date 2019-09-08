package com.daimler.mbmobilesdk.vehiclestage

import android.app.Application
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.utils.extensions.getString

internal class StageServicesViewModel(app: Application) : StageDescriptionViewModel(app) {

    override fun getHeadline(): String =
        getString(R.string.activate_services_request_title)

    override fun getDescription(): String =
        getString(R.string.activate_services_request_explanation)

    override fun getActionButtonText(): String =
        getString(R.string.activate_services_activation_button_title)

    override fun getCancelButtonText(): String =
        getString(R.string.activate_services_activate_later_button_title)
}