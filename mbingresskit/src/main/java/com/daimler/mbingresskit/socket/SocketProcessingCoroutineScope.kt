package com.daimler.mbingresskit.socket

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Suppress("FunctionName")
internal fun SocketProcessingCoroutineScope() =
    CoroutineScope(SupervisorJob() + Dispatchers.Main)
