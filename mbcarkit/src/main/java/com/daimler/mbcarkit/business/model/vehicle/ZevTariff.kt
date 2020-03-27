package com.daimler.mbcarkit.business.model.vehicle

class ZevTariff(
    /**
     * Price level
     */
    val rate: Rate,

    /**
     * Time in seconds from midnight
     */
    val time: Int
)
