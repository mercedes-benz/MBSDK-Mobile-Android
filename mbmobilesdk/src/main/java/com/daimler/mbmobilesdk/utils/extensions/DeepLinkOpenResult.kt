package com.daimler.mbmobilesdk.utils.extensions

import com.daimler.mbdeeplinkkit.common.DeepLinkOpenResult

internal fun DeepLinkOpenResult.anyTargetOpened() =
    this == DeepLinkOpenResult.SUCCESS || this == DeepLinkOpenResult.FALLBACK_OPENED