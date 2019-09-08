package com.daimler.mbmobilesdk.units

import android.content.Context
import android.content.Intent
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.daimler.mbmobilesdk.BR
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.databinding.ActivityUnitSettingsBinding
import com.daimler.mbmobilesdk.utils.extensions.showCentered
import com.daimler.mbuikit.components.activities.MBBaseToolbarActivity
import com.daimler.mbuikit.lifecycle.events.LiveEventObserver
import com.google.android.material.snackbar.Snackbar

internal class UnitsSettingsActivity : MBBaseToolbarActivity<UnitsSettingsViewModel>() {
    override val buttonMode = BUTTON_BACK

    override val toolbarTitle: String by lazy { getString(R.string.setting_units_title) }

    override fun createViewModel(): UnitsSettingsViewModel {
        return ViewModelProviders.of(this).get(UnitsSettingsViewModel::class.java)
    }

    override fun getContentLayoutRes() = R.layout.activity_unit_settings

    override fun getContentModelId() = BR.model

    override fun onContentBindingCreated(binding: ViewDataBinding) {
        if (binding !is ActivityUnitSettingsBinding) return

        viewModel.unitPreferenceGroups.observe(
            this, Observer {
            it.forEach { group ->
                val unitPreferenceView = UnitPreferenceView(this).apply {
                    title = group.title
                    values = group.displayValues
                    selectedIndex = group.selectedIndex.value ?: 0
                    onSelectedIndexChange = { oldValue, newValue ->
                        group.onSelectedIndexChanged(oldValue, newValue)
                    }
                }
                group.selectedIndex.observe(this, Observer {
                    unitPreferenceView.selectedIndex = it
                })
                binding.unitGroupsContainer.addView(unitPreferenceView)
            }
        })

        viewModel.errorText.observe(this, LiveEventObserver {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).showCentered()
        })
    }

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, UnitsSettingsActivity::class.java)
        }
    }
}