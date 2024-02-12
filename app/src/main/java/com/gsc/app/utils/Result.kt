package com.gsc.app.utils

/**
 * Generic class for holding success response, error response and loading status
 */
data class Result<out T>(val status: Status, val data: T?, val message: String) {

    enum class Status {
        SUCCESS,
        ERROR
    }

    companion object {
        fun <T> success(data: T): Result<T> {
            return Result(Status.SUCCESS, data, "")
        }

        fun <T> error(message: String): Result<T> {
            return Result(Status.ERROR, null, message)
        }
    }

    override fun toString(): String {
        return "Result(status=$status, data=$data, message=$message)"
    }
}