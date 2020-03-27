package com.daimler.mbingresskit

import com.daimler.mbnetworkkit.networking.RequestError

class TooManyConcurrentRequestsException : IllegalStateException("Too many concurrent requests."), RequestError
