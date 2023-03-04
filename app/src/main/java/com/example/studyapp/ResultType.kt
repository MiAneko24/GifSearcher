package com.example.studyapp

sealed class ResultType<T> {
    data class Error<T>(val message: String) : ResultType<T>()
    data class Ok<T>(val value: T) : ResultType<T>()
}