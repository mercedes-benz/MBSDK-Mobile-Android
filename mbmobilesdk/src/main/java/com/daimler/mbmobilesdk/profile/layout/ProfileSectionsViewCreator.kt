package com.daimler.mbmobilesdk.profile.layout

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileView
import com.daimler.mbmobilesdk.profile.views.MBProfileSectionView
import kotlin.math.roundToInt

internal class ProfileSectionsViewCreator(
    private val context: Context
) : ProfileLayoutCreator<ProfileView, LinearLayout> {

    override fun createLayoutStructure(items: List<ProfileView>): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL

            val personalDataLayout = createSectionLayout(context.getString(R.string.activate_services_data_title))
            addView(personalDataLayout)

            val addressLayout = createSectionLayout(context.getString(R.string.profile_address))
            addView(addressLayout)

            val appDataLayout = createSectionLayout(context.getString(R.string.profile_app_data_title))
            addView(appDataLayout)

            val addressViews = items.filter {
                (it.view() != null) &&
                    (it.associatedField() is ProfileField.Address ||
                        it.associatedField() is ProfileField.LanguageCode)
            }.apply {
                forEach {
                    it.view()?.let { view ->
                        addressLayout.addView(view, this.last() != it)
                    }
                }
            }

            val appDataViews = items.filter {
                it.view() != null &&
                    it.associatedField() is ProfileField.MePin
            }.apply {
                forEach {
                    it.view()?.let { view ->
                        appDataLayout.addView(view, this.last() != it)
                    }
                }
            }

            items.filter {
                // personal data items
                it.view() != null &&
                    !addressViews.contains(it) &&
                    !appDataViews.contains(it)
            }.apply {
                forEach {
                    it.view()?.let { view ->
                        personalDataLayout.addView(view, this.last() != it)
                    }
                }
            }
        }
    }

    private fun createSectionLayout(title: String) = MBProfileSectionView(context).apply {
        this.title = title
        val margin = context.resources.getDimension(R.dimen.mb_margin_default).roundToInt()
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        ).also {
            it.setMargins(0, 0, 0, margin)
        }
        setPadding(margin, margin, margin, margin)
    }

    private fun ViewGroup.addView(child: View, withSeparator: Boolean) {
        addView(child)
        if (withSeparator) {
            LayoutInflater.from(context).inflate(R.layout.view_profile_separator, this, true)
        }
    }
}
