package com.daimler.mbnetworkkit.socket.reconnect

data class Reconnect(val inProgress: Boolean = false, val attempt: Int = 0, val timeInMillis: Long = 0)
