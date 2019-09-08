package com.daimler.mbmobilesdk.tou.soe

import androidx.annotation.IntDef
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerItem

internal abstract class SoeRecyclerItem(
    @Type private val type: Int
) : MBBaseRecyclerItem() {

    val isTitle = isType(TYPE_TITLE)
    val isAgreement = isType(TYPE_AGREEMENT)

    private fun isType(@Type type: Int) = this.type == type

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(TYPE_TITLE, TYPE_AGREEMENT)
    protected annotation class Type

    companion object {
        const val TYPE_TITLE = 1
        const val TYPE_AGREEMENT = 2
    }
}