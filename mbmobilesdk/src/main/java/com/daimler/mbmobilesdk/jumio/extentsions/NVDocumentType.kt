package com.daimler.mbmobilesdk.jumio.extentsions

import com.daimler.mbmobilesdk.jumio.Types
import com.jumio.nv.data.document.NVDocumentType

internal fun NVDocumentType.mapToModel(): Types {
    return when (this) {
        NVDocumentType.PASSPORT -> Types.PASSPORT
        NVDocumentType.IDENTITY_CARD -> Types.ID_CARD
        NVDocumentType.DRIVER_LICENSE -> Types.DRIVER_LICENSE
        NVDocumentType.VISA -> Types.VISA
    }
}

internal fun Types.mapToSDK(): NVDocumentType {
    return when (this) {
        Types.PASSPORT -> NVDocumentType.PASSPORT
        Types.ID_CARD -> NVDocumentType.IDENTITY_CARD
        Types.DRIVER_LICENSE -> NVDocumentType.DRIVER_LICENSE
        Types.VISA -> NVDocumentType.VISA
    }
}