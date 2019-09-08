package com.daimler.mbmobilesdk.serviceactivation.views

import android.view.View

interface FieldResolvable {

    fun applyCallback(callback: MissingFieldCallback)
    fun view(): View?
}