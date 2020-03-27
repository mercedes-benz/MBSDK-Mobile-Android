package com.daimler.mbcommonkit.tasks

import android.os.AsyncTask

/**
 * [AsyncTask] implementation that executes the given [action] in its [doInBackground] function.
 * You can provide functions that will be executed on specific timings in the task
 * execution state.
 */
@Deprecated("AsyncTask deprecated from API 30 on, use Coroutines for example")
class SimpleTask<T : Any>(private val action: () -> T) : AsyncTask<Unit, Unit, T>() {

    private var onBeforeAction: (() -> Unit)? = null
    private var onCompleteAction: ((T) -> Unit)? = null
    private var onErrorAction: ((Throwable) -> Unit)? = { throw it }
    private var onAlwaysAction: (() -> Unit)? = null

    private var error: Throwable? = null

    /**
     * Called in the tasks [onPreExecute] function.
     */
    fun onBefore(action: () -> Unit): SimpleTask<T> {
        onBeforeAction = action
        return this
    }

    /**
     * Called in the tasks [onPostExecute] function when [doInBackground] returned
     * a non-null result.
     */
    fun onComplete(action: (T) -> Unit): SimpleTask<T> {
        onCompleteAction = action
        return this
    }

    /**
     * Called in the tasks [onPostExecute] when [doInBackground] threw an error.
     */
    fun onError(action: (Throwable) -> Unit): SimpleTask<T> {
        onErrorAction = action
        return this
    }

    /**
     * Always called in [onPostExecute] after [onComplete] and [onError].
     */
    fun onAlways(action: () -> Unit): SimpleTask<T> {
        onAlwaysAction = action
        return this
    }

    override fun onPreExecute() {
        super.onPreExecute()
        onBeforeAction?.invoke()
    }

    override fun doInBackground(vararg params: Unit?): T? {
        try {
            return action()
        } catch (e: Exception) {
            error = e
        }
        return null
    }

    override fun onPostExecute(result: T?) {
        super.onPostExecute(result)
        if (result != null) {
            onCompleteAction?.invoke(result)
        } else {
            error?.let { onErrorAction?.invoke(it) }
        }
        onAlwaysAction?.invoke()
        destroy()
    }

    private fun destroy() {
        onBeforeAction = null
        onCompleteAction = null
        onErrorAction = null
        onAlwaysAction = null
    }
}
