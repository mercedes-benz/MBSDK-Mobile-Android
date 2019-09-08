package com.daimler.mbmobilesdk.registration

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.daimler.mbmobilesdk.R
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileView
import com.daimler.mbmobilesdk.profile.layout.ProfileLayoutCreator
import com.daimler.mbmobilesdk.profile.views.MBProfileSectionView
import com.daimler.mbmobilesdk.profile.views.ProfileEditTextView
import kotlin.math.roundToInt

internal class RegistrationSectionsViewCreator(
    private val context: Context
) : ProfileLayoutCreator<ProfileView, LinearLayout> {

    override fun createLayoutStructure(items: List<ProfileView>): LinearLayout {
        return LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL

            val personalDataLayout = createSectionLayout(context.getString(R.string.activate_services_data_title))
            addView(personalDataLayout)

            val addressLayout = createSectionLayout(context.getString(R.string.profile_address))
            addView(addressLayout)

            val addressViews = items.filter {
                (it.view() != null).and(
                    (it.associatedField() is ProfileField.Address)
                        .or(it.associatedField() is ProfileField.AddressCity)
                        .or(it.associatedField() is ProfileField.AddressCountryCode)
                        .or(it.associatedField() is ProfileField.AddressDoorNumber)
                        .or(it.associatedField() is ProfileField.AddressFloorNumber)
                        .or(it.associatedField() is ProfileField.AddressZipCode)
                        .or(it.associatedField() is ProfileField.AddressHouseName)
                        .or(it.associatedField() is ProfileField.AddressHouseNumber)
                        .or(it.associatedField() is ProfileField.AddressLine1)
                        .or(it.associatedField() is ProfileField.AddressPostOfficeBox)
                        .or(it.associatedField() is ProfileField.AddressProvince)
                        .or(it.associatedField() is ProfileField.AddressState)
                        .or(it.associatedField() is ProfileField.AddressStreet)
                        .or(it.associatedField() is ProfileField.AddressStreetType)
                        .or(it.associatedField() is ProfileField.LanguageCode)
                )
            }.apply {
                forEach {
                    it.view()?.let { view ->
                        addressLayout.addView(view, this.last() != it && view !is ProfileEditTextView)
                    }
                }
            }

            val titleSalutationHorizontalLayout = createHorisontalLayout()

            items.filter { it.view() != null && !addressViews.contains(it) }.apply { // personal data items
                forEach {
                    when (it.associatedField()) {
                        is ProfileField.Salutation, is ProfileField.Title -> {
                            if (titleSalutationHorizontalLayout.parent == null) {
                                personalDataLayout.addView(titleSalutationHorizontalLayout)
                            }

                            it.view()?.let { view ->
                                titleSalutationHorizontalLayout.addView(view)
                                with(view.layoutParams as LinearLayout.LayoutParams) {
                                    gravity = Gravity.START
                                    width = 0
                                    weight = if (it.associatedField() is ProfileField.Salutation) 0.3f else 0.7f
                                    if (it.associatedField() is ProfileField.Salutation) {
                                        marginEnd = context.resources.getDimension(R.dimen.mb_margin_default).roundToInt()
                                    }
                                }
                            }
                        }
                        else -> it.view()?.let { view ->
                            personalDataLayout.addView(view, this.last() != it && view !is ProfileEditTextView)
                        }
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

    private fun createHorisontalLayout() = LinearLayout(context).apply {
        val margin = context.resources.getDimension(R.dimen.mb_margin_default).roundToInt()
        layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).also {
            it.setMargins(0, margin, 0, margin)
        }
    }

    private fun ViewGroup.addView(child: View, withSeparator: Boolean) {
        addView(child)
        if (withSeparator) {
            LayoutInflater.from(context).inflate(R.layout.view_profile_separator, this, true)
        }
    }
}
