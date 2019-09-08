package com.daimler.mbmobilesdk.serviceactivation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout

internal abstract class BaseMissingFieldView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), FieldResolvable {

    protected var callback: MissingFieldCallback? = null

    override fun applyCallback(callback: MissingFieldCallback) {
        this.callback = callback
    }

    override fun view(): View? {
        return this
    }
}