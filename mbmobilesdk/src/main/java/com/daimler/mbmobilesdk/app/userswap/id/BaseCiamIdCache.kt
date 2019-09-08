package com.daimler.mbmobilesdk.app.userswap.id

internal abstract class BaseCiamIdCache : UserIdCache {

    protected companion object {
        const val KEY_CIAM_ID = "user.id.ciam"
    }
}