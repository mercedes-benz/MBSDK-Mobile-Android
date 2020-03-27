package com.daimler.mbmobilesdk.example.login

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.daimler.mbmobilesdk.example.BR
import com.daimler.mbmobilesdk.example.R
import com.daimler.mbmobilesdk.example.databinding.ActivityLoginBinding
import com.daimler.mbmobilesdk.example.utils.ViewModelActivity
import com.daimler.mbmobilesdk.example.utils.observe

class LoginActivity : ViewModelActivity<LoginViewModel, ActivityLoginBinding>() {

    override fun createViewModel(): LoginViewModel =
        ViewModelProvider(this, LoginViewModelFactory()).get(LoginViewModel::class.java)

    override fun getLayoutResId(): Int = R.layout.activity_login

    override fun getModelId(): Int = BR.viewModel

    override fun onBindingCreated(viewModel: LoginViewModel, binding: ActivityLoginBinding) {
        super.onBindingCreated(viewModel, binding)

        viewModel.apply {
            notRegisteredEvent.observe(this@LoginActivity) { onNotRegisteredEvent(it) }
            errorEvent.observe(this@LoginActivity) { onErrorEvent() }
            userLoggedInEvent.observe(this@LoginActivity) { onUserLoggedInEvent() }
        }
    }

    private fun onNotRegisteredEvent(user: String) {
        AlertDialog.Builder(this)
            .setMessage(String.format(getString(R.string.error_not_registered), user))
            .setPositiveButton(R.string.ok, null)
            .create()
            .show()
    }

    private fun onErrorEvent() {
        AlertDialog.Builder(this)
            .setMessage(R.string.error_general)
            .setPositiveButton(R.string.ok, null)
            .create()
            .show()
    }

    private fun onUserLoggedInEvent() {
        setResult(RESULT_OK)
        finish()
    }
}
