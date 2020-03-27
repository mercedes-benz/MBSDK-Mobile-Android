package com.daimler.mbnetworkkit

import com.daimler.mbnetworkkit.networking.HttpError
import com.daimler.mbnetworkkit.networking.RequestError
import com.daimler.mbnetworkkit.networking.defaultErrorMapping
import com.daimler.mbnetworkkit.networking.exception.ResponseException
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.junit.Assert.assertEquals
import org.junit.Test

class ErrorMappingTest {

    @Test
    fun testDefaultHttpErrorMapping() {
        val error2Body = "test"
        val error3Body = "{\"description\":\"test1234\"}"
        val error3Msg = "test1234"

        val error = defaultErrorMapping(ResponseException(400, null, null))
        val error2 = defaultErrorMapping(ResponseException(400, null, error2Body))
        val error3 = defaultErrorMapping(ResponseException(400, null, error3Body))

        assertEquals(null, (error.requestError as HttpError).description.message)
        assertEquals(null, (error.requestError as HttpError).description.rawError)

        assertEquals(null, (error2.requestError as HttpError).description.message)
        assertEquals(error2Body, (error2.requestError as HttpError).description.rawError)

        assertEquals(error3Msg, (error3.requestError as HttpError).description.message)
        assertEquals(error3Body, (error3.requestError as HttpError).description.rawError)
    }

    @Test
    fun testCustomErrorMapping() {
        val error1 = TestError("field1", "description1")
        val error2 = TestError("field2", "description2")
        val errors = TestErrors(listOf(error1, error2))
        val errorsJson = Gson().toJson(errors)

        val errorResponse = ResponseException(400, null, errorsJson)
        val mappedError = defaultErrorMapping(errorResponse, TestErrors::class.java)

        assert(mappedError.requestError is TestErrors)

        val testErrors = mappedError.requestError as TestErrors
        assertEquals(testErrors.errors.size, 2)

        assertEquals(testErrors.errors[0], error1)
        assertEquals(testErrors.errors[1], error2)
    }

    private data class TestErrors(
        @SerializedName("errors") val errors: List<TestError>
    ) : RequestError

    private data class TestError(
        @SerializedName("fieldName") val fieldName: String?,
        @SerializedName("description") val description: String?
    )
}
