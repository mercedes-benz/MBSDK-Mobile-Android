package com.daimler.mbcarkit.network

import com.daimler.mbcarkit.business.model.speedalert.SpeedUnit
import com.daimler.mbcarkit.network.model.ApiAcceptAvpDriveRequest
import com.daimler.mbcarkit.network.model.ApiAccountLinkages
import com.daimler.mbcarkit.network.model.ApiAccountType
import com.daimler.mbcarkit.network.model.ApiAutoSyncConfiguration
import com.daimler.mbcarkit.network.model.ApiAutomaticSyncResponse
import com.daimler.mbcarkit.network.model.ApiAvpReservationStatus
import com.daimler.mbcarkit.network.model.ApiCommandCapabilities
import com.daimler.mbcarkit.network.model.ApiConfirmAssignmentVacRequest
import com.daimler.mbcarkit.network.model.ApiConsumption
import com.daimler.mbcarkit.network.model.ApiCustomerFenceViolations
import com.daimler.mbcarkit.network.model.ApiCustomerFences
import com.daimler.mbcarkit.network.model.ApiDeleteCustomerFenceViolationsRequest
import com.daimler.mbcarkit.network.model.ApiDeleteCustomerFencesRequest
import com.daimler.mbcarkit.network.model.ApiDeleteOnboardFencesRequest
import com.daimler.mbcarkit.network.model.ApiDeleteSpeedFenceViolationsRequest
import com.daimler.mbcarkit.network.model.ApiDeleteSpeedfencesRequest
import com.daimler.mbcarkit.network.model.ApiDesireServiceStatusRequest
import com.daimler.mbcarkit.network.model.ApiFence
import com.daimler.mbcarkit.network.model.ApiFinImageProviderResponse
import com.daimler.mbcarkit.network.model.ApiGeofenceViolation
import com.daimler.mbcarkit.network.model.ApiLicensePlate
import com.daimler.mbcarkit.network.model.ApiMerchantRequest
import com.daimler.mbcarkit.network.model.ApiMerchantResponse
import com.daimler.mbcarkit.network.model.ApiNormalizedProfileControl
import com.daimler.mbcarkit.network.model.ApiOnboardFences
import com.daimler.mbcarkit.network.model.ApiQrAssignmentRequest
import com.daimler.mbcarkit.network.model.ApiQrAssignmentResponse
import com.daimler.mbcarkit.network.model.ApiQrInvitationRequest
import com.daimler.mbcarkit.network.model.ApiResetDamageDetectionRequest
import com.daimler.mbcarkit.network.model.ApiRifability
import com.daimler.mbcarkit.network.model.ApiSendToCarCapabilities
import com.daimler.mbcarkit.network.model.ApiSendToCarRoute
import com.daimler.mbcarkit.network.model.ApiServiceResponse
import com.daimler.mbcarkit.network.model.ApiSpeedAlertViolation
import com.daimler.mbcarkit.network.model.ApiSpeedFenceViolations
import com.daimler.mbcarkit.network.model.ApiSpeedFences
import com.daimler.mbcarkit.network.model.ApiValetprotectItem
import com.daimler.mbcarkit.network.model.ApiValetprotectViolation
import com.daimler.mbcarkit.network.model.ApiVehicle
import com.daimler.mbcarkit.network.model.ApiVehicleDealersRequest
import com.daimler.mbcarkit.network.model.ApiVehicleImageResponse
import com.daimler.mbcarkit.network.model.ApiVehicleSoftwareUpdateInformation
import com.daimler.mbcarkit.network.model.ApiVehicleUsersManagement
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

internal interface VehicleApi {

