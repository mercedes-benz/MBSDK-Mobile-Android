package com.daimler.mbmobilesdk.units

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.RadioButton
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.databinding.ViewUnitPreferenceGroupBinding
import com.daimler.mbuikit.widgets.radiobuttons.MBRadioButton
import kotlin.properties.Delegates

class UnitPreferenceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val binding: ViewUnitPreferenceGroupBinding =
        ViewUnitPreferenceGroupBinding.inflate(LayoutInflater.from(context), this, true)

    var title by Delegates.observable<CharSequence>("") { _, _, newValue ->
        binding.headerText.text = newValue
    }

    var values by Delegates.observable<List<CharSequence>>(emptyList()) { _, _, newValue ->
        binding.buttonsContainer.removeAllViews()

        newValue.forEachIndexed { index, title ->
            val radioButton = MBRadioButton(context, attrs).apply {
                layoutParams = LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                text = title
                setPadding(
                    resources.getDimensionPixelSize(R.dimen.mb_margin_default),
                    0,
                    resources.getDimensionPixelSize(R.dimen.mb_margin_large),
                    0
                )
                setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        val oldValue = selectedIndex
                        selectedIndex = index
                        onSelectedIndexChange?.invoke(oldValue, selectedIndex)
                    }
                }
                isClickable = true
            }
            binding.buttonsContainer.addView(radioButton)
        }
    }

    var selectedIndex by Delegates.observable(-1) { _, _, newValue ->
        for (i in 0 until binding.buttonsContainer.childCount) {
            (binding.buttonsContainer.getChildAt(i) as RadioButton).isChecked = i == newValue
        }
    }

    var onSelectedIndexChange: ((oldValue: Int, newValue: Int) -> Unit)? = null

    init {
        orientation = LinearLayout.VERTICAL
    }
}