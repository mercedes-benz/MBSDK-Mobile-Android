package com.daimler.mbnetworkkit.task

interface Task<C, F> {
    fun complete(result: C)
    fun fail(result: F)
    fun futureTask(): FutureTask<C, F>
}
