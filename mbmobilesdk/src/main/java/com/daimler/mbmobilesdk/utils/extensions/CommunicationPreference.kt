package com.daimler.mbmobilesdk.utils.extensions

import com.daimler.mbingresskit.common.CommunicationPreference

internal fun CommunicationPreference.noneSelected() =
    !contactByPhone && !contactByMail && !contactBySms && !contactByLetter

internal fun CommunicationPreference.anySelected() = !noneSelected()