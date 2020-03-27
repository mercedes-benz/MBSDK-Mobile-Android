package com.daimler.mbcarkit.persistance

class RealmUtil {
    companion object {
        const val ID_ENCRYPTED_REALM = "encrypted_mb_car_kit_realm"
        const val ID_REALM = "mb_car_kit_realm"

        /**
         * Must be the same as name of attribute in RealmVehicleState.vin
         */
        const val FIELD_VIN_OR_FIN = "finOrVin"

        const val FIELD_IMAGE_KEY = "primaryKey"

        /**
         * Schema version which is updated if schema changes -> will be reset with release
         */
        const val REALM_ENCRYPTED_SCHEMA_VERSION = 35L

        const val ENCRYPTED_FILE_NAME = "encrypted_mb_car_kit_realm_table"

        const val REALM_SCHEMA_VERSION = 35L

        const val REALM_FILE_NAME = "mb_car_kit_realm_table"
    }
}
