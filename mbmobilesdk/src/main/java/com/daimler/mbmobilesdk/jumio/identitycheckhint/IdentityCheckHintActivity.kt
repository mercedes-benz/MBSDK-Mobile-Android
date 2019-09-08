package com.daimler.mbmobilesdk.jumio.identitycheckhint

import android.app.Activity
import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.jumio.identitycheck.IdentityCheckActivity
import com.daimler.mbuikit.components.activities.MBBaseToolbarActivity
import com.daimler.mbuikit.components.dialogfragments.MBDialogFragment
import com.daimler.mbuikit.components.dialogfragments.buttons.DialogButtonOrientation
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver

class IdentityCheckHintActivity : MBBaseToolbarActivity<IdentityCheckHintViewModel>() {

    override val toolbarTitle: String by lazy { getString(R.string.service_summary_identity_check) }

    override val buttonMode: Int = BUTTON_BACK

    override fun getContentLayoutRes(): Int = R.layout.activity_identity_check_hint

    override fun getContentModelId(): Int = BR.viewModel

    override fun createViewModel(): IdentityCheckHintViewModel {
        return ViewModelProviders.of(this).get(IdentityCheckHintViewModel::class.java)
    }

    override fun onContentBindingCreated(binding: ViewDataBinding) {
        super.onContentBindingCreated(binding)

        viewModel.onStartClickEvent.observe(this, onStartClicked())
        viewModel.onLaterClickedEvent.observe(this, onLaterClicked())
    }

    private fun onStartClicked() = LiveEventObserver<Unit> {
        startIdentityCheck()
    }

    private fun startIdentityCheck() {
        startActivity(Intent(this, IdentityCheckActivity::class.java)
            .apply { addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT) })
    }

    private fun onLaterClicked() = LiveEventObserver<Unit> {
        showInfoDialog()
    }

    private fun showInfoDialog() {
        return MBDialogFragment.Builder()
            .withTitle(getString(R.string.verification_info_popup_title))
            .withMessage(getString(R.string.verification_info_popup_body))
            .withPositiveButton(getString(R.string.verification_info_start_button)) {
                startIdentityCheck()
            }
            .withNegativeButton(getString(R.string.verification_info_skip_button)) {
                finish()
            }
            .withOrientation(DialogButtonOrientation.VERTICAL)
            .build().show(supportFragmentManager, null)
    }

    companion object {
        fun Activity.getIdentityCheckIntent(): Intent {
            return Intent(this, IdentityCheckHintActivity::class.java)
        }
    }
}