    private companion object {
        private const val PATH_VERSION = "/v1"
        private const val PATH_VEHICLE = "/vehicle"
        private const val PATH_VEHICLES = "/vehicles"
        private const val PATH_USERS = "/users"
        private const val PATH_USER = "/user"
        private const val PATH_PROFILES = "/profiles"
        private const val PATH_SYNC = "/sync"
        private const val PATH_ASSIGNMENT = "/assignment"
        private const val PATH_QR_ASSIGNMENT = "/qr-assignment"
        private const val PATH_MASTERDATA = "/masterdata"
        private const val PATH_ASSIGNMENT_CODE_CONFIRMATION = "/vehicle-assignment-code-confirmation"
        private const val PATH_SERVICES = "/services"
        private const val PATH_RIF = "/rifability"
        private const val PATH_OUTLETS = "/outlets"
        private const val PATH_IMAGES = "/images"
        private const val PATH_HU_NOTIF_ROUTE = "/route"
        private const val PATH_LICENSE_PLATE = "/licenseplate"
        private const val PATH_DEALERS = "/dealers"
        private const val PATH_CONSUMPTION = "/consumption"
        private const val PATH_SPEED_ALERT = "/speedalert"
        private const val PATH_FIN_IMAGE_PROVIDER = "/static_ui_config"
        private const val PATH_TOP_VIEW_IMAGE = "/topviewimage"
        private const val PATH_GEOFENCING = "/geofencing"
        private const val PATH_FENCES = "/fences"
        private const val PATH_VIOLATIONS = "/violations"
        private const val PATH_VALETPROTECT = "/valetprotect"
        private const val PATH_PROTECTITEM = "/protectitem"
        private const val PATH_CAPABILITIES = "/capabilities"
        private const val PATH_SENDTOCAR = "/sendtocar"
        private const val PATH_COMMANDS = "/commands"
        private const val PATH_DAMAGE_DETECTION = "/damagedetection"
        private const val PATH_ACCEPT_AVP_DRIVE = "/acceptavpdrive"
        private const val PATH_CUSTOMERFENCES = "/customerfences"
        private const val PATH_ONBOARDFENCES = "/onboardfences"
        private const val PATH_SPEEDFENCES = "/speedfences"
        private const val PATH_UPGRADE = "/upgrade"
        private const val PATH_PERSONALIZATION = "/personalization"
        private const val PATH_AUTOMATIC_SYNC_SCREEN = "/automaticSyncScreen"
        private const val PATH_NORMALIZED_PROFILE_CONTROL = "/normalizedprofilecontrol"
        private const val PATH_AVP = "/avp"
        private const val PATH_RESERVATION_STATUS = "/reservationstatus"
        private const val PATH_ACCOUNTS = "/accounts"
        private const val PATH_SOFTWAREUPDATE = "/softwareupdate"

        const val PATH_PARAM_VEHICLE = "vehicle"
        private const val PATH_CIAM_ID = "self"
        const val PATH_PARAM_ID = "id"
        private const val PATH_QR_INVITATION = "qr-invitation"
        private const val PATH_AUTHORIZATION = "authorization"
        const val PATH_PARAM_AUTHORIZATION_ID = "authorizationId"

        const val HEADER_AUTHORIZATION = "Authorization"
        const val HEADER_MARKET_COUNTRY_CODE = "X-MarketCountryCode"
        const val HEADER_LOCALE = "X-Locale"

        const val QUERY_COUNTRY_CODE = "countryCode"
        const val QUERY_LOCALE = "locale"
        const val QUERY_GROUP = "group_by"
        const val QUERY_FILL_MISSING_DATA = "fillMissingData"
        const val QUERY_IDS = "id"
        const val QUERY_VIN = "vin"
        const val QUERY_FIN_OR_VIN = "FinOrVin"
        const val QUERY_UNIT = "unit"
        const val QUERY_IMAGE_KEYS = "imageKeys"
        const val QUERY_ROOF_OPEN = "roofOpen"
        const val QUERY_CENTERED = "centered"
        const val QUERY_NIGHT = "night"
        const val QUERY_ALLOW_FALLBACK_IMAGE = "fallbackImage"
        const val QUERY_BACKGROUND = "background"
        const val QUERY_AUTHORIZATION_ID = "authorizationId"
        const val QUERY_ACTIVATE_ALL = "activateAll"
        const val QUERY_RESERVATION_IDS = "reservationIds"
        const val QUERY_SERVICE_IDS = "serviceIds"
        const val QUERY_CONNECT_REDIRECT_URL = "connectRedirectURL"
        const val QUERY_VENDOR_ID = "vendorId"
        const val QUERY_ACCOUNT_TYPE = "accountType"

        const val REQUEST_VEHICLES = "$PATH_VERSION$PATH_VEHICLE/$PATH_CIAM_ID$PATH_MASTERDATA"
        const val REQUEST_ASSIGNMENT = "$PATH_VERSION$PATH_VEHICLE/{$PATH_PARAM_VEHICLE}$PATH_USER/$PATH_CIAM_ID$PATH_ASSIGNMENT"
        const val REQUEST_UNASSIGN = "$PATH_VERSION$PATH_VEHICLE/{$PATH_PARAM_VEHICLE}$PATH_USER$PATH_ASSIGNMENT"
        const val REQUEST_QR_ASSIGNMENT = "$PATH_VERSION$PATH_QR_ASSIGNMENT"
        const val REQUEST_ASSIGNMENT_CONFIRMATION = "$PATH_VERSION$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}$PATH_ASSIGNMENT_CODE_CONFIRMATION"
        const val REQUEST_SERVICES = "$PATH_VERSION$PATH_VEHICLE/{$PATH_PARAM_VEHICLE}$PATH_USER/$PATH_CIAM_ID$PATH_SERVICES"
        const val REQUEST_RIFABILITY = "$PATH_VERSION$PATH_RIF/{$PATH_PARAM_VEHICLE}"
        const val REQUEST_OUTLETS = "$PATH_VERSION$PATH_OUTLETS"
        const val REQUEST_VEHICLE_IMAGES = "$PATH_VERSION$PATH_VEHICLE/{$PATH_PARAM_VEHICLE}$PATH_IMAGES"
        const val REQUEST_SEND_TO_CAR_CAPABILITIES = "$PATH_VERSION$PATH_VEHICLE/{$PATH_PARAM_VEHICLE}$PATH_CAPABILITIES$PATH_SENDTOCAR"
        const val REQUEST_COMMAND_CAPABILITIES = "$PATH_VERSION$PATH_VEHICLE/{$PATH_PARAM_VEHICLE}$PATH_CAPABILITIES$PATH_COMMANDS"
        const val REQUEST_HU_NOTIF_ROUTE = "$PATH_VERSION$PATH_VEHICLE/{$PATH_PARAM_VEHICLE}$PATH_HU_NOTIF_ROUTE"

        const val REQUEST_USERS = "$PATH_VERSION$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}$PATH_USERS"
        const val REQUEST_CONFIGURE_AUTO_SYNC = "$PATH_VERSION$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}$PATH_PROFILES$PATH_SYNC"
        const val REQUEST_USER_UPGRADE = "$PATH_VERSION$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}$PATH_USERS/{$PATH_PARAM_AUTHORIZATION_ID}$PATH_UPGRADE"
        const val REQUEST_AUTOMATIC_SYNC = "$PATH_VERSION$PATH_VEHICLE/{$PATH_PARAM_VEHICLE}$PATH_PERSONALIZATION$PATH_AUTOMATIC_SYNC_SCREEN"
        const val REQUEST_NORMALIZED_PROFILE_CONTROL = "$PATH_VERSION$PATH_USER$PATH_PERSONALIZATION$PATH_NORMALIZED_PROFILE_CONTROL"

        const val REQUEST_LICENSE_PLATE = "$PATH_VERSION$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}$PATH_LICENSE_PLATE"
        const val REQUEST_PUT_DEALERS = "$PATH_VERSION$PATH_VEHICLE/{$PATH_PARAM_VEHICLE}$PATH_DEALERS"
        const val REQUEST_CONSUMPTION = "$PATH_VERSION$PATH_VEHICLE/{$PATH_PARAM_VEHICLE}$PATH_CONSUMPTION"
        const val REQUEST_SPEED_ALERT_VIOLATIONS = "$PATH_VERSION$PATH_SPEED_ALERT$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}$PATH_VIOLATIONS"
        const val REQUEST_SPEED_ALERT_VIOLATIONS_SINGLE =
            "$PATH_VERSION$PATH_SPEED_ALERT$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}$PATH_VIOLATIONS/{$PATH_PARAM_ID}"
        const val REQUEST_FIN_IMAGE_PROVIDER = "$PATH_VERSION$PATH_FIN_IMAGE_PROVIDER"

        const val REQUEST_GEOFENCING_FENCES = "$PATH_VERSION$PATH_GEOFENCING$PATH_FENCES"
        const val REQUEST_GEOFENCING_FENCE_SINGLE = "$PATH_VERSION$PATH_GEOFENCING$PATH_FENCES/{$PATH_PARAM_ID}"
        const val REQUEST_GEOFENCING_VEHICLE_FENCES = "$PATH_VERSION$PATH_GEOFENCING$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}$PATH_FENCES"
        const val REQUEST_GEOFENCING_VEHICLE_FENCE_SINGLE = "$PATH_VERSION$PATH_GEOFENCING$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}$PATH_FENCES/{$PATH_PARAM_ID}"
        const val REQUEST_GEOFENCING_VIOLATIONS = "$PATH_VERSION$PATH_GEOFENCING$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}$PATH_FENCES$PATH_VIOLATIONS"
        const val REQUEST_GEOFENCING_VIOLATION_SINGLE = "$PATH_VERSION$PATH_GEOFENCING$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}$PATH_FENCES$PATH_VIOLATIONS/{$PATH_PARAM_ID}"

        const val REQUEST_VALETPROTECT_ITEM = "$PATH_VERSION$PATH_VALETPROTECT$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}$PATH_PROTECTITEM"
        const val REQUEST_VALETPROTECT_VIOLATIONS = "$PATH_VERSION$PATH_VALETPROTECT$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}$PATH_VIOLATIONS"
        const val REQUEST_VALETPROTECT_VIOLATION_SINGLE = "$PATH_VERSION$PATH_VALETPROTECT$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}$PATH_VIOLATIONS/{$PATH_PARAM_ID}"
        const val REQUEST_TOP_VIEW_IMAGES = "$PATH_VERSION$PATH_VEHICLE/{$PATH_PARAM_VEHICLE}$PATH_TOP_VIEW_IMAGE"

        const val REQUEST_CREATE_QR_INVITATION = "$PATH_VERSION/$PATH_QR_INVITATION"
        const val REQUEST_DELETE_SUB_USER = "$PATH_VERSION$PATH_VEHICLE/{$PATH_PARAM_VEHICLE}$PATH_USER/$PATH_AUTHORIZATION"

        const val REQUEST_RESET_DAMAGE_DETECTION = "$PATH_VERSION$PATH_VEHICLE/{$PATH_PARAM_VEHICLE}$PATH_DAMAGE_DETECTION"

        const val REQUEST_ACCEPT_AVP_DRIVE = "$PATH_VERSION$PATH_VEHICLE/{$PATH_PARAM_VEHICLE}$PATH_ACCEPT_AVP_DRIVE"
        const val REQUEST_AVP_RESERVATION_STATUS = "$PATH_VERSION$PATH_VEHICLE/{$PATH_PARAM_VEHICLE}$PATH_AVP$PATH_RESERVATION_STATUS"

        const val REQUEST_CUSTOMERFENCE_VEHICLES = "$PATH_VERSION$PATH_CUSTOMERFENCES$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}"
        const val REQUEST_CUSTOMERFENCE_VIOLATIONS = "$PATH_VERSION$PATH_CUSTOMERFENCES$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}$PATH_VIOLATIONS"

        const val REQUEST_ONBOARDFENCE_VEHICLES = "$PATH_VERSION$PATH_ONBOARDFENCES$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}"

        const val REQUEST_SPEEDFENCE_VEHICLES = "$PATH_VERSION$PATH_SPEEDFENCES$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}"
        const val REQUEST_SPEEDFENCE_VIOLATIONS = "$PATH_VERSION$PATH_SPEEDFENCES$PATH_VEHICLES/{$PATH_PARAM_VEHICLE}$PATH_VIOLATIONS"

        const val REQUEST_ACCOUNTS = "$PATH_VERSION$PATH_VEHICLE/{$PATH_PARAM_VEHICLE}$PATH_ACCOUNTS"

        const val REQUEST_SOFTWAREUPDATES = "$PATH_VERSION$PATH_VEHICLE/{$PATH_PARAM_VEHICLE}$PATH_SOFTWAREUPDATE"
    }

