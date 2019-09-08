package com.daimler.mbmobilesdk.preferences.jumio

import com.daimler.mbcommonkit.preferences.Preference

interface JumioSettings {

    /**
     * Contains true if jumio terms and conditions were accepted
     */
    val jumioTermsAccepted: Preference<Boolean>
}