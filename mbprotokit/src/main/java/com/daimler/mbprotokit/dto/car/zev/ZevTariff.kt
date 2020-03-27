package com.daimler.mbprotokit.dto.car.zev

import com.daimler.mbprotokit.generated.VehicleEvents

class ZevTariff(
    /**
     * Price level
     */
    val rate: Rate,

    /**
     * Time in seconds from midnight
     */
    val time: Int
) {
    companion object {
        fun mapToWeekdayTariffs(): (VehicleEvents.WeekdayTariffValue?) -> List<ZevTariff>? = {
            it?.tariffsList?.map { tariff ->
                ZevTariff(Rate.map(tariff.rate), tariff.time)
            }
        }

        fun mapToWeekendTariffs(): (VehicleEvents.WeekendTariffValue?) -> List<ZevTariff>? = {
            it?.tariffsList?.map { tariff ->
                ZevTariff(Rate.map(tariff.rate), tariff.time)
            }
        }
    }
}
