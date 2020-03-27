package com.daimler.mbprotokit.dto.car

import java.util.Date

data class AttributeInfo(
    val status: AttributeStatus,
    val lastChanged: Date?,
    val unit: AttributeUnit?
)
