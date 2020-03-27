package com.daimler.mbmobilesdk.example.car

import androidx.annotation.StringRes
import com.daimler.mbmobilesdk.example.R

sealed class SendToCarError(@StringRes val text: Int) {
    object TokenRefresh : SendToCarError(R.string.s2c_error_token_refresh)
    object UnsupportedVehicle : SendToCarError(R.string.s2c_vehicle_not_supported)
    object Preconditions : SendToCarError(R.string.s2c_preconditions_missing)
    object Misc : SendToCarError(R.string.s2c_failed_unknown)
}
