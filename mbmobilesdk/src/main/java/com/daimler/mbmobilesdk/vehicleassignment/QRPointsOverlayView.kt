package com.daimler.mbmobilesdk.vehicleassignment

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import com.daimler.mbuikit.utils.extensions.dpToPx

class QRPointsOverlayView : View {

    private val paint: Paint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    private val radius = dpToPx(CIRCLE_RADIUS_DP).toFloat()

    var points: Array<PointF> = emptyArray()
        set(value) {
            field = value
            invalidate()
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        points.forEach { canvas.drawCircle(it.x, it.y, radius, paint) }
    }

    companion object {

        private const val CIRCLE_RADIUS_DP = 4
    }
}