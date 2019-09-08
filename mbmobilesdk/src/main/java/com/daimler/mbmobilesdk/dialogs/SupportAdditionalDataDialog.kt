package com.daimler.mbmobilesdk.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.daimler.mbmobilesdk.R
import kotlinx.android.synthetic.main.dialog_support_senddatainfo.*

internal class SupportAdditionalDataDialog(private val message: String) : DialogFragment() {

    override fun onStart() {
        super.onStart()
        tv_message.text = message
        button_ok.setOnClickListener { dismiss() }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_support_senddatainfo, container, false)
    }
}