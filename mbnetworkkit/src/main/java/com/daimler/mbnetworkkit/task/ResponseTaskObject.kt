package com.daimler.mbnetworkkit.task

import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError

typealias ResponseTaskObject<T> = TaskObject<T, ResponseError<out RequestError>?>
