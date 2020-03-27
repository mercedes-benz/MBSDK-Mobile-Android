package com.daimler.mbmobilesdk.example.utils

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class ViewModelActivity<Model : ViewModel, Binding : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var viewModel: Model
        private set

    protected lateinit var binding: Binding
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = createViewModel()
        binding = DataBindingUtil.setContentView<Binding>(this, getLayoutResId()).apply {
            lifecycleOwner = this@ViewModelActivity
            setVariable(getModelId(), viewModel)
            onBindingCreated(viewModel, this)
        }
    }

    protected abstract fun createViewModel(): Model

    protected abstract fun getLayoutResId(): Int

    protected abstract fun getModelId(): Int

    protected open fun onBindingCreated(viewModel: Model, binding: Binding) = Unit
}
