package com.daimler.mbmobilesdk.tou

import android.text.Html
import android.text.method.LinkMovementMethod
import androidx.lifecycle.ViewModel
import com.daimler.mbmobilesdk.utils.isNougat
import com.daimler.mbuikit.lifecycle.events.MutableLiveUnitEvent

internal class HtmlUserAgreementViewModel(
    val title: String,
    htmlContent: String
) : ViewModel() {

    val content = getHtmlString(htmlContent)

    internal val closeEvent = MutableLiveUnitEvent()

    fun getMovementMethod() = LinkMovementMethod.getInstance()

    fun onCloseClicked() {
        closeEvent.sendEvent()
    }

    private fun getHtmlString(content: String) =
        if (isNougat()) {
            Html.fromHtml(content, 0)
        } else {
            Html.fromHtml(content)
        }
}