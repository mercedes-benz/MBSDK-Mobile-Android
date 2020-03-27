package com.daimler.mbcarkit.business.model.merchants

class Merchants(val merchants: List<Merchant>, val isUpdated: Boolean = true) : Iterable<Merchant> {

    override fun iterator(): Iterator<Merchant> {
        return merchants.iterator()
    }
}
