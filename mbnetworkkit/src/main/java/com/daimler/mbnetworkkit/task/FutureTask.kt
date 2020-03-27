package com.daimler.mbnetworkkit.task

interface FutureTask<C, F> {
    /**
     * Execute callback upon successful completion of this FutureTask.
     * If the task is already successfully completed, the callback is executed immediately.
     */
    fun onComplete(callback: (C) -> Unit): FutureTask<C, F>

    /**
     * Execute callback upon failed complection of this FutureTask.
     * If this task already failed, the callback is executed immediately.
     */
    fun onFailure(callback: (F) -> Unit): FutureTask<C, F>

    /**
     * Execute callback upon completion of this FutureTask.
     * If this task already completed, the callback is executed immediately.
     */
    fun onAlways(callback: (TaskState, C?, F?) -> Unit): FutureTask<C, F>

    /**
     * returns true iff the task has not completed yet.
     */
    fun isPending(): Boolean

    /**
     * returns true iff the task has completed successfully.
     */
    fun isCompleted(): Boolean

    /**
     * returns true iff the task has completed with an error.
     */
    fun isFailed(): Boolean
}
