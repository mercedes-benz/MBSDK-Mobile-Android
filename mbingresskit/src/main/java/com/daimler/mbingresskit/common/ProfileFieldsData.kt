package com.daimler.mbingresskit.common

data class ProfileFieldsData(
    val customerDataFields: List<CustomerDataField>,
    val fieldDependencies: List<ProfileFieldDependency>,
    val groupDependencies: List<ProfileGroupDependency>
)
