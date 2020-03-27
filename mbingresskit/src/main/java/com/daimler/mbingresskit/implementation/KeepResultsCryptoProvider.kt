package com.daimler.mbingresskit.implementation

import com.daimler.mbcommonkit.security.Crypto

internal class KeepResultsCryptoProvider : CryptoProvider {

    override val crypto: Crypto = Crypto(true)
}