    @GET(REQUEST_SOFTWAREUPDATES)
    suspend fun fetchSoftwareUpdates(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String
    ): Response<ApiVehicleSoftwareUpdateInformation>

    @GET(REQUEST_VEHICLES)
    suspend fun fetchVehicles(
        @Header(HEADER_AUTHORIZATION) jwtToken: String
    ): Response<ArrayList<ApiVehicle>>

    @POST(REQUEST_QR_ASSIGNMENT)
    suspend fun assignVehicleWithQrCode(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Query(QUERY_COUNTRY_CODE) countryCode: String?,
        @Body body: ApiQrAssignmentRequest
    ): Response<ApiQrAssignmentResponse>

    @DELETE(REQUEST_UNASSIGN)
    suspend fun unassignVehicleByVin(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String
    ): Response<Unit>

    @PUT(REQUEST_ASSIGNMENT)
    suspend fun assignVehicleByVin(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String
    ): Response<ApiRifability>

    @PUT(REQUEST_ASSIGNMENT_CONFIRMATION)
    suspend fun confirmVehicleAssignmentWithVac(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String,
        @Body body: ApiConfirmAssignmentVacRequest
    ): Response<Unit>

    @GET(REQUEST_SERVICES)
    suspend fun fetchServices(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Header(HEADER_LOCALE) localeHeader: String,
        @Path(PATH_PARAM_VEHICLE) vin: String,
        @Query(QUERY_LOCALE, encoded = true) locale: String,
        @Query(QUERY_GROUP, encoded = true) groupBy: String?,
        @Query(QUERY_FILL_MISSING_DATA, encoded = true) fillMissingData: Boolean
    ): Response<List<ApiServiceResponse>>

