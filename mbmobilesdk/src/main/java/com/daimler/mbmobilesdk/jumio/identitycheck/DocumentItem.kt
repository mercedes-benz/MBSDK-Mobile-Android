package com.daimler.mbmobilesdk.jumio.identitycheck

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.jumio.Types
import com.daimler.mbmobilesdk.jumio.Variants
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerItem

class DocumentItem(context: Context, val type: Types, val variant: Variants?, private val events: ItemEvents) : MBBaseRecyclerItem() {

    val documentName = MutableLiveData(type.getLocalizedName(context))

    init {
        variant?.let {
            documentName.value += " (${it.name})"
        }
    }

    fun onClickItem() = events.onDocumentItemClicked(this)

    override fun getLayoutRes(): Int = R.layout.item_jumio_document_type

    override fun getModelId(): Int = BR.item

    interface ItemEvents {
        fun onDocumentItemClicked(item: DocumentItem)
    }
}