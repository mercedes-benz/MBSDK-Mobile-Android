package com.daimler.mbloggerkit.format

interface TagStrategy {
    fun createTag(tag: String?): String
}
