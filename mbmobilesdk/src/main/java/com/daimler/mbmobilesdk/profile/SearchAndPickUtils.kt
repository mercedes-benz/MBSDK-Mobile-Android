package com.daimler.mbmobilesdk.profile

import android.app.Activity
import android.content.Intent
import com.daimler.mbmobilesdk.picker.SearchAndPickActivity
import com.daimler.mbmobilesdk.utils.ifNotNull

/**
 * Returns a [Pair] (key, value) as received by [SearchAndPickActivity.ARG_SELECTED_KEY] and
 * [SearchAndPickActivity.ARG_SELECTED_VALUE] if the [resultCode] is [Activity.RESULT_OK] and
 * the values are not null.
 * Returns null otherwise.
 * Call this method with the results received in onActivityResult().
 */
internal fun searchAndPickResultReceived(resultCode: Int, data: Intent?): Pair<String, String>? {
    return if (resultCode == Activity.RESULT_OK) {
        val key = data?.getStringExtra(SearchAndPickActivity.ARG_SELECTED_KEY)
        val value = data?.getStringExtra(SearchAndPickActivity.ARG_SELECTED_VALUE)
        ifNotNull(key, value) { k, v ->
            Pair(k, v)
        }
    } else {
        null
    }
}