    @GET(REQUEST_SERVICES)
    suspend fun fetchServices(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Header(HEADER_LOCALE) localeHeader: String,
        @Path(PATH_PARAM_VEHICLE) vin: String,
        @Query(QUERY_LOCALE, encoded = true) locale: String,
        @Query(QUERY_GROUP, encoded = true) groupBy: String?,
        @Query(QUERY_IDS, encoded = true) ids: String, // IDs separated by ",": "ids=123,234,345"
        @Query(QUERY_FILL_MISSING_DATA, encoded = true) fillMissingData: Boolean
    ): Response<List<ApiServiceResponse>>

    @POST(REQUEST_SERVICES)
    suspend fun requestServiceUpdate(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String,
        @Query(QUERY_ACTIVATE_ALL) activateAll: Boolean,
        @Body body: ApiDesireServiceStatusRequest
    ): Response<Unit>

    @GET(REQUEST_RIFABILITY)
    suspend fun fetchRifability(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String
    ): Response<ApiRifability>

    @POST(REQUEST_OUTLETS)
    suspend fun fetchMerchants(
        @Header(HEADER_AUTHORIZATION) jwtToken: String?,
        @Body merchantRequest: ApiMerchantRequest
    ): Response<List<ApiMerchantResponse>>

    @GET(REQUEST_VEHICLE_IMAGES)
    suspend fun fetchVehicleImages(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String,
        @Query(QUERY_IMAGE_KEYS) imageKeys: String,
        @Query(QUERY_BACKGROUND) background: String,
        @Query(QUERY_ROOF_OPEN) roofOpen: String?,
        @Query(QUERY_CENTERED) centered: String?,
        @Query(QUERY_NIGHT) night: String?,
        @Query(QUERY_ALLOW_FALLBACK_IMAGE) fallbackImage: String?
    ): Response<List<ApiVehicleImageResponse>>

