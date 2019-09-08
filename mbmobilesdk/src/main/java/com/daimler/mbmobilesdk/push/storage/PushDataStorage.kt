package com.daimler.mbmobilesdk.push.storage

import com.daimler.mbmobilesdk.push.PushData

internal interface PushDataStorage {

    var latestPushData: PushData?

    fun clear()
}