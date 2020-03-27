package com.daimler.mbingresskit.login

import com.daimler.mbingresskit.common.Countries
import com.daimler.mbingresskit.common.LoginUser
import com.daimler.mbingresskit.common.ProfileFieldsData
import com.daimler.mbingresskit.common.RegistrationUser
import com.daimler.mbingresskit.common.RegistrationUserAgreementUpdates
import com.daimler.mbingresskit.common.UnitPreferences
import com.daimler.mbingresskit.common.User
import com.daimler.mbingresskit.common.UserAdaptionValues
import com.daimler.mbingresskit.common.UserBiometricState
import com.daimler.mbingresskit.common.UserBodyHeight
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbnetworkkit.task.FutureTask

interface UserService {

    /**
     * Sends a TAN for the passed username, which is required to perform a successful login.
     *
     * @param userName The value that should be passed depends on the registration process.
     *                          This could be the users mail or phone.
     *
     * @return If the requests completes, the TAN is send to the user by mail or sms.
     *                          If the request fails, a [NetworkError] or [HttpError] is passed.
     */
    fun sendTan(userName: String, countryCode: String): FutureTask<LoginUser, ResponseError<out RequestError>?>

    /**
     * Sends a TAN for the passed username, which is required to perform a successful login.
     *
     * @param userName The value that should be passed depends on the registration process.
     *                          This could be the users mail or phone.
     *
     * @return If the requests completes, the TAN is send to the user by mail or sms.
     *                          If the request fails, a [NetworkError] or [HttpError] is passed.
     */
    @Deprecated("Use sendTan() instead", ReplaceWith("sendTan"))
    fun sendPin(userName: String, countryCode: String): FutureTask<LoginUser, ResponseError<out RequestError>?>

    /**
     * Loads the current user data.
     */
    fun loadUser(jwtToken: String): FutureTask<User, ResponseError<out RequestError>?>

    /**
     * Creates the user with passed data. After registration was successfull, the user can now simply
     * login by sending a pin. For registration, only mail or phone should be set.
     *
     *
     * @param useMailAsUsername A flag indicating whether [User.email] shall be used as username, otherwise [User.mobilePhone] will be used.
     * @param user The user to create.
     * @param agreementUpdates user's consent for agreements necessary for registration;
     * NOTE: this argument will become mandatory in a future version
     *
     * @return If registration was completed, the registered user will be returned in completion
     *                          block. If the registration fails, the failure block will be called. If there
     *                          was no network available, a [NetworkError] will be passed in Failure block.
     *                          If response contains 400 as Response-Code, a [RegistrationErrors] object
     *                          is passed. A [HttpError] is passed, if non of the previous errors occurred.
     */
    fun createUser(
        useMailAsUsername: Boolean,
        user: User
    ): FutureTask<RegistrationUser, ResponseError<out RequestError>?>

    /**
     * Same as [createUser], but requires an additional user consent.
     *
     * @param useMailAsUsername A flag indicating whether [User.email] shall be used as username, otherwise [User.mobilePhone] will be used.
     * @param user The user to create.
     * @param consent user's consent for agreements necessary for registration
     */
    fun createUserWithConsent(
        useMailAsUsername: Boolean,
        user: User,
        consent: RegistrationUserAgreementUpdates
    ): FutureTask<RegistrationUser, ResponseError<out RequestError>?>

    /**
     * Updates the remote user object with the data of the given user object.
     *
     * @param jwtToken the current user token
     * @param user the user object to put
     * @return A [FutureTask] that contains the updated [User] object on its completion block
     * and one of the following objects in its failure block if the request failed.
     *  - [NetworkError]
     *  - [HttpError]
     *  - [UserInputErrors]
     */
    fun updateUser(jwtToken: String, user: User): FutureTask<User, ResponseError<out RequestError>?>

