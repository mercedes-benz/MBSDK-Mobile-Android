package com.daimler.mbmobilesdk.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.daimler.mbloggerkit.MBLoggerKit
import java.io.File

/**
 * Returns a [File] in the external picture directory with the package name and
 * the current timestamp as name. File extension is png.
 */
fun createImageFile(context: Context): File {
    val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
    val name = "${context.packageName}_${System.currentTimeMillis()}.png"
    return File(dir, name)
}

/**
 * Collects all available Intents for cameras and galleries.
 * The image file for camera pictures is determined by [createImageFile]. The returned
 * [ChooserIntent] contains that uri. The Intent within the returned ChooserIntent can be used
 * to show a chooser from where the user can select an application to take/ select a picture.
 * ```
 *  class MyActivity : Activity() {
 *
 *      fun myFunction() {
 *          val chooserIntent = createImageChooserIntent(this)
 *          startActivityForResult(chooserIntent.intent, REQ_CODE)
 *      }
 *
 *      companion object {
 *          private const val REQ_CODE = 10
 *      }
 *  }
 * ```
 */
fun createImageChooserIntent(activity: Activity): ChooserIntent {
    // camera intents
    val outputFile = createImageFile(activity)
    val uri = Uri.fromFile(outputFile)
    val cameraIntents = getCameraIntents(activity, uri)
    MBLoggerKit.d("Received ${cameraIntents.size} camera intents.")

    // gallery intents
    val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
    val chooserIntent = Intent.createChooser(galleryIntent, "")
    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents)

    return ChooserIntent(uri.toString(), chooserIntent)
}

/**
 * Resizes the given bitmap to create a bitmap with width and height at least as large as the
 * given values.
 *
 * @return the resized bitmap or null if the bitmap could not be decoded
 */
fun resizeBitmap(bytes: ByteArray, targetWidth: Int, targetHeight: Int): Bitmap? {
    val options = BitmapFactory.Options()
    options.inJustDecodeBounds = true
    BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
    options.inSampleSize = calculateSampleSize(options, targetWidth, targetHeight)
    options.inJustDecodeBounds = false

    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, options)
}

private fun getCameraIntents(activity: Activity, uri: Uri): Array<Intent> {
    val camIntents = ArrayList<Intent>()
    val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
    val camList = activity.packageManager.queryIntentActivities(pictureIntent, 0)
    camList.asSequence().map { info ->
        val intent = Intent(pictureIntent)
        intent.`package` = info.activityInfo.packageName
        intent
    }.toCollection(camIntents)
    return camIntents.toArray(arrayOfNulls(camIntents.size))
}

private fun calculateSampleSize(
    options: BitmapFactory.Options,
    targetWidth: Int,
    targetHeight: Int
): Int {
    val (width: Int, height: Int) = options.run { outWidth to outHeight }
    var factor = 1

    if (width > targetWidth || height > targetHeight) {
        val scaledWidth = width / 2
        val scaledHeight = height / 2

        while (scaledWidth / factor >= targetWidth && scaledHeight / factor >= targetHeight) {
            factor *= 2
        }
    }

    return factor
}

data class ChooserIntent(val cameraUri: String, val intent: Intent)