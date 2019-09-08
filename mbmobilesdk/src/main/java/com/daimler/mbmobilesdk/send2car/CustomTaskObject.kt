package com.daimler.mbmobilesdk.send2car

import com.daimler.mbnetworkkit.task.FutureTask
import com.daimler.mbnetworkkit.task.TaskObject

class CustomTaskObject<C, F> : TaskObject<C, F>() {

    override fun onComplete(callback: (C) -> Unit): FutureTask<C, F> {
        completeCallback = callback
        return super.onComplete(callback)
    }

    override fun onFailure(callback: (F) -> Unit): FutureTask<C, F> {
        failureCallback = callback
        return super.onFailure(callback)
    }
}