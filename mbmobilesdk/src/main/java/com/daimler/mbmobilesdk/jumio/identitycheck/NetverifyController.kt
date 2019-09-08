package com.daimler.mbmobilesdk.jumio.identitycheck

import androidx.lifecycle.MutableLiveData
import com.auth0.android.jwt.JWT
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.jumio.JumioConfig
import com.daimler.mbmobilesdk.jumio.JumioSelectedConfig
import com.daimler.mbmobilesdk.jumio.Types
import com.daimler.mbmobilesdk.jumio.Variants
import com.daimler.mbmobilesdk.jumio.extentsions.mapToModel
import com.daimler.mbmobilesdk.jumio.extentsions.mapToSDK
import com.daimler.mbingresskit.MBIngressKit
import com.jumio.core.data.document.ScanSide
import com.jumio.nv.NetverifyDocumentData
import com.jumio.nv.NetverifySDK
import com.jumio.nv.custom.*
import com.jumio.nv.nfc.custom.NetverifyCustomNfcInterface
import com.jumio.nv.nfc.custom.NetverifyCustomNfcPresenter
import java.util.*

object NetverifyController {
    private var sideIterator: Iterator<IndexedValue<ScanSide>>? = null
    private var currentController: NetverifyCustomSDKController? = null
    private var netverifyCountries: HashMap<String, NetverifyCountry>? = null

    val scanFinishedCommand = MutableLiveData<String>()

    var currentStep = 0
        private set

    var totalCount = 0
        private set

    fun start(netverifySDK: NetverifySDK, callback: (JumioConfig) -> Unit) {
        val region = MBMobileSDK.selectedRegion()
        val stage = MBMobileSDK.selectedStage()
        val ciam = getCiam()
        netverifySDK.setUserReference("$ciam|$stage|$region")

        currentController = netverifySDK.start(object : NetverifyCustomSDKInterface {
            override fun onNetverifyFinished(data: NetverifyDocumentData?, docId: String?) {
                docId?.let {
                    scanFinishedCommand.value = it
                }
            }

            override fun onNetverifyResourcesLoaded() {
            }

            override fun onNetverifyCountriesReceived(all: HashMap<String, NetverifyCountry>?, p1: String?) {
                val config = JumioConfig(TreeMap())
                netverifyCountries = all
                all?.let { map ->
                    map.keys.forEach {
                        map[it]?.let { country ->
                            val countryConfig = HashMap<Types, List<Variants>>()
                            country.documentTypes.forEach { type ->
                                countryConfig[type.mapToModel()] = country.getDocumentVariants(type)
                                    .toTypedArray()
                                    .map { v -> v.mapToModel() }
                            }
                            config.map[country.isoCode] = countryConfig
                        }
                    }
                }
                callback.invoke(config)
            }

            override fun onNetverifyError(p0: String?, p1: String?, p2: Boolean, p3: String?) {
            }
        })
    }

    fun setConfig(config: JumioSelectedConfig) {
        val documentConfiguration = currentController?.setDocumentConfiguration(
            netverifyCountries?.get(config.isoCode),
            config.selectedType.mapToSDK(),
            config.selectedVariant.mapToSDK()
        )
        totalCount = documentConfiguration?.size ?: 0
        sideIterator = documentConfiguration?.iterator()?.withIndex()
    }

    fun hasNextSide(): Boolean = sideIterator?.hasNext() ?: false

    private fun calcScanViewRatio(scanView: NetverifyCustomScanView) {
        if (scanView.height > 0) {
            val resources = scanView.context.resources
            val displayMetrics = resources.displayMetrics
            scanView.ratio = displayMetrics.widthPixels.toFloat() / displayMetrics.heightPixels.toFloat()
        } else {
            scanView.ratio = scanView.minRatio
        }
    }

    fun scanNextSide(
        scanView: NetverifyCustomScanView,
        confirmView: NetverifyCustomConfirmationView,
        events: ScanEvents
    ): NetverifyCustomScanPresenter? {

        val nextIndexedValue = sideIterator?.next()
        val next = nextIndexedValue?.value
        currentStep = (nextIndexedValue?.index?.plus(1)) ?: 0

        val isFace = next == ScanSide.FACE
        scanView.mode = if (next == ScanSide.FACE) NetverifyCustomScanView.MODE_FACE else NetverifyCustomScanView.MODE_ID
        if (next == ScanSide.FACE) calcScanViewRatio(scanView)

        return currentController?.startScanForPart(
            next,
            scanView,
            confirmView,
            object : NetverifyCustomScanInterface {
                override fun onNetverifyScanForPartCanceled(scanSide: ScanSide?, cancelReason: NetverifyCancelReason?) {
                    events.onCanceled(cancelReason)
                }

                override fun getNetverifyCustomNfcInterface(): NetverifyCustomNfcInterface? {
                    return null
                }

                override fun onNetverifyStartNfcExtraction(p0: NetverifyCustomNfcPresenter?) {
                }

                override fun onNetverifyScanForPartFinished(scanSide: ScanSide, p1: Boolean) {
                    if (scanSide == ScanSide.FACE) {
                        events.onScanNext()
                    }
                }

                override fun onNetverifyDisplayFlipDocumentHint() {
                }

                override fun onNetverifyExtractionStarted() {
                    if (isFace) {
                        events.onUploading()
                    }
                }

                override fun onNetverifyNoUSAddressFound() {
                }

                override fun onNetverifyPresentConfirmationView() {
                    events.onConfirm()
                }

                override fun onNetverifyShowLegalAdvice(p0: String?) {
                }

                override fun onNetverifyCameraAvailable() {
                }

                override fun onNetverifyDisplayBlurHint() {
                }

                override fun onNetverifyFaceInLandscape() {
                }
            })
    }

    fun resume() = currentController?.resume()

    fun pause() = currentController?.pause()

    fun finish() = currentController?.finish()

    interface ScanEvents {
        fun onConfirm()
        fun onScanNext()
        fun onUploading()
        fun onCanceled(cancelReason: NetverifyCancelReason?)
    }

    private fun getCiam(): String? {
        val token = MBIngressKit.authenticationService().getToken().jwtToken.plainToken
        return if (token.isEmpty()) ""
        else JWT(token).getClaim("ciamid").asString()
    }
}