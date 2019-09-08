package com.daimler.mbmobilesdk.socket

import android.app.Application
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbcarkit.MBCarKit

abstract class MyCarSocketAndroidViewModel(app: Application) : SocketAndroidViewModel(app) {

    override fun connectToSocket() {
        MBIngressKit.refreshTokenIfRequired()
            .onComplete {
                MBCarKit.connectToWebSocket(it.jwtToken.plainToken, this)
            }.onFailure {
                MBLoggerKit.e("Failed to refresh token.", throwable = it)
            }
    }

    override fun disposeFromSocket() {
        MBCarKit.unregisterFromSocket(this)
    }
}