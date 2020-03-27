package com.daimler.mbingresskit

import com.daimler.mbnetworkkit.networking.RequestError

class NotLoggedInException : IllegalStateException("Cannot update token because currently not logged in"), RequestError
