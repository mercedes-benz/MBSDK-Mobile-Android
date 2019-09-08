package com.daimler.mbmobilesdk.stageselector

import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.app.Endpoint
import com.daimler.mbuikit.components.activities.MBBaseToolbarActivity
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

internal class StageSelectorActivity : MBBaseToolbarActivity<StageSelectorViewModel>() {

    override val buttonMode: Int = BUTTON_CLOSE

    override val toolbarTitle: String
        get() = getString(R.string.stage_selector_title)

    override fun createViewModel(): StageSelectorViewModel =
        ViewModelProviders.of(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        ).get(StageSelectorViewModel::class.java)

    override fun getContentLayoutRes(): Int = R.layout.activity_stage_selector

    override fun getContentModelId(): Int = BR.model

    override fun onContentBindingCreated(binding: ViewDataBinding) {
        super.onContentBindingCreated(binding)
        viewModel.endpointSelectedEvent.observe(this, onStageSelected())
    }

    private fun onStageSelected() = LiveEventObserver<Endpoint> {
        setEndpointResult(it)
    }

    private fun setEndpointResult(endpoint: Endpoint) {
        Intent().apply {
            putExtra(ARG_SELECTED_REGION, endpoint.region)
            putExtra(ARG_SELECTED_STAGE, endpoint.stage)
            setResult(RESULT_OK, this)
        }
    }

    companion object {

        const val ARG_SELECTED_REGION = "arg.result.stage.selector.region"
        const val ARG_SELECTED_STAGE = "arg.result.stage.selector.stage"
    }
}