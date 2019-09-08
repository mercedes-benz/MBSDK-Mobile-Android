package com.daimler.mbmobilesdk.profile

import com.daimler.mbuikit.eventbus.Event

/**
 * Event class that is fired when the user updated his profile picture.
 */
data class ProfilePictureUpdatedEvent(val pictureBytes: ByteArray) : Event