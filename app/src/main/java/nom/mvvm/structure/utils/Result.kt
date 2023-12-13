package nom.mvvm.structure.utils

/**
 * Generic class for holding success response, error response and loading status
 */
sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}