package nom.mvvm.structure.network.repository

import nom.mvvm.structure.network.model.Error
import nom.mvvm.structure.utils.extensions.ifNullOrBlank
import retrofit2.Response
import nom.mvvm.structure.utils.Result

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