    /**
     * Deletes the related user. After completed, the user must be logged out because its token
     * will not be valid anymore.
     */
    fun deleteUser(jwtToken: String, user: User): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Updates user profile pic
     *
     * @param bitmapByteArray byte array of Bitmap to send
     *
     * @param mediaType possible values "image/jpeg", "image/jpg" and "image/png"
     *
     */
    fun updateProfilePicture(jwtToken: String, bitmapByteArray: ByteArray, mediaType: String): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Returns user profile pic as a byte array
     */
    fun fetchProfilePictureBytes(jwtToken: String): FutureTask<ByteArray, ResponseError<out RequestError>?>

    /**
     * Loads a list of all supported countries.
     */
    fun fetchCountries(): FutureTask<Countries, ResponseError<out RequestError>?>

    /**
     * Sets the initial pin code for the user.
     *
     * @param jwtToken the current token of the user
     * @param pin the pin that should be set
     * @return A [FutureTask] that contains nothing in its completion block and one of the
     * following objects in its failure block, if the request failed:
     *  - [NetworkError]
     *  - [UserInputErrors] if the response code was 400
     *  - [HttpError] in any other case
     */
    fun setPin(jwtToken: String, pin: String): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Changes the current user pin.
     *
     * @param jwtToken the current token of the user
     * @param currentPin the current pin of the user
     * @param newPin the new pin that should be set as the user pin
     * @return A [FutureTask] that contains nothing in its completion block and one of the
     * following objects in its failure block, if the request failed:
     *  - [NetworkError]
     *  - [UserInputErrors] if the response code was 400
     *  - [HttpError] in any other case
     */
    fun changePin(jwtToken: String, currentPin: String, newPin: String): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Deletes the current user pin.
     *
     * @param jwtToken the current token of the user
     * @param currentPin the current user pin
     * @return A [FutureTask] that contains nothing in its completion block and one of the
     * following objects in its failure block, if the request failed:
     *  - [NetworkError]
     *  - [UserInputErrors] if the response code was 400
     *  - [HttpError] in any other case
     */
    fun deletePin(jwtToken: String, currentPin: String): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Resets the current user pin.
     *
     * @param jwtToken the current token of the user
     * @return A [FutureTask] that contains nothing in its completion block and one of the
     * following objects in its failure block, if the request failed:
     *  - [NetworkError]
     *  - [UserInputErrors] if the response code was 400
     *  - [HttpError] in any other case
     */
    fun resetPin(jwtToken: String): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Sends the state for the biometric activation.
     *
     * @param jwtToken the current token of the user
     * @param countryCode the countryCode of the user
     * @param currentPin the current user pin; only required if [state] is [UserBiometricState.ENABLED]
     * @param state the new state
     */
    fun sendBiometricActivation(
        jwtToken: String,
        countryCode: String,
        state: UserBiometricState,
        currentPin: String?
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Updates the user unit preferences.
     *
     * @param jwtToken the current token of the user
     * @param unitPreferences a collection of unit preferences to be saved
     */
    fun updateUnitPreferences(
        jwtToken: String,
        unitPreferences: UnitPreferences
    ): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Updates the user's adaption values.
     *
     * @param jwtToken the current token of the user
     * @param bodyHeight the body height parameters of the user
     */
    @Deprecated("Use updateUserAdaptionValues(token, adaptionValues) instead.")
    fun updateAdaptionValues(jwtToken: String, bodyHeight: UserBodyHeight): FutureTask<Unit, ResponseError<out RequestError>?>

    /**
     * Updates the user's adaption values.
     *
     * @param jwtToken the current token of the user
     * @param userAdaptionValues the adaption values
     */
    fun updateAdaptionValues(jwtToken: String, userAdaptionValues: UserAdaptionValues): FutureTask<Unit, ResponseError<out RequestError>?>

    fun fetchProfileFields(countryCode: String, locale: String? = null): FutureTask<ProfileFieldsData, ResponseError<out RequestError>?>

    /**
     * Registers a scanReference for the current user.
     */
    fun verifyUser(
        jwtToken: String,
        scanReference: String
    ): FutureTask<Unit, ResponseError<out RequestError>?>
}
