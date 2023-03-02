package com.example.studyapp.network.models

sealed class ResultType<T> {
    data class Error<T>(val message: String) : ResultType<T>()
    data class Ok<T>(val value: T) : ResultType<T>()
}