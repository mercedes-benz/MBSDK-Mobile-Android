package com.daimler.mbmobilesdk.stageselector

import androidx.databinding.ObservableBoolean
import com.daimler.mbuikit.components.recyclerview.MBBaseRecyclerItem

internal abstract class BaseStageItem(val selectable: Boolean) : MBBaseRecyclerItem() {

    val selected = ObservableBoolean()

    abstract fun select()

    abstract fun deselect()
}