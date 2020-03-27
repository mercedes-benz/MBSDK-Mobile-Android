package com.daimler.mbcarkit.business

import com.daimler.mbcarkit.business.model.merchants.GeoCoordinates
import com.daimler.mbcarkit.business.model.merchants.Merchants
import com.daimler.mbcarkit.business.model.merchants.OutletActivity
import com.daimler.mbcarkit.business.model.merchants.Radius
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface OutletsService {

    /**
     * @param token A valid access-token
     * @param countryIsoCode IsoCode, e.g. "DE" or "US"
     * @param geoCoordinates The search center's latitude/longitude
     * @param radius Search radius, e.g. Radius("28", "km"). Default is 50 kilometers
     * @param outletActivity The search subject, either SALES or SERVICE
     */
    fun fetchOutlets(
        token: String?,
        zipOrCity: String?,
        countryIsoCode: String?,
        geoCoordinates: GeoCoordinates?,
        radius: Radius = Radius(DEFAULT_RADIUS, DEFAULT_RADIUS_UNIT),
        outletActivity: OutletActivity? = null
    ): FutureTask<Merchants, ResponseError<out RequestError>?>

    private companion object {
        private const val DEFAULT_RADIUS = "50"
        private const val DEFAULT_RADIUS_UNIT = "km"
    }
}
