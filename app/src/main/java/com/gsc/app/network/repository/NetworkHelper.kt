package com.gsc.app.network.repository

import com.gsc.app.network.model.Error
import com.gsc.app.utils.extensions.common.ifNullOrBlank
import retrofit2.Response
import com.gsc.app.utils.Result

object NetworkHelper {
    suspend fun <T> getResponse(
        request: suspend () -> Response<T>,
        defaultErrorMessage: String
    ): Result<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                result.body()?.let {
                    Result.success(it)
                } ?: Result.error("Response body is null")
            } else {
                val errorResponse = Error(result.code(), result.message())
                Result.error("${errorResponse.status_code} ".trim() + errorResponse.status_message.ifNullOrBlank { defaultErrorMessage })
            }
        } catch (e: Throwable) {
            Result.error(e.message.ifNullOrBlank { defaultErrorMessage })
        }
    }
}