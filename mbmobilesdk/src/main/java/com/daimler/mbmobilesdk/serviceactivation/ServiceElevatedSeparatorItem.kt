package com.daimler.mbmobilesdk.serviceactivation

import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R

internal class ServiceElevatedSeparatorItem : BaseServiceItem(false) {

    override val type = TYPE_CATEGORY
    override val serviceId: Int = -1

    override fun getLayoutRes(): Int = R.layout.item_elevated_sections_separator

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