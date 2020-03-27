package com.daimler.mbmobilesdk.example.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel

internal abstract class BaseDialogFragment<Model : ViewModel, Binding : ViewDataBinding> : DialogFragment() {

    protected lateinit var viewModel: Model
    protected lateinit var binding: Binding

    protected abstract fun getLayoutRes(): Int

    protected abstract fun getModelId(): Int

    protected abstract fun createViewModel(): Model

    protected open fun onBindingCreated(binding: Binding) = Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = createViewModel()
        return DataBindingUtil.inflate<Binding>(inflater, getLayoutRes(), container, false).apply {
            lifecycleOwner = this@BaseDialogFragment
            setVariable(getModelId(), viewModel)
            binding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBindingCreated(binding)
    }
}
