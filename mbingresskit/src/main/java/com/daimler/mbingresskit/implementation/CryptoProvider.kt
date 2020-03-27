package com.daimler.mbingresskit.implementation

import com.daimler.mbcommonkit.security.Crypto

internal interface CryptoProvider {

    val crypto: Crypto
}
