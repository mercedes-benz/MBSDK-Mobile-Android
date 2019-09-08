package com.daimler.mbmobilesdk.utils.extensions

import android.app.Activity
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.utils.extensions.toast

fun FragmentActivity.showOkayDialog(
    message: String,
    listener: (() -> Unit)? = null
) {
    showOkayDialog(0, message, object : MBDialogFragment.DialogListener {
        override fun onNegativeAction(id: Int) = Unit

        override fun onPositiveAction(id: Int) {
            listener?.invoke()
        }
    })
}

fun FragmentActivity.showOkayDialog(
    id: Int = 0,
    message: String,
    listener: MBDialogFragment.DialogListener? = null
) {
    MBDialogFragment.Builder(id).apply {
        withMessage(message)
        withPositiveButtonText(getString(R.string.general_okay))
        withListener(listener)
    }.build().show(supportFragmentManager, null)
}

fun Activity.getUpDrawable(): Drawable? {
    val upDrawable = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
    upDrawable?.setColorFilter(ContextCompat.getColor(this, R.color.mb_white), PorterDuff.Mode.SRC_ATOP)
    return upDrawable
}

internal fun AppCompatActivity.simpleTextObserver() =
    LiveEventObserver<String> { showOkayDialog(it) }

internal fun AppCompatActivity.simpleToastObserver() =
    LiveEventObserver<String> {
        toast(it)
    }

internal inline fun <reified T : AndroidViewModel> AppCompatActivity.createAndroidViewModel(): T {
    val factory =
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    return ViewModelProviders.of(this, factory).get(T::class.java)
}

internal inline fun <reified T : ViewModel> AppCompatActivity.createBasicViewModel(): T {
    return ViewModelProviders.of(this).get(T::class.java)
}

internal inline fun <reified T : ViewModel> AppCompatActivity.createViewModelWithFactory(factory: ViewModelProvider.Factory): T {
    return ViewModelProviders.of(this, factory).get(T::class.java)
}