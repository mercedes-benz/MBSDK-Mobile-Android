package com.daimler.mbcarkit.implementation

import com.daimler.mbcarkit.business.VehicleImageCache
import com.daimler.mbcarkit.business.VehicleImageService
import com.daimler.mbcarkit.business.model.vehicle.image.ImageBackground
import com.daimler.mbcarkit.business.model.vehicle.image.ImageConfig
import com.daimler.mbcarkit.business.model.vehicle.image.ImageKey
import com.daimler.mbcarkit.business.model.vehicle.image.ImagePerspective
import com.daimler.mbcarkit.business.model.vehicle.image.StaticView
import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImage
import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImagePngSize
import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImageRequest
import com.daimler.mbcarkit.business.model.vehicle.image.VehicleImageType
import com.daimler.mbcarkit.utils.ResponseTaskObject
import com.daimler.mbnetworkkit.networking.NetworkError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class CachedVehicleImageServiceTest {

    private val imageCache: VehicleImageCache = mockk()
    private val vehicleImageService: VehicleImageService = mockk()

    private lateinit var vehicleImagesTask: ResponseTaskObject<List<VehicleImage>>
    private lateinit var stringTask: ResponseTaskObject<String>
    private lateinit var stringByteArrayMapTask: ResponseTaskObject<Map<String, ByteArray>>

    private lateinit var subject: CachedVehicleImageService

    @BeforeEach
    fun setup() {
        vehicleImagesTask = ResponseTaskObject()
        stringTask = ResponseTaskObject()
        stringByteArrayMapTask = ResponseTaskObject()
        subject = CachedVehicleImageService(imageCache, vehicleImageService)
        every { vehicleImageService.fetchVehicleImages(any(), any()) } returns vehicleImagesTask
        every { vehicleImageService.fetchFinImageProviderUrl(any()) } returns stringTask
        every { vehicleImageService.fetchTopViewImages(any(), any(), any(), any(), any()) } returns stringByteArrayMapTask

        every { imageCache.updateImage(any()) } returns Unit
    }

    @AfterEach
    fun clearMocks() {
        clearAllMocks()
    }

    private val imageConfig = ImageConfig(ImageBackground.WHITE, null, null, null, null)
    private val imageKey1 = ImageKey.Static(ImagePerspective.PerspectiveStatic(StaticView.FRONT_VIEW), VehicleImageType.Png(VehicleImagePngSize.SIZE_1920x1080))
    private val imageKey2 = ImageKey.Static(ImagePerspective.PerspectiveStatic(StaticView.FRONT_VIEW), VehicleImageType.Png(VehicleImagePngSize.SIZE_1920x1080))
    private val imageKey3 = ImageKey.Static(ImagePerspective.PerspectiveStatic(StaticView.FRONT_VIEW), VehicleImageType.Png(VehicleImagePngSize.SIZE_1920x1080))

    private val validImage = VehicleImage("", "", ByteArray(0))
    private val invalidImage = VehicleImage("", "", null)

    @Test
    fun `fetchVehicleImages with caching enabled and all images cached should return cached values`(softly: SoftAssertions) {
        var success: List<VehicleImage>? = null
        var failure: ResponseError<out RequestError>? = null

        every { imageCache.loadImage(any(), any()) } returns validImage

        subject.fetchVehicleImages(
            "",
            VehicleImageRequest.Builder("", imageConfig)
                .addImage(imageKey1)
                .addImage(imageKey2)
                .addImage(imageKey3).build(),
            true
        )
            .onComplete { success = it }
            .onFailure { failure = it }

        softly.assertThat(success?.size).isEqualTo(3)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchVehicleImages with caching enabled and some images cached should fetch missing values`(softly: SoftAssertions) {
        var success: List<VehicleImage>? = null
        var failure: ResponseError<out RequestError>? = null

        every { imageCache.loadImage(any(), any()) } returns validImage andThen invalidImage

        subject.fetchVehicleImages(
            "",
            VehicleImageRequest.Builder("", imageConfig)
                .addImage(imageKey1)
                .addImage(imageKey2)
                .addImage(imageKey3).build(),
            true
        )
            .onComplete { success = it }
            .onFailure { failure = it }

        vehicleImagesTask.complete(listOf(validImage, validImage))

        verify(exactly = 2) { imageCache.updateImage(any()) }

        softly.assertThat(success?.size).isEqualTo(3)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchVehicleImages with caching enabled and no images cached should return fresh values`(softly: SoftAssertions) {
        var success: List<VehicleImage>? = null
        var failure: ResponseError<out RequestError>? = null

        every { imageCache.loadImage(any(), any()) } returns invalidImage

        subject.fetchVehicleImages(
            "",
            VehicleImageRequest.Builder("", imageConfig)
                .addImage(imageKey1)
                .addImage(imageKey2)
                .addImage(imageKey3).build(),
            true
        )
            .onComplete { success = it }
            .onFailure { failure = it }

        vehicleImagesTask.complete(listOf(validImage))

        verify(exactly = 1) { imageCache.updateImage(any()) }

        softly.assertThat(success?.size).isEqualTo(1)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchVehicleImages with caching enabled and no images cached and request failure should fail task`(softly: SoftAssertions) {
        var success: List<VehicleImage>? = null
        var failure: ResponseError<out RequestError>? = null

        every { imageCache.loadImage(any(), any()) } returns invalidImage

        subject.fetchVehicleImages(
            "",
            VehicleImageRequest.Builder("", imageConfig)
                .addImage(imageKey1)
                .addImage(imageKey2)
                .addImage(imageKey3).build(),
            true
        )
            .onComplete { success = it }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        vehicleImagesTask.fail(responseError)

        softly.assertThat(success).isNull()
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `fetchVehicleImages with caching disabled should return fresh values`(softly: SoftAssertions) {
        var success: List<VehicleImage>? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchVehicleImages(
            "",
            VehicleImageRequest.Builder("", imageConfig)
                .addImage(imageKey1)
                .addImage(imageKey2)
                .addImage(imageKey3).build(),
            false
        )
            .onComplete { success = it }
            .onFailure { failure = it }

        vehicleImagesTask.complete(listOf(validImage))

        verify(exactly = 1) { imageCache.updateImage(any()) }

        softly.assertThat(success?.size).isEqualTo(1)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchVehicleImages with caching disabled and request failure should fail task`(softly: SoftAssertions) {
        var success: List<VehicleImage>? = null
        var failure: ResponseError<out RequestError>? = null

        every { imageCache.loadImage(any(), any()) } returns invalidImage

        subject.fetchVehicleImages(
            "",
            VehicleImageRequest.Builder("", imageConfig)
                .addImage(imageKey1)
                .addImage(imageKey2)
                .addImage(imageKey3).build(),
            false
        )
            .onComplete { success = it }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        vehicleImagesTask.fail(responseError)

        softly.assertThat(success).isNull()
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `fetchFinImageProviderUrl without cached url should return fresh value`(softly: SoftAssertions) {
        var success: String? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchFinImageProviderUrl("")
            .onComplete { success = it }
            .onFailure { failure = it }

        val url = "url"
        stringTask.complete(url)

        verify { vehicleImageService.fetchFinImageProviderUrl(any()) }

        softly.assertThat(success).isEqualTo(url)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchFinImageProviderUrl with cached url should return cached value`(softly: SoftAssertions) {
        var success: String? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchFinImageProviderUrl("")
            .onComplete { }
            .onFailure { }

        val url = "url"
        stringTask.complete(url)

        subject.fetchFinImageProviderUrl("")
            .onComplete { success = it }
            .onFailure { failure = it }

        verify(exactly = 1) { vehicleImageService.fetchFinImageProviderUrl(any()) }

        softly.assertThat(success).isEqualTo(url)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchFinImageProviderUrl with request failure should fail task`(softly: SoftAssertions) {
        var success: String? = null
        var failure: ResponseError<out RequestError>? = null

        subject.fetchFinImageProviderUrl("")
            .onComplete { success = it }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        stringTask.fail(responseError)

        softly.assertThat(success).isNull()
        softly.assertThat(failure).isEqualTo(responseError)
    }

    @Test
    fun `fetchTopViewImages with cached images should return cached values`(softly: SoftAssertions) {
        var success: Map<String, ByteArray>? = null
        var failure: ResponseError<out RequestError>? = null

        every { imageCache.loadImage(any(), any()) } returns validImage

        subject.fetchTopViewImages("", "", 0, 0, true)
            .onComplete { success = it }
            .onFailure { failure = it }

        softly.assertThat(success?.size).isEqualTo(TopViewImages.names.size)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchTopViewImages without cached images should return fresh values`(softly: SoftAssertions) {
        var success: Map<String, ByteArray>? = null
        var failure: ResponseError<out RequestError>? = null

        every { imageCache.loadImage(any(), any()) } returns invalidImage

        subject.fetchTopViewImages("", "", 0, 0, true)
            .onComplete { success = it }
            .onFailure { failure = it }

        stringByteArrayMapTask.complete(mapOf(Pair("", ByteArray(0))))

        verify(exactly = 1) { imageCache.updateImage(any()) }

        softly.assertThat(success?.size).isEqualTo(1)
        softly.assertThat(failure).isNull()
    }

    @Test
    fun `fetchTopViewImages with request failure should fail task`(softly: SoftAssertions) {
        var success: Map<String, ByteArray>? = null
        var failure: ResponseError<out RequestError>? = null

        every { imageCache.loadImage(any(), any()) } returns invalidImage

        subject.fetchTopViewImages("", "", 0, 0, true)
            .onComplete { success = it }
            .onFailure { failure = it }

        val responseError: ResponseError<out RequestError> = ResponseError.networkError(NetworkError.NO_CONNECTION)
        stringByteArrayMapTask.fail(responseError)

        softly.assertThat(success).isNull()
        softly.assertThat(failure).isEqualTo(responseError)
    }
}
