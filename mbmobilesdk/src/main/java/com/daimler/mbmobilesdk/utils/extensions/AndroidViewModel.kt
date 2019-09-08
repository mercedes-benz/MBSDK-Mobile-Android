package com.daimler.mbmobilesdk.utils.extensions

import android.app.Application
import androidx.annotation.DimenRes
import androidx.lifecycle.AndroidViewModel
import com.daimler.mbmobilesdk.R
import com.daimler.mbingresskit.common.UserInputErrors
import com.daimler.mbnetworkkit.networking.HttpError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.ResponseError
import com.daimler.mbuikit.utils.extensions.getString

fun AndroidViewModel.getDimensionPixelSize(@DimenRes dimenRes: Int) =
    getApplication<Application>().resources.getDimensionPixelSize(dimenRes)

/**
 * Returns a standardized error message for the given error. This can be one of the following:
 *  1. [generalErrorNetwork] in case of a network error,
 *  2. the description of the error body in case of an [HttpError],
 *  3. [generalError] in other cases.
 */
internal fun AndroidViewModel.defaultErrorMessage(error: ResponseError<out RequestError>?): String =
    defaultErrorMessage<RequestError>(error) { generalError() }

/**
 * Returns a standardized error message if possible and calls [onCustomError] if not.
 * Errors are generated in the following way:
 *  1. [generalErrorNetwork] in case of a network error,
 *  2. the description of the error body in case of an [HttpError],
 *  3. the result of [onCustomError] if [ResponseError.requestError] is of type [T],
 *  4. [generalError] in other cases.
 */
internal inline fun <reified T : RequestError> AndroidViewModel.defaultErrorMessage(
    error: ResponseError<out RequestError>?,
    onCustomError: (T) -> String
): String =
    error?.let {
        val msg = when {
            it.networkError != null -> generalErrorNetwork()
            it.requestError == null -> generalError()
            it.requestError is HttpError -> {
                val httpError = it.requestError as? HttpError
                httpError?.description?.message ?: generalError()
            }
            else -> {
                it.getRequestError<T>()?.let(onCustomError) ?: generalError()
            }
        }
        msg
    } ?: generalError()

/**
 * Tries to parse the [error] as [UserInputErrors] and returns the description of the first
 * error element (if the description is not null and not empty). Otherwise it returns the
 * standardized error message of [defaultErrorMessage].
 */
internal fun AndroidViewModel.userInputErrorMessage(
    error: ResponseError<out RequestError>?
): String = defaultErrorMessage<UserInputErrors>(error) { errors ->
    errors.errors.find { !it.description.isNullOrBlank() }?.description ?: generalError()
}

internal fun AndroidViewModel.defaultErrorMessage(error: Throwable?) = generalError()

internal fun AndroidViewModel.generalError() = getString(R.string.general_error_msg)

internal fun AndroidViewModel.generalErrorNetwork() = getString(R.string.general_error_network_msg)

internal fun AndroidViewModel.assets() = getApplication<Application>().assets

internal fun AndroidViewModel.openFromAssets(path: String) = assets().open(path)