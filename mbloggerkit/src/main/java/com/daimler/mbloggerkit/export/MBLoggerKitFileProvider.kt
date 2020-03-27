package com.daimler.mbloggerkit.export

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

class MBLoggerKitFileProvider : FileProvider() {

    companion object {
        fun getUriForFile(context: Context, file: File): Uri {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                getUriForFile(context, authority(context), file)
            } else {
                Uri.fromFile(file)
            }
        }

        /* MUST be the same as declared in the manifest. */
        private fun authority(context: Context) =
            "${context.applicationContext.packageName}.provider"
    }
}
