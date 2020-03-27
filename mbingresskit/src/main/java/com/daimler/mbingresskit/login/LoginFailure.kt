package com.daimler.mbingresskit.login

import com.daimler.mbnetworkkit.networking.RequestError

/**
 * Describes all the possible failures and/or interuption which can occur during the login process
 * which make it impossible to authorize the user.
 */
enum class LoginFailure : RequestError {
    /**
     * Signals that the user has cancelledByUser the login process by e.g. closing the browser window
     */
    CANCELLED_BY_USER,
    /**
     * Signals that a token exchange could not be performed
     */
    UNABLE_TO_EXCHANGE_TOKEN,
    /**
     * Signals that credentials are wrong
     **/
    WRONG_CREDENTIALS,
    /**
     * Signals that authorization failed
     **/
    AUTHORIZATION_FAILED,
    /**
     * Signals that the user has started the registration process
     */
    REGISTRATION_STARTED,
    /**
     * Signals that the user has the password reset process
     */
    PASSWORD_RESET,
    /**
     * Signals that the user name (e-mail or phone number) is already assigned to a different account
     */
    USER_NAME_ALREADY_TAKEN,

    MISSING_USER_CREDENTIALS,

    LOGIN_CALLED_WHEN_ALREADY_LOGGED_IN,

    LOGIN_CALLED_WHEN_LOGIN_ALREADY_STARTED,

    AUTHORIZED_CALLED_WHEN_ALREADY_LOGGED_IN,

    AUTHORIZED_CALLED_WHEN_CLIENT_ALREADY_AUTHORIZED,

    AUTHORIZED_CALLED_WHEN_LOGIN_NOT_STARTED,

    TOKENRECEIVED_CALLED_WHEN_ALREADY_LOGGED_IN,

    TOKENRECEIVED_CALLED_WHEN_CLIENT_NOT_AUTHORIZED,

    OPERATION_NOT_REQUIRED,

    FAILED
}
