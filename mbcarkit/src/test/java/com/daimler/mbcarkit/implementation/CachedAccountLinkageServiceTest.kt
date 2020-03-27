package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.AccountLinkageService
import com.daimler.mbcarkit.business.model.accountlinkage.AccountLinkageGroup
import com.daimler.mbcarkit.business.model.accountlinkage.AccountType
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.TaskObject
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CachedAccountLinkageServiceTest {

    private val accountLinkageService: AccountLinkageService = mockk()

    private val accountLinkagesTask = TaskObject<List<AccountLinkageGroup>, ResponseError<out RequestError>?>()
    private val disconnectTask = TaskObject<Unit, ResponseError<out RequestError>?>()

    private val subject = CachedAccountLinkageService(accountLinkageService)

    @BeforeEach
    fun setup() {
        every { accountLinkageService.fetchAccounts(any(), any(), any(), any()) } returns accountLinkagesTask.futureTask()
        every { accountLinkageService.deleteAccount(any(), any(), any(), any()) } returns disconnectTask.futureTask()
    }

    @Test
    fun `fetchAccounts should return retrofit response`() {
        Assertions.assertEquals(accountLinkagesTask, subject.fetchAccounts("", "", null, null))
    }

    @Test
    fun `deleteAccount should return retrofit response`() {
        Assertions.assertEquals(disconnectTask, subject.deleteAccount("", "", "", AccountType.MUSIC))
    }
}
