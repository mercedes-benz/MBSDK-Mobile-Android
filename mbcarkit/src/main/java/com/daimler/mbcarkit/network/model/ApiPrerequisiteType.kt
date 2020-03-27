package com.daimler.mbcarkit.network.model

import com.google.gson.annotations.SerializedName

internal enum class ApiPrerequisiteType {
    @SerializedName("consent") CONSENT,
    @SerializedName("contractualAvailability") CONTRACTUAL_AVAILABILITY,
    @SerializedName("fuseBox") FUSE_BOX,
    @SerializedName("license") LICENSE,
    @SerializedName("requiredFields") REQUIRED_FIELDS,
    @SerializedName("technicalAvailability") TECHNICAL_AVAILABILITY,
    @SerializedName("trustlevel") TRUST_LEVEL,
    @SerializedName("userAgreement") USER_AGREEMENT
}
