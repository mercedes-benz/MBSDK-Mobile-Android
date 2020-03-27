package com.daimler.mbmobilesdk.implementation

import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbnetworkkit.common.TokenProvider
import com.daimler.mbnetworkkit.common.TokenProviderCallback
import com.daimler.mbnetworkkit.networking.defaultErrorMapping

class IngressTokenProvider : TokenProvider {

    override fun requestToken(callback: TokenProviderCallback) {
        MBIngressKit.refreshTokenIfRequired()
            .onComplete { callback.onTokenReceived(it.accessToken) }
            .onFailure {
                MBLoggerKit.e("Reuqest token failed.")
                callback.onRequestFailed(defaultErrorMapping(it))
            }
    }
}
