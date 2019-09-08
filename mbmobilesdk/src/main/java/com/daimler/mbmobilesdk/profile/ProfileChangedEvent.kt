package com.daimler.mbmobilesdk.profile

import com.daimler.mbingresskit.common.User
import com.daimler.mbuikit.eventbus.Event

data class ProfileChangedEvent(val user: User) : Event