package com.daimler.mbmobilesdk.vehicleassignment

import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbcarkit.business.model.merchants.MerchantResponse
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerItem

class MerchantChooserItem(
    private val merchant: MerchantResponse,
    val openingHours: String,
    private val listener: MerchantSearchListener
) : MBBaseRecyclerItem() {

    var name: String? = merchant.legalName
    var address: String? = merchant.address?.city + ", " + merchant.address?.street
    var phone: String? = merchant.communication?.phone

    override fun getLayoutRes(): Int = R.layout.item_choose_merchant

    override fun getModelId(): Int = BR.item

    fun onPhoneClick() {
        listener.onPhoneSelected(phone)
    }

    fun onDirectionsClick() {
        listener.onRouteSelected(merchant.address)
    }

    fun onShowDealerActionDialog() {
        listener.onShowDealerActionDialog(merchant)
    }
}