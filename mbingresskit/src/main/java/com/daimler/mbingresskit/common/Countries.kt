package com.daimler.mbingresskit.common

data class Countries(val countries: List<Country>) : Iterable<Country> by countries.asIterable()
