package com.daimler.mbingresskit

import com.daimler.mbingresskit.common.UserCredentials
import com.daimler.testutils.reflection.mockFields
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import org.junit.Test

class MBIngressKitTest {

    private val ingressKit = spyk(MBIngressKit, recordPrivateCalls = true)
    private val serviceProxy = mockk<ServiceProxy>(relaxUnitFun = true)

    @Test
    fun `verify service init logout`() {
        every { serviceProxy.startLogout() } answers { mockk() }
        MBIngressKit.mockFields(serviceProxy)
        ingressKit.logout()

        verify {
            ingressKit["checkServiceInitialized"]()
        }
    }

    @Test
    fun `verify service init login`() {
        every { serviceProxy.login(any()) } answers { mockk() }
        MBIngressKit.mockFields(serviceProxy)
        ingressKit.login(UserCredentials(USER))

        verify {
            ingressKit["checkServiceInitialized"]()
        }
    }

    @Test
    fun `verify service init refreshToken`() {
        every { serviceProxy.refreshToken() } answers { mockk() }
        MBIngressKit.mockFields(serviceProxy)
        ingressKit.refreshTokenIfRequired()

        verify {
            ingressKit["checkServiceInitialized"]()
        }
    }

    @Test
    fun `verify service init authentication service`() {
        MBIngressKit.mockFields(serviceProxy)
        ingressKit.authenticationService()

        verify {
            ingressKit["checkServiceInitializedAndGetProxy"]()
            ingressKit["checkServiceInitialized"]()
        }
    }

    @Test
    fun `verify service init verification service`() {
        MBIngressKit.mockFields(serviceProxy)
        ingressKit.verificationService()

        verify {
            ingressKit["checkServiceInitializedAndGetProxy"]()
            ingressKit["checkServiceInitialized"]()
        }
    }

    @Test
    fun `verify service init cached user`() {
        every { serviceProxy.userCache.loadUser() } answers { mockk() }
        MBIngressKit.mockFields(serviceProxy)
        ingressKit.cachedUser()

        verify {
            ingressKit["checkServiceInitialized"]()
        }
    }

    @Test
    fun `verify service init cached user image`() {
        every { serviceProxy.userCache.loadUserImage() } answers { ByteArray(0) }
        MBIngressKit.mockFields(serviceProxy)
        ingressKit.cachedUserImageBytes()

        verify {
            ingressKit["checkServiceInitialized"]()
        }
    }

    @Test
    fun `verify service init cached profile fields`() {
        every {
            serviceProxy.profileFieldsCache.loadProfileFields(
                COUNTRY,
                LOCALE
            )
        } answers { mockk() }
        MBIngressKit.mockFields(serviceProxy)
        ingressKit.cachedProfileFields(COUNTRY, LOCALE)

        verify {
            ingressKit["checkServiceInitialized"]()
        }
    }

    @Test
    fun `verify service init clear locale cache`() {
        every { serviceProxy.userCache.clear() } answers { mockk() }
        every { serviceProxy.profileFieldsCache.clear() } answers { mockk() }
        every { serviceProxy.eTagProvider.clear(any()) } answers { mockk() }
        MBIngressKit.mockFields(serviceProxy)
        ingressKit.clearLocalCache()

        verify {
            ingressKit["checkServiceInitialized"]()
        }
    }

    companion object {
        private const val USER = "user"
        private const val LOCALE = "de"
        private const val COUNTRY = "DE"
    }
}
