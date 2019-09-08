package com.daimler.mbmobilesdk.tou

import android.content.Context
import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.checkParameterNotNull
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

internal class HtmlUserAgreementActivity : MBBaseViewModelActivity<HtmlUserAgreementViewModel>() {

    override fun createViewModel(): HtmlUserAgreementViewModel {
        val title = intent.getStringExtra(ARG_HTML_TITLE).orEmpty()
        val content = intent.getStringExtra(ARG_HTML_CONTENT)
        checkParameterNotNull("htmlContent", content)
        val factory = HtmlUserAgreementViewModelFactory(title, content)
        return ViewModelProviders.of(this, factory).get(HtmlUserAgreementViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.activity_html_user_agreement

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        overridePendingTransition(R.anim.slide_up, R.anim.stay)

        viewModel.closeEvent.observe(this, onCloseEvent())
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) overridePendingTransition(R.anim.stay, R.anim.slide_down)
    }

    private fun onCloseEvent() = LiveEventObserver<Unit> {
        finish()
    }

    companion object {
        private const val ARG_HTML_TITLE = "arg.html.user.agreement.title"
        private const val ARG_HTML_CONTENT = "arg.html.user.agreement.content"

        fun getStartIntent(context: Context, title: String?, htmlContent: String): Intent {
            val intent = Intent(context, HtmlUserAgreementActivity::class.java)
            intent.putExtra(ARG_HTML_TITLE, title)
            intent.putExtra(ARG_HTML_CONTENT, htmlContent)
            return intent
        }
    }
}