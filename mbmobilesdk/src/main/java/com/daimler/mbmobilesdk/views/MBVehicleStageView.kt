package com.daimler.mbmobilesdk.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.daimler.mbmobilesdk.R
import kotlinx.android.synthetic.main.view_mb_vehicle_stage.view.*

class MBVehicleStageView : ConstraintLayout {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr)

    init {
        LayoutInflater.from(context).inflate(R.layout.view_mb_vehicle_stage,
            this, true)
    }

    fun setCarTitle(title: String) {
        tv_car_title.text = title
    }

    fun setCarDescription(description: String) {
        tv_car_description.text = description
    }

    fun setCarFin(fin: String) {
        tv_car_fin.text = fin
    }

    fun setCarImage(bmp: Bitmap?) {
        bmp?.let { iv_car.setImageBitmap(bmp) }
    }

    fun setCarImage(drawable: Drawable?) {
        drawable?.let { iv_car.setImageDrawable(drawable) }
    }

    fun setShowFallbackImage(showFallback: Boolean) {
        if (showFallback) iv_car.setImageResource(R.drawable.garage_placeholder)
    }
}