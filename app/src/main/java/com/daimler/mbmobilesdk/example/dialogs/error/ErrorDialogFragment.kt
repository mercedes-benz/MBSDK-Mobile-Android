package com.daimler.mbmobilesdk.example.dialogs.error

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.daimler.mbmobilesdk.example.BR
import com.daimler.mbmobilesdk.example.R
import com.daimler.mbmobilesdk.example.databinding.FragmentErrorDialogBinding
import com.daimler.mbmobilesdk.example.dialogs.BaseDialogFragment

internal class ErrorDialogFragment : BaseDialogFragment<ErrorDialogViewModel, FragmentErrorDialogBinding>() {

    private var callback: Callback? = null

    override fun getLayoutRes(): Int = R.layout.fragment_error_dialog

    override fun getModelId(): Int = BR.model

    override fun createViewModel(): ErrorDialogViewModel {
        val factory = ErrorDialogViewModelFactory(
            arguments?.getString(ARG_TITLE),
            arguments?.getString(ARG_MESSAGE)
        )
        return ViewModelProvider(this, factory).get(ErrorDialogViewModel::class.java)
    }

    override fun onBindingCreated(binding: FragmentErrorDialogBinding) {
        super.onBindingCreated(binding)

        viewModel.confirmEvent.observe(this, onConfirmEvent())
    }

    override fun onDestroy() {
        super.onDestroy()
        callback = null
    }

    private fun onConfirmEvent() = Observer<Unit> {
        callback?.onConfirmed()
        dismiss()
    }

    interface Callback {

        fun onConfirmed()
    }

    class Builder {

        private var title: String? = null
        private var message: String? = null
        private var callback: Callback? = null

        fun withTitle(title: String?) = apply { this.title = title }

        fun withMessage(message: String?) = apply { this.message = message }

        fun withConfirmAction(action: () -> Unit) = apply {
            callback = object : Callback {

                override fun onConfirmed() {
                    action.invoke()
                }
            }
        }

        fun build() = newInstance(title, message).apply {
            callback = this@Builder.callback
        }
    }

    companion object {

        private const val ARG_TITLE = "arg.title"
        private const val ARG_MESSAGE = "arg.message"

        fun newInstance(title: String?, message: String?): ErrorDialogFragment {
            val fragment = ErrorDialogFragment()
            Bundle().apply {
                putString(ARG_TITLE, title)
                putString(ARG_MESSAGE, message)
                fragment.arguments = this
            }
            return fragment
        }
    }
}
