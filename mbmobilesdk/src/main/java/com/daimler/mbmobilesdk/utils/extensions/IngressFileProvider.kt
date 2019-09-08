package com.daimler.mbmobilesdk.utils.extensions

import android.content.Context
import android.content.Intent
import com.daimler.mbingresskit.util.IngressFileProvider
import java.io.File

internal fun IngressFileProvider.Companion.getPdfIntent(context: Context, filePath: String): Intent? {
    val file = File(filePath)
    val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(getUriForFile(context, file), TYPE_PDF)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    return if (intent.resolveActivity(context.packageManager) != null) intent else null
}

private const val TYPE_PDF = "application/pdf"