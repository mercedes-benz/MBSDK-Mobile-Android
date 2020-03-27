package com.daimler.mbprotokit.dto.car.theft

enum class ActiveSelectionState(val id: Int) {
    NOT_ACTIVE_SELECTED(0),
    NOT_ACTIVE_UNSELECTED(1),
    ACTIVE(2);

    companion object {
        fun map(id: Int?) = values().find {
            id == it.id
        }

        fun map(): (Int?) -> ActiveSelectionState? = { map(it) }
    }
}
