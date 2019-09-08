package com.daimler.mbmobilesdk.support

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.app.UserLocale
import com.daimler.mbmobilesdk.utils.extensions.toCacDateTimeString
import com.daimler.mbingresskit.common.User
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkObject
import io.mockk.unmockkAll
import org.junit.After
import java.util.*

class SupportUtilsTest {

    private val correctDate = Date()
    private val correctSubject = "[App Family][Me App Family][DE]"
    private val correctBody = "question: \"Test\"\nbotAnswer: \"None\"\nvin: \"1234\"\nuserID: \"123\"\n" +
            "market: \"DE\"\nlanguage: \"de\"\nsessionID: \"999\"\noccurredAt: \"${correctDate.toCacDateTimeString()}\"\n" +
            "appName: \"Me App Family\"\nappVersion: \"V1\"\n" +
            "deviceName: \"null:null (null)\"\n" +
            "os: \"Android null\"\n"

    @MockK private lateinit var mockContext: Context
    @MockK private lateinit var mockUser: User

    @Before
    fun init() {
        MockKAnnotations.init(this)

        // Preparation for MBMobileSDK
        mockkObject(MBMobileSDK)
        every { MBMobileSDK.userLocale() } returns UserLocale("DE", "de")

        // Preparation for getAppName
        val applicationInfo = ApplicationInfo()
        applicationInfo.labelRes = 13
        val packageInfo = PackageInfo()
        packageInfo.versionName = "V1"

        every { mockContext.getString(13) } returns "Me App Family"
        every { mockContext.applicationInfo } returns applicationInfo
        every { mockContext.packageName } returns "test"
        every { mockContext.packageManager.getPackageInfo("test", 0) } returns packageInfo

        // Preparation for User
        every { mockUser.ciamId } returns "123"
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun whenGetMailSubject_shouldReturnCorrectValue() {
        assertThat(SupportUtils.getMailSubject(mockContext)).isEqualTo(correctSubject)
    }

    @Test
    fun whenGetMailBody_shouldReturnCorrectValue() {
        assertThat(SupportUtils.getMailBody(mockContext, "Test", "None", "1234", correctDate, mockUser, "999")).isEqualTo(correctBody)
    }
}