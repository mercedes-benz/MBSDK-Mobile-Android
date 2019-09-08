package com.daimler.mbmobilesdk.serviceactivation.precondition

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.daimler.mbcarkit.business.model.services.Service

internal class SingleServicePreconditionViewModel(
    app: Application,
    finOrVin: String,
    private val service: Service
) : BaseServicePreconditionViewModel(app, finOrVin) {

    override val title: String = service.name

    override fun serviceForId(id: Int): Service? = if (id == service.id) service else null

    override fun collectPreconditions(result: MutableLiveData<List<ServicePreconditionType>>) {
        result.postValue(SingleServicePreparation(service).preconditions())
    }
}