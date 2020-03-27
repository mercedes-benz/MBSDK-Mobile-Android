package com.daimler.mbingresskit.implementation.network

import com.daimler.mbingresskit.common.RegistrationErrors
import com.daimler.mbingresskit.common.UserInputErrors
import com.daimler.mbingresskit.implementation.network.model.ApiInputError
import com.daimler.mbingresskit.implementation.network.model.ApiInputErrors
import com.daimler.mbingresskit.implementation.network.model.ConsentApiInputErrors
import com.daimler.mbnetworkkit.networking.HttpError
import com.daimler.mbnetworkkit.networking.exception.ResponseException
import com.google.gson.Gson
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SoftAssertionsExtension::class)
class ApiErrorMapperTest {

    @Test
    fun `test mapping to UserInputErrors with correct model`() {
        val apiError = ApiInputErrors(listOf(ApiInputError("name", "description")))
        val error = ResponseException(400, null, Gson().toJson(apiError))
        val inputError = mapDefaultInputError(error)
        Assertions.assertNotNull(inputError.requestError)
        Assertions.assertEquals(UserInputErrors::class, inputError.requestError!!::class)
    }

    @Test
    fun `test mapping to UserInputErrors with no model`() {
        val error = ResponseException(400, null, null)
        val inputError = mapDefaultInputError(error)
        Assertions.assertNotNull(inputError.requestError)
        Assertions.assertEquals(HttpError.BadRequest::class, inputError.requestError!!::class)
    }

    @Test
    fun `test mapping to UserInputErrors with wrong response code`() {
        val apiError = ApiInputErrors(listOf(ApiInputError("name", "description")))
        val error = ResponseException(409, null, Gson().toJson(apiError))
        val inputError = mapDefaultInputError(error)
        Assertions.assertNotNull(inputError.requestError)
        Assertions.assertEquals(HttpError.Conflict::class, inputError.requestError!!::class)
    }

    @Test
    fun `test mapping to RegistrationErrors with correct model`() {
        val apiError = ConsentApiInputErrors(listOf(ApiInputError("name", "description")), true)
        val error = ResponseException(400, null, Gson().toJson(apiError))
        val inputError = mapRegistrationErrors(error)
        Assertions.assertNotNull(inputError.requestError)
        Assertions.assertEquals(RegistrationErrors::class, inputError.requestError!!::class)
    }

    @Test
    fun `test mapping to RegistrationErrors with no model`() {
        val error = ResponseException(400, null, null)
        val inputError = mapRegistrationErrors(error)
        Assertions.assertNotNull(inputError.requestError)
        Assertions.assertEquals(HttpError.BadRequest::class, inputError.requestError!!::class)
    }

    @Test
    fun `test mapping to RegistrationErrors with wrong response code`() {
        val apiError = ApiInputErrors(listOf(ApiInputError("name", "description")))
        val error = ResponseException(409, null, Gson().toJson(apiError))
        val inputError = mapRegistrationErrors(error)
        Assertions.assertNotNull(inputError.requestError)
        Assertions.assertEquals(HttpError.Conflict::class, inputError.requestError!!::class)
    }
}
