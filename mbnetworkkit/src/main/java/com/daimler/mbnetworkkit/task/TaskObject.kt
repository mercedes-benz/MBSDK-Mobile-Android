package com.daimler.mbnetworkkit.task

open class TaskObject<C, F> : BaseTask<C, F>(), Task<C, F> {

    final override fun futureTask(): FutureTask<C, F> {
        return this
    }

    final override fun complete(result: C) {
        synchronized(this) {
            if (isPending().not()) {
                throw AlreadyFinishedException("TaskObject was already finished, cannot complete again")
            }
            state = TaskState.COMPLETED
            completeResult = result

            triggerComplete(result)
            triggerAlways(state, result, null)
        }
    }

    final override fun fail(result: F) {
        synchronized(this) {
            if (isPending().not()) {
                throw AlreadyFinishedException("TaskObject was already finished, cannot fail again")
            }
            state = TaskState.FAILED
            failureResult = result

            triggerFailure(result)
            triggerAlways(state, null, result)
        }
    }
}
