package com.daimler.mbmobilesdk.tou.soe

import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R

internal class SoeTitleItem(val title: String) : SoeRecyclerItem(TYPE_TITLE) {

    override fun getLayoutRes(): Int = R.layout.item_soe_title

    override fun getModelId(): Int = BR.item
}