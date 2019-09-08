package com.daimler.mbmobilesdk.serviceactivation

import androidx.annotation.DrawableRes
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbuikit.eventbus.EventBus

internal class ServicePurchaseItem(
    var service: Service,
    @DrawableRes val iconRes: Int,
    serviceHint: String?,
    selectable: Boolean
) : BaseServiceItem() {

    override val type = TYPE_PURCHASE
    override val serviceId: Int get() = service.id

    val name = service.name
    val hint = ObservableField<String>(serviceHint)
    val isSelectable = ObservableBoolean(selectable)

    override fun getLayoutRes(): Int = R.layout.item_service_purchase

    override fun getModelId(): Int = BR.model

    override fun destroy() = Unit

    override fun updateService(
        service: Service,
        isActive: Boolean,
        isChangeable: Boolean,
        hint: String?,
        isSelectable: Boolean
    ) {
        this.service = service
        this.hint.set(hint)
        this.isSelectable.set(isSelectable)
    }

    fun onItemClicked() = EventBus.fuel(ServiceSelectedEvent(service))

    fun onPurchaseClicked() = EventBus.fuel(PurchaseServiceEvent(service))
}