    @GET
    suspend fun fetchFile(@Url fileUrl: String): Response<ResponseBody>

    @GET(REQUEST_SEND_TO_CAR_CAPABILITIES)
    suspend fun fetchSendToCarCapabilities(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Query(QUERY_LOCALE, encoded = true) locale: String
    ): Response<ApiSendToCarCapabilities>

    @GET(REQUEST_COMMAND_CAPABILITIES)
    suspend fun fetchCommandCapabilities(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String
    ): Response<ApiCommandCapabilities>

    @POST(REQUEST_HU_NOTIF_ROUTE)
    suspend fun sendRoute(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Body route: ApiSendToCarRoute
    ): Response<Unit>

    @GET(REQUEST_USERS)
    suspend fun fetchVehicleUsers(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String
    ): Response<ApiVehicleUsersManagement>

    @PUT(REQUEST_CONFIGURE_AUTO_SYNC)
    suspend fun configureProfileAutoSync(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Body configuration: ApiAutoSyncConfiguration
    ): Response<Unit>

    @PUT(REQUEST_LICENSE_PLATE)
    suspend fun updateLicensePlate(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Header(HEADER_MARKET_COUNTRY_CODE) countryCode: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Body licensePlate: ApiLicensePlate
    ): Response<Unit>

    @PUT(REQUEST_PUT_DEALERS)
    suspend fun updateVehicleDealers(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Body vehicleDealers: ApiVehicleDealersRequest
    ): Response<Unit>

