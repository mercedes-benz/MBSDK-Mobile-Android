package com.daimler.mbmobilesdk.vehicleassignment

import android.content.Context
import androidx.lifecycle.ViewModel
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbuikit.components.fragments.MBBaseViewModelFragment

internal abstract class BaseAssignVehicleFragment<T : ViewModel> : MBBaseViewModelFragment<T>() {

    private var callback: AssignVehicleActionsCallback? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as? AssignVehicleActionsCallback
        if (callback == null) {
            MBLoggerKit.w("No callback attached because host does not implement AssignVehicleActionsCallback.")
        }
    }

    override fun onDetach() {
        super.onDetach()
        callback = null
    }

    protected fun notifyShowQrCode() {
        callback?.onShowQrCode()
    }

    protected fun notifyAssignWithVin(vin: String?) {
        callback?.onAssignWithVin(vin)
    }

    protected fun notifyShowHelp() {
        callback?.onShowHelp()
    }

    protected fun notifyInfoCall() {
        callback?.onInfoCall()
    }

    protected fun notifySearchRetailer() {
        callback?.onSearchRetailer()
    }

    protected fun notifyCancel() {
        callback?.onCancel()
    }
}