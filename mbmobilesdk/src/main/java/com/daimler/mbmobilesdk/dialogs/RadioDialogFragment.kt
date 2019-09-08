package com.daimler.mbmobilesdk.dialogs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.daimler.mbmobilesdk.R
import com.daimler.mbuikit.components.dialogfragments.MBGenericDialogFragment
import com.daimler.mbuikit.components.dialogfragments.buttons.ClickablePurpose
import com.daimler.mbuikit.components.dialogfragments.buttons.DialogButtonOrientation
import com.daimler.mbuikit.components.dialogfragments.buttons.DialogClickable
import com.daimler.mbuikit.components.viewmodels.MBGenericDialogViewModel

internal class RadioDialogFragment(val callback: SelectCallback, val selections: Array<CharSequence>, var selected: Int = 0) : MBGenericDialogFragment<MBGenericDialogViewModel>() {

    override fun onNegativeAction() = callback.onCancelDialog(dialogId)

    override fun onPositiveAction() = callback.onSelectItem(dialogId, selected)

    override fun <T> onButtonClicked(button: DialogClickable<T>) {
        when (button.purpose) {
            ClickablePurpose.POSITIVE -> callback.onSelectItem(dialogId, selected)
            ClickablePurpose.NEGATIVE -> callback.onCancelDialog(dialogId)
        }
    }

    override fun onInflateBelowMessage(root: ViewGroup) {
        super.onInflateBelowMessage(root)

        activity ?: return
        val layout = LayoutInflater.from(activity!!.applicationContext).inflate(R.layout.dialog_support_select_singlechoice_listview, null)
        root.addView(layout)

        val listView = root.findViewById<View>(R.id.radio_listview) as ListView
        listView.adapter = ArrayAdapter<CharSequence>(activity!!.applicationContext, R.layout.dialog_support_select_singlechoice, selections)
        listView.setOnItemClickListener { _, _, _, id -> selected = id.toInt() }
        listView.setItemChecked(selected, true)
    }

    companion object {

        fun newInstance(
            callback: SelectCallback,
            selections: Array<CharSequence>,
            selected: Int,
            id: Int,
            title: String?,
            message: String?,
            positiveButtonText: String?,
            negativeButtonText: String?
        ): RadioDialogFragment =
                RadioDialogFragment(callback, selections, selected).apply {
                    putBundle(id, title, message, positiveButtonText, negativeButtonText, DialogButtonOrientation.HORIZONTAL)
                }
    }

    interface SelectCallback {
        fun onSelectItem(dialogId: Int, choice: Int)
        fun onCancelDialog(dialogId: Int)
    }
}