    @GET(REQUEST_CONSUMPTION)
    suspend fun fetchConsumption(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String
    ): Response<ApiConsumption>

    @GET(REQUEST_SPEED_ALERT_VIOLATIONS)
    suspend fun fetchViolations(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Query(QUERY_UNIT) unit: SpeedUnit
    ): Response<List<ApiSpeedAlertViolation>>

    @DELETE(REQUEST_SPEED_ALERT_VIOLATIONS)
    suspend fun deleteViolations(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String
    ): Response<Unit>

    @DELETE(REQUEST_SPEED_ALERT_VIOLATIONS_SINGLE)
    suspend fun deleteViolation(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Path(PATH_PARAM_ID) violationId: String
    ): Response<Unit>

    @GET(REQUEST_FIN_IMAGE_PROVIDER)
    suspend fun fetchFinImageProviderUrl(
        @Header(HEADER_AUTHORIZATION) jwtToken: String
    ): Response<ApiFinImageProviderResponse>

    //region GeofencingService
    @GET(REQUEST_GEOFENCING_FENCES)
    suspend fun fetchAllFences(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Query(QUERY_VIN) vin: String?
    ): Response<List<ApiFence>>

    @POST(REQUEST_GEOFENCING_FENCES)
    suspend fun createFence(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Query(QUERY_FIN_OR_VIN) vin: String?,
        @Body fence: ApiFence
    ): Response<ApiFence>

