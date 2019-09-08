package com.daimler.mbmobilesdk.vehicleassignment

import android.content.Context
import android.content.Intent
import androidx.databinding.ViewDataBinding
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.utils.extensions.createAndroidViewModel
import com.daimler.mbuikit.components.activities.MBBaseToolbarActivity
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

class AssignLegacyActivity : MBBaseToolbarActivity<AssignLegacyViewModel>() {

    override val toolbarTitle: String by lazy { getString(R.string.assign_no_connect_title) }

    override val buttonMode: Int = BUTTON_CLOSE

    override fun createViewModel(): AssignLegacyViewModel = createAndroidViewModel()

    override fun getContentLayoutRes(): Int = R.layout.activity_assign_legacy

    override fun getContentModelId(): Int = BR.model

    override fun onContentBindingCreated(binding: ViewDataBinding) {
        viewModel.closeScreenEvent.observe(this, onCloseScreenEvent())
    }

    private fun onCloseScreenEvent() = LiveEventObserver<Unit> {
        finish()
    }

    companion object {
        fun getStartIntent(context: Context) = Intent(context, AssignLegacyActivity::class.java)
    }
}
