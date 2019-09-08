package com.daimler.mbmobilesdk.jumio.extentsions

import com.daimler.mbmobilesdk.jumio.Variants
import com.jumio.nv.data.document.NVDocumentVariant

fun NVDocumentVariant.mapToModel(): Variants {
    return when (this) {
        NVDocumentVariant.PAPER -> Variants.PAPER
        NVDocumentVariant.PLASTIC -> Variants.PLASTIC
    }
}

fun Variants.mapToSDK(): NVDocumentVariant {
    return when (this) {
        Variants.PAPER -> NVDocumentVariant.PAPER
        Variants.PLASTIC -> NVDocumentVariant.PLASTIC
    }
}