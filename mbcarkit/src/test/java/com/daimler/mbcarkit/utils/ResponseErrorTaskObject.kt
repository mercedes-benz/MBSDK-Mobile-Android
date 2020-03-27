package com.daimler.mbcarkit.utils

import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.TaskObject

internal typealias ResponseTaskObject<T> = TaskObject<T, ResponseError<out RequestError>?>
internal typealias ResponseTaskObjectUnit = ResponseTaskObject<Unit>
