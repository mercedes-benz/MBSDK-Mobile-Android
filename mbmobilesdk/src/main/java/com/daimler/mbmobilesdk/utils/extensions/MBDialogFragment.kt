package com.daimler.mbmobilesdk.utils.extensions

import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment

/**
 * Sets an action that is executed on a positive button click.
 * NOTE: Any existing listener will be overwritten.
 */
internal fun MBDialogFragment.Builder.withPositiveAction(action: (() -> Unit)?): MBDialogFragment.Builder {
    return withListener(object : MBDialogFragment.DialogListener {
        override fun onNegativeAction(id: Int) = Unit

        override fun onPositiveAction(id: Int) {
            action?.invoke()
        }
    })
}

/**
 * Sets an action that is executed on a negative button click.
 * NOTE: Any existing listener will be overwritten.
 */
internal fun MBDialogFragment.Builder.withNegativeAction(action: (() -> Unit)?): MBDialogFragment.Builder {
    return withListener(object : MBDialogFragment.DialogListener {
        override fun onNegativeAction(id: Int) {
            action?.invoke()
        }

        override fun onPositiveAction(id: Int) = Unit
    })
}