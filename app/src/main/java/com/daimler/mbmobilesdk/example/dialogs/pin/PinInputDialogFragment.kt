package com.daimler.mbmobilesdk.example.dialogs.pin

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.daimler.mbmobilesdk.example.BR
import com.daimler.mbmobilesdk.example.R
import com.daimler.mbmobilesdk.example.databinding.FragmentPinInputDialogBinding
import com.daimler.mbmobilesdk.example.dialogs.BaseDialogFragment

internal class PinInputDialogFragment : BaseDialogFragment<PinInputDialogViewModel, FragmentPinInputDialogBinding>() {

    private var callback: Callback? = null

    override fun getLayoutRes(): Int = R.layout.fragment_pin_input_dialog

    override fun getModelId(): Int = BR.model

    override fun createViewModel(): PinInputDialogViewModel =
        ViewModelProvider(this).get(PinInputDialogViewModel::class.java)

    override fun onBindingCreated(binding: FragmentPinInputDialogBinding) {
        super.onBindingCreated(binding)

        viewModel.apply {
            confirmEvent.observe(this@PinInputDialogFragment, onPinConfirmedEvent())
            cancelEvent.observe(this@PinInputDialogFragment, onCancelEvent())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        callback = null
    }

    private fun onPinConfirmedEvent() = Observer<String> {
        it?.let {
            callback?.onPinInput(it)
            dismiss()
        }
    }

    private fun onCancelEvent() = Observer<Unit> {
        it?.let {
            callback?.onCancel()
            dismiss()
        }
    }

    interface Callback {

        fun onPinInput(pin: String)

        fun onCancel()
    }

    class Builder {

        private var callback: Callback? = null

        fun withCallback(callback: Callback) = apply { this.callback = callback }

        fun build() = PinInputDialogFragment().apply {
            callback = this@Builder.callback
        }
    }
}
