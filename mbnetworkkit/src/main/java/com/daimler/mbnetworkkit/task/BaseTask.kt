package com.daimler.mbnetworkkit.task

abstract class BaseTask<C, F> : FutureTask<C, F> {

    protected var completeResult: C? = null

    protected var failureResult: F? = null

    protected var completeCallback: ((C) -> Unit)? = null

    protected var failureCallback: ((F) -> Unit)? = null

    private var alwaysCallback: ((TaskState, C?, F?) -> Unit)? = null

    var state = TaskState.PENDING
        internal set

    protected fun triggerComplete(result: C) {
        completeCallback?.invoke(result)
        completeCallback = null
    }

    protected fun triggerFailure(result: F) {
        failureCallback?.invoke(result)
        failureCallback = null
    }

    protected fun triggerAlways(state: TaskState, completeResult: C?, failureResult: F?) {
        alwaysCallback?.invoke(state, completeResult, failureResult)
        alwaysCallback = null
    }

    override fun onComplete(callback: (C) -> Unit): FutureTask<C, F> {
        synchronized(this) {
            if (isCompleted()) {
                completeResult?.let {
                    callback.invoke(it)
                }
            } else {
                completeCallback = callback
            }
        }
        return this
    }

    override fun onFailure(callback: (F) -> Unit): FutureTask<C, F> {
        synchronized(this) {
            if (isFailed()) {
                failureResult?.let {
                    callback.invoke(it)
                }
            } else {
                failureCallback = callback
            }
        }
        return this
    }

    override fun onAlways(callback: (TaskState, C?, F?) -> Unit): FutureTask<C, F> {
        synchronized(this) {
            if (!isPending()) {
                callback.invoke(state, completeResult, failureResult)
            } else {
                alwaysCallback = callback
            }
        }
        return this
    }

    override fun isPending() = state == TaskState.PENDING

    override fun isCompleted() = state == TaskState.COMPLETED

    override fun isFailed() = state == TaskState.FAILED

    class AlreadyFinishedException @JvmOverloads constructor(message: String, cause: Throwable? = null) : IllegalStateException(message, cause)
}
