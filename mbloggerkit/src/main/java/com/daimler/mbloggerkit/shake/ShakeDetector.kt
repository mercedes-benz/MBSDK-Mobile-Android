package com.daimler.mbloggerkit.shake

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

internal class ShakeDetector(requiredShakeCount: Int) : SensorEventListener, ShakeProducer {

    var shakeListener: ShakeListener? = null

    private val requiredCount = when {
        requiredShakeCount < 1 -> 1
        else -> requiredShakeCount
    }
    private var shakeCount = 0
    private var mShakeTimestamp: Long = 0L
    private var shakeConsumed: Boolean = true

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    override fun continueShaking() {
        shakeConsumed = true
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (isAccelerating(event)) {
                val now = System.currentTimeMillis()
                if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                    return
                }
                // reset the shake count after 3 seconds of no shakes
                if (mShakeTimestamp + SHAKE_COUNT_RESET_TIME_MS < now) {
                    shakeCount = 0
                }
                mShakeTimestamp = now
                shakeCount++

                if (shakeConsumed.and(shakeCount >= requiredCount)) {
                    shakeListener?.let { shakeListener ->
                        shakeConsumed = false
                        shakeCount = 0
                        shakeListener.onShake(this)
                    }
                }
            }
        }
    }

    private fun isAccelerating(event: SensorEvent): Boolean {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        val gX = x / SensorManager.GRAVITY_EARTH
        val gY = y / SensorManager.GRAVITY_EARTH
        val gZ = z / SensorManager.GRAVITY_EARTH

        // gForce will be close to 1 when there is no movement.
        val gForce = sqrt((gX * gX + gY * gY + gZ * gZ).toDouble())
        return gForce > SHAKE_THRESHOLD_GRAVITY
    }

    interface ShakeListener {
        fun onShake(shaker: ShakeProducer)
    }

    companion object {
        private const val SHAKE_THRESHOLD_GRAVITY = 2.7F
        private const val SHAKE_SLOP_TIME_MS = 500
        private const val SHAKE_COUNT_RESET_TIME_MS = 1000
    }
}
