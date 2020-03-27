package com.daimler.mbcarkit.network.error

import com.daimler.mbnetworkkit.networking.RequestError

class PinRequestError(msg: String) : IllegalArgumentException(msg), RequestError
