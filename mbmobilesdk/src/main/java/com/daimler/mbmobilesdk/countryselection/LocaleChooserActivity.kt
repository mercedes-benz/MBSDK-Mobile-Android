package com.daimler.mbmobilesdk.countryselection

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.countryselection.LocaleChooserActivity.Companion.ARG_SELECTED_CODE
import com.daimler.mbmobilesdk.countryselection.LocaleChooserActivity.Companion.ARG_SELECTED_COUNTRY
import com.daimler.mbmobilesdk.databinding.ActivityLocaleChooserBinding
import com.daimler.mbmobilesdk.utils.extensions.simpleTextObserver
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

/**
 * Activity that choose a list to let the user select a country.
 * The selection is returned in this activity's result. No values are returned if the user
 * cancelled the selection.
 *
 * @see ARG_SELECTED_COUNTRY
 * @see ARG_SELECTED_CODE
 */
class LocaleChooserActivity : MBBaseViewModelActivity<LocaleChooserViewModel>() {

    private lateinit var dialog: AlertDialog

    override fun getLayoutRes(): Int = R.layout.activity_locale_chooser

    override fun getModelId(): Int = BR.model

    override fun createViewModel(): LocaleChooserViewModel =
        ViewModelProviders.of(
            this,
            LocaleChooserViewModelFactory(application, intent.getStringExtra(ARG_CURRENT_COUNTRY_CODE))
        ).get(LocaleChooserViewModel::class.java)

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)
        overridePendingTransition(R.anim.slide_up, R.anim.stay)
        val toolbar = (binding as ActivityLocaleChooserBinding).toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        viewModel.countrySelectedEvent.observe(this, onCountrySelected())
        viewModel.countrySelectionCancelledEvent.observe(this, onCountrySelectionCancelled())
        viewModel.countryLoadingError.observe(this, onCountryLoadingError())

        viewModel.loadCountries()

        dialog = AlertDialog.Builder(this)
            .setTitle(getString(R.string.country_selection_dialog_title))
            .setMessage(getString(R.string.country_selection_dialog_message))
            .setPositiveButton(R.string.general_okay) { dialog, which ->
                dialog.dismiss()
            }
            .setNegativeButton(R.string.general_cancel) { dialog, which ->
                dialog.dismiss()
                finish()
            }
            .show()
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) overridePendingTransition(R.anim.stay, R.anim.slide_down)
    }

    override fun onDestroy() {
        if (dialog.isShowing) {
            dialog.dismiss()
        }
        super.onDestroy()
    }

    private fun onCountrySelected() = LiveEventObserver<Pair<String, String>> {
        val intent = Intent()
        intent.apply {
            putExtra(ARG_SELECTED_COUNTRY, it.first)
            putExtra(ARG_SELECTED_CODE, it.second)
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun onCountrySelectionCancelled() = LiveEventObserver<Unit> {
        setResult(RESULT_CANCELED)
        finish()
    }

    private fun onCountryLoadingError() = simpleTextObserver()

    companion object {
        /**
         * Bundle key that points to the name of the country selected by the user.
         */
        const val ARG_SELECTED_COUNTRY = "arg.country.selected"

        /**
         * Bundle key that points to the country code of the country selected by the user.
         */
        const val ARG_SELECTED_CODE = "arg.country.code.selected"

        private const val ARG_CURRENT_COUNTRY_CODE = "arg.country.code.current"

        /**
         * Returns the intent to start this activity.
         *
         * @param context context that starts this activity
         * @param currentCountryCode the current country code of the user, or null
         */
        fun getStartIntent(context: Context, currentCountryCode: String?): Intent {
            val intent = Intent(context, LocaleChooserActivity::class.java)
            intent.putExtra(ARG_CURRENT_COUNTRY_CODE, currentCountryCode)
            return intent
        }
    }
}