package com.daimler.mbingresskit.login

import com.daimler.mbingresskit.common.User
import com.daimler.mbingresskit.common.UserPinStatus
import com.daimler.mbingresskit.implementation.etag.ETagProvider
import com.daimler.mbingresskit.implementation.etag.ETags
import com.daimler.mbingresskit.persistence.UserCache
import com.daimler.mbingresskit.testutils.createUser
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.TaskObject
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class CachedUserServiceTest {

    private val delegate = mockk<UserService>()
    private val userCache = mockk<UserCache>(relaxUnitFun = true)
    private val eTagProvider = mockk<ETagProvider>(relaxUnitFun = true)

    private lateinit var subject: CachedUserService

    @BeforeEach
    fun setup() {
        every { eTagProvider.get(any()) } returns ""
        subject = CachedUserService(delegate, userCache, mockk(), mockk(), mockk(), eTagProvider)
    }

    @AfterEach
    fun clearMocks() {
        clearAllMocks()
    }

    @Test
    fun `set pin should update user in cache`() {
        val setPinTask = TaskObject<Unit, ResponseError<out RequestError>?>()
        every { delegate.setPin(any(), any()) } returns setPinTask

        val user = createUser(pinStatus = UserPinStatus.NOT_SET)
        var pinStatus = UserPinStatus.UNKNOWN
        every { userCache.updateUser(any()) } answers {
            pinStatus = (arg(0) as (User.() -> User)).invoke(user).pinStatus
        }
        subject.setPin("", "1234")
        setPinTask.complete(Unit)
        assertEquals(UserPinStatus.SET, pinStatus)
    }

    @ParameterizedTest
    @ValueSource(booleans = [true, false])
    fun `fetch profile picture should clear eTag if no cached image available`(available: Boolean) {
        val array = if (available) ByteArray(0) else null
        val task = TaskObject<ByteArray, ResponseError<out RequestError>?>()
        every { delegate.fetchProfilePictureBytes(any()) } returns task.futureTask()
        every { userCache.loadUserImage() } returns array

        subject.fetchProfilePictureBytes("")
        verify(inverse = available) { eTagProvider.set(ETags.PROFILE_PICTURE, null) }
    }

    @Test
    fun `fetch profile picture should cache image on success`() {
        val task = TaskObject<ByteArray, ResponseError<out RequestError>?>()
        every { delegate.fetchProfilePictureBytes(any()) } returns task.futureTask()
        every { userCache.loadUserImage() } returns null

        var result: ByteArray? = null
        subject.fetchProfilePictureBytes("")
            .onComplete { result = it }
        val array = ByteArray(20)
        task.complete(array)
        verify { userCache.updateUserImage(array) }
        assertEquals(array, result)
    }

    @Test
    fun `fetch profile picture should return cached image on error`() {
        val task = TaskObject<ByteArray, ResponseError<out RequestError>?>()
        val array = ByteArray(20)
        every { delegate.fetchProfilePictureBytes(any()) } returns task.futureTask()
        every { userCache.loadUserImage() } returns array

        var result: ByteArray? = null
        subject.fetchProfilePictureBytes("")
            .onComplete { result = it }
        task.fail(null)

        assertEquals(array, result)
    }
}
