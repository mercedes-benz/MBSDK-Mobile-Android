package com.daimler.mbingresskit.persistence

internal class RealmUtil {
    companion object {
        const val ID_ENCR_INGRESS_REALM = "encrypted_ingress_realm"

        /**
         * Schema version which is updated if schema changes -> will be reset with release
         */
        const val REALM_ENCR_SCHEMA_VERSION = 22L

        const val ENCR_INGRESS_FILE_NAME = "encrypted_ingress_realm_table"
    }
}
