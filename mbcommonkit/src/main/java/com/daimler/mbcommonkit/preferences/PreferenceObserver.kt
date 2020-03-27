package com.daimler.mbcommonkit.preferences

interface PreferenceObserver<T> {

    fun onChanged(newValue: T)
}
