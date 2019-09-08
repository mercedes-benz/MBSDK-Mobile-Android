package com.daimler.mbmobilesdk.vehicleassignment

import com.daimler.mbcarkit.business.model.merchants.Address
import com.daimler.mbcarkit.business.model.merchants.MerchantResponse

interface MerchantSearchListener {

    fun onPhoneSelected(phoneNumber: String?)

    fun onRouteSelected(address: Address?)

    fun onShowDealerActionDialog(merchant: MerchantResponse)
}