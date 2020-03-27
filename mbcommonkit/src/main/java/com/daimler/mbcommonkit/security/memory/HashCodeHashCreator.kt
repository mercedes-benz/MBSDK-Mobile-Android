package com.daimler.mbcommonkit.security.memory

/**
 * [EntryHashCreator] that uses [hashCode] for hashing.
 */
internal class HashCodeHashCreator : EntryHashCreator {

    override fun generateHash(text: String): String {
        return text.hashCode().toString()
    }
}
