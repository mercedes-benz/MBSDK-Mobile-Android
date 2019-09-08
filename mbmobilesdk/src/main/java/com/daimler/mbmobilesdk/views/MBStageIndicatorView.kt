package com.daimler.mbmobilesdk.views

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.utils.extensions.*

class MBStageIndicatorView : View {

    private var circleColor = findColor(R.color.mb_accent_primary)
    private var textColorUnselected = circleColor
    private val textColorSelected = findColor(R.color.ris_white)
    private var dividerColor = findColor(R.color.ris_grey3)

    private var circleRadius = dpToPx(DEFAULT_RADIUS_DP).toFloat()
    private var circlePadding = dpToPx(DEFAULT_CIRCLE_PADDING_DP).toFloat()
    private val circleStrokeWidth = dpToPx(DEFAULT_CIRCLE_STROKE_WIDTH_DP).toFloat()
    private var dividerWidth = dpToPx(DEFAULT_DIVIDER_WIDTH_DP).toFloat()
    private var dividerStrokeWidth = dpToPx(DEFAULT_DIVIDER_STROKE_WIDTH_DP).toFloat()
    private var textSize = circleRadius * TEXT_SIZE_FACTOR
    private val textRect = Rect()

    private var stages = 0
    private var selectedStage = 0

    private lateinit var paints: Paints

    constructor(context: Context) : super(context) {
        initialize(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr) {
        initialize(attrs)
    }

    private fun initialize(attrs: AttributeSet?) {
        val array: TypedArray? = obtainTypedArrayOrNull(attrs, R.styleable.MBStageIndicatorView)
        stages = array.getInt(R.styleable.MBStageIndicatorView_stages, 0)
        selectedStage = array.getInt(R.styleable.MBStageIndicatorView_selectedStage, 0)
        circleColor = array.getColor(R.styleable.MBStageIndicatorView_circleColor, circleColor)
        circleRadius = array.getDimensionPixelSize(R.styleable.MBStageIndicatorView_circleRadius, circleRadius.toInt()).toFloat()
        circlePadding = array.getDimensionPixelSize(R.styleable.MBStageIndicatorView_circlePadding, circlePadding.toInt()).toFloat()
        dividerWidth = array.getDimensionPixelSize(R.styleable.MBStageIndicatorView_dividerWidth, dividerWidth.toInt()).toFloat()
        dividerColor = array.getColor(R.styleable.MBStageIndicatorView_dividerColor, dividerColor)
        array?.recycle()

        textColorUnselected = circleColor
        textSize = circleRadius * TEXT_SIZE_FACTOR

        paints = Paints(circleColor, circleStrokeWidth, textColorSelected,
            textColorUnselected, textSize, dividerStrokeWidth, dividerColor)
    }

    fun setStages(stages: Int) {
        this.stages = stages
        invalidate()
    }

    fun setSelectedStage(stage: Int) {
        this.selectedStage = stage
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val allRadius = stages * (circleRadius * 2 + circlePadding * 2) + (stages - 1) * dividerWidth
        setMeasuredDimension(allRadius.toInt(), (circleRadius * 2 + circlePadding * 2).toInt())
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        var x = circlePadding + circleRadius
        val y = circleRadius + circlePadding
        repeat(stages) {
            val drawStage = it + 1
            val selected = drawStage == selectedStage
            val circlePaint = paints.circlePaint(selected)
            val textPaint = paints.textPaint(selected)

            canvas.drawCircle(x, y, circleRadius, circlePaint)

            val text = if (drawStage < selectedStage) CHECKMARK_UNICODE else drawStage.toString()
            val textHeight = getTextHeight(text, textPaint)
            canvas.drawText(text, 0, text.length, x, y + textHeight / 2, textPaint)

            if (drawStage < stages) {
                val lineStart = x + circleRadius + circlePadding
                val lineEnd = lineStart + dividerWidth
                canvas.drawLine(lineStart, y, lineEnd, y, paints.dividerPaint())
                x = lineEnd + circlePadding * 2
            }
        }
    }

    private fun getTextHeight(text: String, paint: Paint): Float {
        paint.getTextBounds(text, 0, text.length, textRect)
        return textRect.height().toFloat()
    }

    private class Paints(
        circleColor: Int,
        circleStrokeWidth: Float,
        textColorSelected: Int,
        textColorUnselected: Int,
        textSize: Float,
        dividerStrokeWidth: Float,
        dividerColor: Int
    ) {

        private val selectedCirclePaint = fillPaint(circleColor)
        private val unselectedCirclePaint = strokePaint(circleColor, circleStrokeWidth)
        private val selectedTextPaint = textPaint(textColorSelected, textSize)
        private val unselectedTextPaint = textPaint(textColorUnselected, textSize)
        private val dividerPaint = strokePaint(dividerColor, dividerStrokeWidth)

        fun circlePaint(selected: Boolean) = if (selected) selectedCirclePaint else unselectedCirclePaint

        fun textPaint(selected: Boolean) = if (selected) selectedTextPaint else unselectedTextPaint

        fun dividerPaint() = dividerPaint

        private fun fillPaint(color: Int) = antiAliasPaint().apply {
            style = Paint.Style.FILL
            this.color = color
        }

        private fun strokePaint(color: Int, width: Float) = antiAliasPaint().apply {
            style = Paint.Style.STROKE
            strokeWidth = width
            this.color = color
        }

        private fun textPaint(color: Int, size: Float) = antiAliasPaint().apply {
            style = Paint.Style.STROKE
            this.color = color
            textAlign = Paint.Align.CENTER
            textSize = size
        }

        private fun antiAliasPaint() = Paint().apply { isAntiAlias = true }
    }

    private companion object {

        private const val DEFAULT_RADIUS_DP = 16
        private const val DEFAULT_CIRCLE_PADDING_DP = 16
        private const val DEFAULT_DIVIDER_WIDTH_DP = 24
        private const val TEXT_SIZE_FACTOR = 1.25f
        private const val DEFAULT_DIVIDER_STROKE_WIDTH_DP = 2
        private const val DEFAULT_CIRCLE_STROKE_WIDTH_DP = 1
        private const val CHECKMARK_UNICODE = "\u2713"
    }
}