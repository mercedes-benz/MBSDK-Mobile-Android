package com.daimler.mbmobilesdk.utils.extensions

import android.content.Context
import android.content.res.Configuration

fun Context.isLandscapeOrientation() =
    resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE