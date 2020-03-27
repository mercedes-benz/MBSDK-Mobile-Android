package com.daimler.mbcarkit.network.service

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Suppress("FunctionName")
internal fun NetworkCoroutineScope() =
    CoroutineScope(SupervisorJob() + Dispatchers.IO)
