package com.daimler.mbmobilesdk.serviceactivation

import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbcarkit.business.model.services.Service

internal class ServiceCategoryItem(val title: String) : BaseServiceItem() {

    override val type = TYPE_CATEGORY
    override val serviceId: Int = -1

    override fun getLayoutRes(): Int = R.layout.item_service_category

    override fun getModelId(): Int = BR.model

    override fun destroy() = Unit

    override fun updateService(
        service: Service,
        isActive: Boolean,
        isChangeable: Boolean,
        hint: String?,
        isSelectable: Boolean
    ) = Unit
}