package com.daimler.mbmobilesdk.serviceactivation.precondition

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.R
import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbuikit.utils.extensions.getString

internal class MultipleServicesPreconditionViewModel(
    app: Application,
    finOrVin: String,
    private val services: List<Service>
) : BaseServicePreconditionViewModel(app, finOrVin) {

    override val title: String = getString(R.string.activate_services_preconditions)

    override fun serviceForId(id: Int): Service? =
        services.firstOrNull { it.id == id }

    override fun collectPreconditions(result: MutableLiveData<List<ServicePreconditionType>>) {
        result.postValue(MultipleServicesPreparation(services).preconditions())
    }
}