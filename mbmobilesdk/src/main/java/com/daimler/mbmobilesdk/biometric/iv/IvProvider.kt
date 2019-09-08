package com.daimler.mbmobilesdk.biometric.iv

internal interface IvProvider {

    /**
     * Returns the IV used for encryption for the given alias and tag.
     */
    fun getIvForAlias(alias: String, tag: String): ByteArray?

    /**
     * Saves the IV used for encryption for the given alias and tag.
     * NOTE: The tag MUST be unique per alias.
     */
    fun saveIvForAlias(alias: String, tag: String, iv: ByteArray)

    /**
     * Deletes all IVs for the given alias.
     */
    fun deleteIvForAlias(alias: String)
}