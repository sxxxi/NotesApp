package ca.sxxxi.notes.model

sealed class HttpStatus {
    data class Ok<T>(val data: T): HttpStatus()
    data class Error(val code: Int): HttpStatus()
}