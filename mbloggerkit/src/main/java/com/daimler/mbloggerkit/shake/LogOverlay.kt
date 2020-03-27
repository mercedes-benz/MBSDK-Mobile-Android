package com.daimler.mbloggerkit.shake

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbloggerkit.R
import com.daimler.mbloggerkit.export.shareAsFile
import com.daimler.mbloggerkit.export.shareAsText
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LogOverlay(private val order: Order) : ShakeDetector.ShakeListener, Application.ActivityLifecycleCallbacks {

    private var context: Activity? = null

    companion object {
        private const val POSITION_SHOW_LOG = 0
        private const val POSITION_SHARE_LOG_TEXT = 1
        private const val POSITION_SHARE_LOG_FILE = 2
        private const val LOG_PATTERN = "yyyy_MM_dd-HH_mm_ss_SSS"
        private const val FILE_EXTENSION_LOG = ".log"
        private const val MEGABYTE = 1024 * 1000

        fun showChooser(context: Activity, order: Order) {
            LogOverlay(order).apply {
                showChooserDialog(context)
            }
        }
    }

    override fun onShake(shaker: ShakeProducer) {
        context?.let {
            showChooserDialog(it, shaker)
        } ?: skipShake(shaker)
    }

    private fun showChooserDialog(context: Activity, shaker: ShakeProducer? = null) {
        val selectionDialogBuilder = AlertDialog.Builder(context)
        val resources = context.resources
        val arrayAdapter = ArrayAdapter<String>(context, android.R.layout.simple_selectable_list_item)
        arrayAdapter.apply {
            add(resources.getString(R.string.show_log))
            add(resources.getString(R.string.share_as_text))
            add(resources.getString(R.string.share_as_file))
        }
        selectionDialogBuilder.setAdapter(arrayAdapter) { dialog, which ->
            when (which) {
                POSITION_SHOW_LOG -> showLog(context)
                POSITION_SHARE_LOG_TEXT -> {
                    val currentLog = MBLoggerKit.loadCurrentLog()
                    if (currentLog.toString().toByteArray(Charsets.UTF_8).size < MEGABYTE) {
                        currentLog.shareAsText(context)
                    } else {
                        Toast.makeText(context, R.string.text_size_hint, Toast.LENGTH_LONG).show()
                    }
                }
                POSITION_SHARE_LOG_FILE -> MBLoggerKit.loadCurrentLog().shareAsFile(context, "${SimpleDateFormat(LOG_PATTERN, Locale.getDefault()).format(Date(System.currentTimeMillis()))}$FILE_EXTENSION_LOG")
            }
            dialog.dismiss()
            shaker?.continueShaking()
        }.setOnCancelListener {
            shaker?.continueShaking()
        }.show()
    }

    private fun showLog(callingContext: Activity) {
        val fragmentManager: FragmentManager? = (callingContext as? FragmentActivity)?.supportFragmentManager
        fragmentManager?.let {
            LogDialogFragment.show(it, order)
        } ?: Toast.makeText(callingContext, "SupportFragmentManager is required", Toast.LENGTH_SHORT).show()
    }

    private fun skipShake(shaker: ShakeProducer) {
        shaker.continueShaking()
    }

    override fun onActivityPaused(activity: Activity) {
        context = null
    }

    override fun onActivityResumed(activity: Activity) {
        context = activity
    }

    override fun onActivityStarted(activity: Activity) = Unit

    override fun onActivityDestroyed(activity: Activity) = Unit

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

    override fun onActivityStopped(activity: Activity) = Unit

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit

    enum class Order {
        CHRONOLOGICAL_ASCENDING,
        CHRONOLOGICAL_DESCENDING
    }
}
