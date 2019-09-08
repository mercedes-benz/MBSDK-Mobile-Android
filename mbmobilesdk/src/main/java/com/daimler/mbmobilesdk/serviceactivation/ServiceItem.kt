package com.daimler.mbmobilesdk.serviceactivation

import android.os.Handler
import android.os.Looper
import androidx.annotation.DrawableRes
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbcarkit.business.model.services.Service
import com.daimler.mbuikit.eventbus.EventBus
import com.daimler.mbuikit.utils.extensions.invert

internal class ServiceItem(
    var service: Service,
    @DrawableRes val iconRes: Int,
    isActive: Boolean,
    changeable: Boolean,
    serviceHint: String?,
    selectable: Boolean
) : BaseServiceItem() {

    override val type = TYPE_DEFAULT
    override val serviceId: Int get() = service.id

    val name = service.name
    val hint = ObservableField<String>(serviceHint)
    val isChangeable = ObservableBoolean(changeable)
    val isSelectable = ObservableBoolean(selectable)
    val isChecked = ObservableBoolean(isActive)
    val progressVisible = ObservableBoolean()
    private var handler: Handler? = Handler(Looper.getMainLooper())

    private var preventNotifying = false

    override fun getLayoutRes(): Int = R.layout.item_service

    override fun getModelId(): Int = BR.model

    fun setProgress(isProcessing: Boolean) = progressVisible.set(isProcessing)

    fun toggle() {
        // We prevent notifications here.
        preventNotifying = true
        isChecked.invert()
        preventNotifying = false
    }

    fun onSelectionToggled(isChecked: Boolean) {
        if (isChecked == this.isChecked.get()) return
        this.isChecked.set(isChecked)
        if (!preventNotifying) {
            // Switches call their listeners before updating their UI, so we post
            // the event to prevent a delay of the switches' state change.
            handler?.post { EventBus.fuel(ServiceToggledEvent(this, isChecked)) }
        }
        preventNotifying = false
    }

    fun onItemClicked() = EventBus.fuel(ServiceSelectedEvent(service))

    override fun destroy() {
        handler?.removeCallbacksAndMessages(null)
        handler = null
    }

    override fun updateService(
        service: Service,
        isActive: Boolean,
        isChangeable: Boolean,
        hint: String?,
        isSelectable: Boolean
    ) {
        this.service = service
        if (isChecked.get() != isActive) toggle()
        this.isChangeable.set(isChangeable)
        this.hint.set(hint)
        this.isSelectable.set(isSelectable)
    }
}