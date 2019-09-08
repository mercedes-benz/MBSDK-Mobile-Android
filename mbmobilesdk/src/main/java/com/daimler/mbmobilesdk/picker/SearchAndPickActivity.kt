package com.daimler.mbmobilesdk.picker

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.checkParameterNotNull
import com.daimler.mbmobilesdk.utils.checkParameterType
import com.daimler.mbuikit.components.activities.MBBaseViewModelActivity
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.daimler.mbuikit.utils.extensions.hideKeyboard
import kotlinx.android.synthetic.main.activity_search_and_pick.edit_search
import kotlinx.android.synthetic.main.activity_search_and_pick.toolbar

internal class SearchAndPickActivity : MBBaseViewModelActivity<SearchAndPickViewModel>() {

    override fun createViewModel(): SearchAndPickViewModel {
        val title = intent.getStringExtra(ARG_TITLE).orEmpty()
        val values = intent.getSerializableExtra(ARG_VALUES)
        val initial = intent.getStringExtra(ARG_INITIAL)
        checkParameterNotNull("values", values)
        checkParameterType<HashMap<String, String>>("values", values)

        @Suppress("UNCHECKED_CAST")
        val factory = SearchAndPickViewModelFactory(
            application,
            title,
            values as HashMap<String, String>,
            initial
        )
        return ViewModelProviders.of(this, factory).get(SearchAndPickViewModel::class.java)
    }

    override fun getLayoutRes(): Int = R.layout.activity_search_and_pick

    override fun getModelId(): Int = BR.model

    override fun onBindingCreated(binding: ViewDataBinding) {
        super.onBindingCreated(binding)

        overridePendingTransition(R.anim.slide_up, R.anim.stay)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        viewModel.valueSelectedEvent.observe(this, onValuesSelected())
        viewModel.cancelEvent.observe(this, onCancel())
        viewModel.clearSearchEvent.observe(this, onClearSearch())
    }

    override fun onPause() {
        super.onPause()
        if (isFinishing) overridePendingTransition(R.anim.stay, R.anim.slide_down)
    }

    private fun onValuesSelected() =
        LiveEventObserver<Pair<String, String>> {
            hideKeyboard()
            val intent = Intent().apply {
                putExtra(ARG_SELECTED_KEY, it.first)
                putExtra(ARG_SELECTED_VALUE, it.second)
            }
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    private fun onCancel() = LiveEventObserver<Unit> {
        hideKeyboard()
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    private fun onClearSearch() = LiveEventObserver<Unit> {
        edit_search.text.clear()
    }

    companion object {
        private const val ARG_TITLE = "arg.search.pick.title"
        private const val ARG_VALUES = "arg.search.pick.values"
        private const val ARG_INITIAL = "arg.search.pick.values.initial"

        const val ARG_SELECTED_KEY = "arg.search.pick.result.key"
        const val ARG_SELECTED_VALUE = "arg.search.pick.result.value"

        fun getStartIntent(
            context: Context,
            title: String,
            values: HashMap<String, String>,
            initialSelection: String?
        ): Intent {
            return Intent(context, SearchAndPickActivity::class.java).apply {
                putExtra(ARG_TITLE, title)
                putExtra(ARG_VALUES, values)
                putExtra(ARG_INITIAL, initialSelection)
            }
        }
    }
}