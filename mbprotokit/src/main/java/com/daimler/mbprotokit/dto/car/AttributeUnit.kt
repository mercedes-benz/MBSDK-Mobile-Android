package com.daimler.mbprotokit.dto.car

import com.daimler.mbprotokit.dto.car.unit.Unit
import com.daimler.mbprotokit.dto.car.unit.UnitCase

data class AttributeUnit(
    val displayValue: String,
    val displayUnitCase: UnitCase,
    val displayUnit: Unit?
)
