package com.daimler.mbmobilesdk.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.extensions.isLandscapeOrientation
import com.daimler.mbuikit.utils.extensions.dpToPx
import com.daimler.mbuikit.utils.extensions.findColor

class ScannerFrame : View {

    private var frameColor = findColor(R.color.mb_accent_primary)
    private var frameThickness = dpToPx(DEFAULT_THICKNESS_DP).toFloat()
    private var cornerLength = dpToPx(DEFAULT_CORNER_LENGTH_DP).toFloat()

    private val drawingPath = Path()
    private var framePaint = Paint().apply {
        style = Paint.Style.STROKE
        color = frameColor
        strokeWidth = frameThickness
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr)

    init {
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT)
        setBackgroundColor(Color.TRANSPARENT)
    }

    fun setFrameBorderColor(color: Int) {
        frameColor = color
        framePaint.color = color
        invalidate()
    }

    fun setFrameWidth(width: Float) {
        frameThickness = width
        framePaint.strokeWidth = width
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val width = width
        val height = height

        drawingPath.reset()

        val frameLength = frameLengthConstant() * DEFAULT_LENGTH_WIDTH_RELATIVE
        val sideOffset = width / 2f - frameLength / 2f
        val topOffset = height / 2f - frameLength / 2f

        // top left
        drawingPath.moveTo(sideOffset, topOffset + cornerLength)
        drawingPath.lineTo(sideOffset, topOffset)
        drawingPath.lineTo(sideOffset + cornerLength, topOffset)

        // top right
        drawingPath.moveTo(width - sideOffset, topOffset + cornerLength)
        drawingPath.lineTo(width - sideOffset, topOffset)
        drawingPath.lineTo(width - sideOffset - cornerLength, topOffset)

        // bottom left
        drawingPath.moveTo(sideOffset, height - topOffset - cornerLength)
        drawingPath.lineTo(sideOffset, height - topOffset)
        drawingPath.lineTo(sideOffset + cornerLength, height - topOffset)

        // bottom right
        drawingPath.moveTo(width - sideOffset, height - topOffset - cornerLength)
        drawingPath.lineTo(width - sideOffset, height - topOffset)
        drawingPath.lineTo(width - sideOffset - cornerLength, height - topOffset)

        canvas.drawPath(drawingPath, framePaint)
    }

    private fun frameLengthConstant() = if (context.isLandscapeOrientation()) height else width

    private companion object {
        private const val DEFAULT_THICKNESS_DP = 3
        private const val DEFAULT_CORNER_LENGTH_DP = 50
        private const val DEFAULT_LENGTH_WIDTH_RELATIVE = 0.75f
    }
}