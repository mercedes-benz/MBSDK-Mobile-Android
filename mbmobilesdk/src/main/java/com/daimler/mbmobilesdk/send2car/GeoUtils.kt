package com.daimler.mbmobilesdk.send2car

import android.location.Location

object GeoUtils {

    private val bits = intArrayOf(16, 8, 4, 2, 1)
    private val base32Chars = "0123456789bcdefghjkmnpqrstuvwxyz"

    fun decodeGeoHash(hash: String): Location? {
        var isEven = true
        val lat = DoubleArray(2)
        val lon = DoubleArray(2)
        lat[0] = -90.0
        lat[1] = 90.0
        lon[0] = -180.0
        lon[1] = 180.0

        for (i in 0 until hash.length) {
            val c = hash[i]
            val cd = base32Chars.indexOf(c)
            for (j in 0..4) {
                val mask = bits[j]
                if (isEven) {
                    refineInterval(lon, cd, mask)
                } else {
                    refineInterval(lat, cd, mask)
                }
                isEven = !isEven
            }
        }

        return Location("").apply {
            latitude = (lat[0] + lat[1]) / 2
            longitude = (lon[0] + lon[1]) / 2
        }
    }

    private fun refineInterval(interval: DoubleArray, cd: Int, mask: Int) {
        if (cd and mask != 0) {
            interval[0] = (interval[0] + interval[1]) / 2
        } else {
            interval[1] = (interval[0] + interval[1]) / 2
        }
    }
}