    @GET(REQUEST_GEOFENCING_FENCE_SINGLE)
    suspend fun fetchFenceById(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_ID) id: String
    ): Response<ApiFence>

    @PUT(REQUEST_GEOFENCING_FENCE_SINGLE)
    suspend fun updateFence(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_ID) id: String,
        @Body fence: ApiFence
    ): Response<Unit>

    @DELETE(REQUEST_GEOFENCING_FENCE_SINGLE)
    suspend fun deleteFence(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_ID) id: String
    ): Response<Unit>

    @GET(REQUEST_GEOFENCING_VEHICLE_FENCES)
    suspend fun fetchAllVehicleFences(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String
    ): Response<List<ApiFence>>

    @PUT(REQUEST_GEOFENCING_VEHICLE_FENCE_SINGLE)
    suspend fun activateVehicleFence(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String,
        @Path(PATH_PARAM_ID) id: String
    ): Response<Unit>

    @DELETE(REQUEST_GEOFENCING_VEHICLE_FENCE_SINGLE)
    suspend fun deleteVehicleFence(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String,
        @Path(PATH_PARAM_ID) id: String
    ): Response<Unit>

    @GET(REQUEST_GEOFENCING_VIOLATIONS)
    suspend fun fetchAllViolations(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String
    ): Response<List<ApiGeofenceViolation>>

    @DELETE(REQUEST_GEOFENCING_VIOLATIONS)
    suspend fun deleteAllViolations(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String
    ): Response<Unit>

    @DELETE(REQUEST_GEOFENCING_VIOLATION_SINGLE)
    suspend fun deleteGeofencingViolation(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String,
        @Path(PATH_PARAM_ID) id: String
    ): Response<Unit>
    //endregion GeofencingService

    //region Valet Protect
    @GET(REQUEST_VALETPROTECT_ITEM)
    suspend fun fetchValetprotectItem(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String,
        @Query(QUERY_UNIT) unit: String
    ): Response<ApiValetprotectItem>

    @POST(REQUEST_VALETPROTECT_ITEM)
    suspend fun createValetprotectItem(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String,
        @Body fence: ApiValetprotectItem
    ): Response<ApiValetprotectItem>

    @DELETE(REQUEST_VALETPROTECT_ITEM)
    suspend fun deleteValetprotectItem(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String
    ): Response<Unit>

    @GET(REQUEST_VALETPROTECT_VIOLATIONS)
    suspend fun fetchAllValetprotectViolations(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String,
        @Query(QUERY_UNIT) unit: String
    ): Response<List<ApiValetprotectViolation>>

    @DELETE(REQUEST_VALETPROTECT_VIOLATIONS)
    suspend fun deleteAllValetprotectViolations(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String
    ): Response<Unit>

    @GET(REQUEST_VALETPROTECT_VIOLATION_SINGLE)
    suspend fun fetchValetprotectViolation(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String,
        @Path(PATH_PARAM_ID) id: String,
        @Query(QUERY_UNIT) unit: String
    ): Response<ApiValetprotectViolation>

    @DELETE(REQUEST_VALETPROTECT_VIOLATION_SINGLE)
    suspend fun deleteValetprotectViolation(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) vin: String,
        @Path(PATH_PARAM_ID) id: String
    ): Response<Unit>
    //endregion Valet Protect

    @GET(REQUEST_TOP_VIEW_IMAGES)
    suspend fun fetchTopViewImages(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String
    ): Response<ResponseBody>

    @POST(REQUEST_CREATE_QR_INVITATION)
    suspend fun createQrInvitation(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Body qrInvitationRequest: ApiQrInvitationRequest
    ): Response<ResponseBody>

    @DELETE(REQUEST_DELETE_SUB_USER)
    suspend fun deleteSubUser(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Query(QUERY_AUTHORIZATION_ID) authorizationId: String
    ): Response<Unit>

    @PUT(REQUEST_USER_UPGRADE)
    suspend fun upgradeTemporaryUser(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Path(PATH_PARAM_AUTHORIZATION_ID) authorizationId: String
    ): Response<Unit>

    @GET(REQUEST_AUTOMATIC_SYNC)
    suspend fun fetchAutomaticSyncStatus(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String
    ): Response<ApiAutomaticSyncResponse>

    @GET(REQUEST_NORMALIZED_PROFILE_CONTROL)
    suspend fun fetchNormalizedProfileControl(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
    ): Response<ApiNormalizedProfileControl>

    @PUT(REQUEST_NORMALIZED_PROFILE_CONTROL)
    suspend fun configureNormalizedProfileControl(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Body body: ApiNormalizedProfileControl
    ): Response<Unit>

    @HTTP(method = "DELETE", path = REQUEST_RESET_DAMAGE_DETECTION, hasBody = true)
    suspend fun resetDamageDetection(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Body request: ApiResetDamageDetectionRequest
    ): Response<Unit>

    @POST(REQUEST_ACCEPT_AVP_DRIVE)
    suspend fun acceptAvpDrive(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Body body: ApiAcceptAvpDriveRequest
    ): Response<Unit>

    @GET(REQUEST_AVP_RESERVATION_STATUS)
    suspend fun fetchAvpReservationStatus(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Query(QUERY_RESERVATION_IDS) reservationIds: String // IDs separated by ",": reservationIds="123,234,345"
    ): Response<List<ApiAvpReservationStatus>>

    @GET(REQUEST_CUSTOMERFENCE_VEHICLES)
    suspend fun fetchCustomerFences(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String
    ): Response<ApiCustomerFences>

    @POST(REQUEST_CUSTOMERFENCE_VEHICLES)
    suspend fun createCustomerFences(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Body customerFences: ApiCustomerFences
    ): Response<Unit>

    @PUT(REQUEST_CUSTOMERFENCE_VEHICLES)
    suspend fun updateCustomerFences(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Body customerFences: ApiCustomerFences
    ): Response<Unit>

    @HTTP(method = "DELETE", path = REQUEST_CUSTOMERFENCE_VEHICLES, hasBody = true)
    suspend fun deleteCustomerFences(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Body body: ApiDeleteCustomerFencesRequest
    ): Response<Unit>

    @GET(REQUEST_CUSTOMERFENCE_VIOLATIONS)
    suspend fun fetchCustomerFenceViolations(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String
    ): Response<ApiCustomerFenceViolations>

    @HTTP(method = "DELETE", path = REQUEST_CUSTOMERFENCE_VIOLATIONS, hasBody = true)
    suspend fun deleteCustomerFenceViolations(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Body body: ApiDeleteCustomerFenceViolationsRequest
    ): Response<Unit>

    @GET(REQUEST_ONBOARDFENCE_VEHICLES)
    suspend fun fetchOnboardFences(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String
    ): Response<ApiOnboardFences>

    @POST(REQUEST_ONBOARDFENCE_VEHICLES)
    suspend fun createOnboardFences(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Body onboardFences: ApiOnboardFences
    ): Response<Unit>

    @PUT(REQUEST_ONBOARDFENCE_VEHICLES)
    suspend fun updateOnboardFences(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Body onboardFences: ApiOnboardFences
    ): Response<Unit>

    @HTTP(method = "DELETE", path = REQUEST_ONBOARDFENCE_VEHICLES, hasBody = true)
    suspend fun deleteOnboardFences(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Body body: ApiDeleteOnboardFencesRequest
    ): Response<Unit>

    @GET(REQUEST_SPEEDFENCE_VEHICLES)
    suspend fun fetchSpeedFences(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String
    ): Response<ApiSpeedFences>

    @POST(REQUEST_SPEEDFENCE_VEHICLES)
    suspend fun createSpeedFences(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Body speedfences: ApiSpeedFences
    ): Response<Unit>

    @PUT(REQUEST_SPEEDFENCE_VEHICLES)
    suspend fun updateSpeedFences(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Body speedfences: ApiSpeedFences
    ): Response<Unit>

    @HTTP(method = "DELETE", path = REQUEST_SPEEDFENCE_VEHICLES, hasBody = true)
    suspend fun deleteSpeedFences(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Body body: ApiDeleteSpeedfencesRequest
    ): Response<Unit>

    @GET(REQUEST_SPEEDFENCE_VIOLATIONS)
    suspend fun fetchSpeedFenceViolations(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String
    ): Response<ApiSpeedFenceViolations>

    @HTTP(method = "DELETE", path = REQUEST_SPEEDFENCE_VIOLATIONS, hasBody = true)
    suspend fun deleteSpeedFenceViolations(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Body body: ApiDeleteSpeedFenceViolationsRequest
    ): Response<Unit>

    @GET(REQUEST_ACCOUNTS)
    suspend fun fetchAccounts(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Query(QUERY_SERVICE_IDS) serviceIds: String?, // IDs separated by ",": "serviceIds=123,234,345"
        @Query(QUERY_CONNECT_REDIRECT_URL) connectRedirectUrl: String?
    ): Response<ApiAccountLinkages>

    @DELETE(REQUEST_ACCOUNTS)
    suspend fun deleteAccount(
        @Header(HEADER_AUTHORIZATION) jwtToken: String,
        @Path(PATH_PARAM_VEHICLE) finOrVin: String,
        @Query(QUERY_VENDOR_ID) vendorId: String,
        @Query(QUERY_ACCOUNT_TYPE) accountType: ApiAccountType
    ): Response<Unit>
}
