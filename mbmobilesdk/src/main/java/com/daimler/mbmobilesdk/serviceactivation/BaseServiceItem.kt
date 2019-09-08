package com.daimler.mbmobilesdk.serviceactivation

import androidx.annotation.IntDef
import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerItem

internal abstract class BaseServiceItem : MBBaseRecyclerItem() {

    @ServiceItemType
    abstract val type: Int
    abstract val serviceId: Int

    abstract fun destroy()

    abstract fun updateService(
        service: Service,
        isActive: Boolean,
        isChangeable: Boolean,
        hint: String?,
        isSelectable: Boolean
    )

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(TYPE_DEFAULT, TYPE_CATEGORY, TYPE_PURCHASE)
    annotation class ServiceItemType

    companion object {
        const val TYPE_DEFAULT = 1
        const val TYPE_CATEGORY = 2
        const val TYPE_PURCHASE = 3
    }
}