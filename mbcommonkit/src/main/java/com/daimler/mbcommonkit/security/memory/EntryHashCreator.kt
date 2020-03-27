package com.daimler.mbcommonkit.security.memory

/**
 * Responsible to generate generic hashes.
 */
internal interface EntryHashCreator {

    /**
     * Generates a hash of the given text. Each generation should be unique for each possible text.
     */
    fun generateHash(text: String): String
}
