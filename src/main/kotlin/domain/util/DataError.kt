package com.example.domain.util

sealed interface DataError : Error {
    data object NotFound : DataError
    data object Unknown : DataError
    data object Validation : DataError
    data object Database : DataError
}