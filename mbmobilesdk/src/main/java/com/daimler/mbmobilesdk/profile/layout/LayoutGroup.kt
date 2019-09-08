package com.daimler.mbmobilesdk.profile.layout

interface LayoutGroup<T> {

    fun add(t: T)

    fun findByTag(tag: Any?): T?
}