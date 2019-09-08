package com.daimler.mbmobilesdk.support.models

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import com.daimler.mbmobilesdk.BR
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerItem
import com.daimler.mbmobilesdk.R

open class ImageItem(val icon: Bitmap, val listener: GridItemListener) : MBBaseRecyclerItem() {

    override fun getLayoutRes(): Int = R.layout.griditem_support_imagelist

    override fun getModelId(): Int = BR.item

    val closeVisible = MutableLiveData<Boolean>()
    val liveImage = MutableLiveData<Bitmap>()

    init {
        closeVisible.postValue(this is RealImage)
        liveImage.postValue(icon)
    }

    fun hideRemoveForOverview() {
        closeVisible.postValue(false)
    }

    fun onRemoveClick() = listener.onAttachedImageCloserClicked(this)

    fun onAddClick() {
        if (this is RealImage) return
        listener.onAddImageClicked()
    }

    interface GridItemListener {
        fun onAttachedImageCloserClicked(imageItem: ImageItem)
        fun onAddImageClicked()
    }
}
