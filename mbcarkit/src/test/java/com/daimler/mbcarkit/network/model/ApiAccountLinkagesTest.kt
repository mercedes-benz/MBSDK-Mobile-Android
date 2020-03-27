package com.daimler.mbcarkit.network.model

import com.daimler.mbcarkit.utils.ApiAccountLinkageFactory
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
internal class ApiAccountLinkagesTest {

    @Test
    fun `mapping to AccountLinkages`(softly: SoftAssertions) {
        val apiGroup = ApiAccountLinkageFactory.createApiAccountLinkageGroup(name = "name")
        val apiLinkages = ApiAccountLinkages(listOf(apiGroup))
        val groups = apiLinkages.toAccountGroups()
        softly.assertThat(groups.size).isEqualTo(1)
        softly.assertThat(groups.firstOrNull()?.name).isEqualTo(apiGroup.name)
    }
}
