package com.daimler.mbmobilesdk.profile.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.daimler.mbmobilesdk.profile.fields.ProfileField
import com.daimler.mbmobilesdk.profile.fields.ProfileValueCallback
import com.daimler.mbmobilesdk.profile.fields.ProfileView

internal abstract class BaseProfileView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ProfileView {

    protected var callback: ProfileValueCallback? = null
        private set

    protected var profileField: ProfileField? = null
        private set

    final override fun applyCallback(callback: ProfileValueCallback) {
        this.callback = callback
    }

    final override fun applyAssociatedField(profileField: ProfileField) {
        this.profileField = profileField
    }

    final override fun associatedField(): ProfileField? = profileField

    override fun view(): View? = this

    companion object {
        const val MANDATORY_CHAR = '*'
    }
}