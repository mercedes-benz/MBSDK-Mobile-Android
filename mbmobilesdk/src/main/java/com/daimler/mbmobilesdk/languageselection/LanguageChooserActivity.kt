package com.daimler.mbmobilesdk.languageselection

import android.content.Context
import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.databinding.ActivityLanguageChooserBinding
import com.daimler.mbmobilesdk.languageselection.LanguageChooserActivity.Companion.ARG_SELECTED_LANGUAGE_CODE
import com.daimler.mbmobilesdk.utils.extensions.simpleTextObserver
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

/**
 * Activity that choose a list to let the user select a preferred language.
 * The selection is returned in this activity's result. No values are returned if the user
 * cancelled the selection.
 *
 * @see ARG_SELECTED_LANGUAGE_CODE
 */
class LanguageChooserActivity : MBBaseViewModelActivity<LanguageChooserViewModel>() {

    override fun getLayoutRes(): Int = R.layout.activity_language_chooser

    override fun getModelId(): Int = BR.model

    override fun createViewModel(): LanguageChooserViewModel =
        ViewModelProviders.of(
            this,
            LanguageChooserViewModelFactory(application, intent.getStringExtra(ARG_CURRENT_LANGUAGE_CODE))
        ).get(LanguageChooserViewModel::class.java)

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        overridePendingTransition(R.anim.slide_up, R.anim.stay)
        val toolbar = (binding as ActivityLanguageChooserBinding).toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        viewModel.languageSelectedEvent.observe(this, onLanguageSelected())
        viewModel.languageSelectionCancelledEvent.observe(this, onLanguageSelectionCancelled())
        viewModel.languageLoadingError.observe(this, onLanguageLoadingError())

        viewModel.loadLanguages()
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) overridePendingTransition(R.anim.stay, R.anim.slide_down)
    }

    private fun onLanguageSelected() = LiveEventObserver<String> {
        val intent = Intent()
        intent.apply {
            putExtra(ARG_SELECTED_LANGUAGE_CODE, it)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun onLanguageSelectionCancelled() = LiveEventObserver<Unit> {
        setResult(RESULT_CANCELED)
        finish()
    }

    private fun onLanguageLoadingError() = simpleTextObserver()

    companion object {
        /**
         * Bundle key that points to the language code of the language selected by the user.
         */
        const val ARG_SELECTED_LANGUAGE_CODE = "arg.language.code.selected"

        private const val ARG_CURRENT_LANGUAGE_CODE = "arg.language.code.current"

        /**
         * Returns the intent to start this activity.
         *
         * @param context context that starts this activity
         * @param currentLanguageCode the current country code of the user, or null
         */
        fun getStartIntent(context: Context, currentLanguageCode: String?): Intent {
            val intent = Intent(context, LanguageChooserActivity::class.java)
            intent.putExtra(ARG_CURRENT_LANGUAGE_CODE, currentLanguageCode)
            return intent
        }
    }
}