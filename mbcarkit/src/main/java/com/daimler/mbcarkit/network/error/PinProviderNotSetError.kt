package com.daimler.mbcarkit.network.error

import com.daimler.mbnetworkkit.networking.RequestError

class PinProviderNotSetError : IllegalArgumentException("No PinProvider set!"), RequestError
