package com.daimler.mbmobilesdk.views

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.annotation.IntDef
import androidx.constraintlayout.widget.ConstraintLayout

internal class SwipeConstraintLayout : ConstraintLayout {

    private var lastChildWidth = 0
    private var touchX = -1f
    private var swiped = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
        super(context, attrs, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val lastWidth = lastChildWidth()
        if (lastWidth > 0) {
            setMeasuredDimension(width + lastWidth, height)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                touchX = event.x
                swiped = false
            }
            MotionEvent.ACTION_MOVE -> {
                val delta = event.x - touchX
                if (delta >= MIN_SWIPE_DISTANCE_PX) {
                    swiped = handleSwipe(DIRECTION_RIGHT)
                } else if (delta <= -MIN_SWIPE_DISTANCE_PX) {
                    swiped = handleSwipe(DIRECTION_LEFT)
                }
                return swiped
            }
        }
        return swiped || super.onTouchEvent(event)
    }

    private fun handleSwipe(@SwipeDirection direction: Int): Boolean {
        if (direction == DIRECTION_LEFT && x >= 0) {
            animateBy(-lastChildWidth())
            return true
        } else if (direction == DIRECTION_RIGHT && x < 0) {
            animateBy(lastChildWidth())
            return true
        }
        return false
    }

    private fun lastChildWidth(): Int {
        if (lastChildWidth == 0) {
            val count = childCount
            if (count > 0) {
                lastChildWidth = getChildAt(count - 1).width
            }
        }
        return lastChildWidth
    }

    private fun animateBy(distance: Int) {
        animate()
            .translationX(x + distance)
            .setDuration(ANIMATION_MILLIS)
            .start()
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(DIRECTION_LEFT, DIRECTION_RIGHT)
    private annotation class SwipeDirection

    companion object {
        private const val MIN_SWIPE_DISTANCE_PX = 20

        private const val ANIMATION_MILLIS = 100L

        private const val DIRECTION_LEFT = 0
        private const val DIRECTION_RIGHT = 1
    }
}