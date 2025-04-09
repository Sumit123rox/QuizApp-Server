package com.example.presentation.validator

import com.example.domain.model.QuizQuestion
import com.example.domain.model.QuizTopic
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.validateQuizTopic() {
    validate<QuizTopic> { question ->
        when {
            question.name.isBlank() || question.name.length < 3 -> {
                ValidationResult.Invalid("Topic Name must  be at least 3 characters long and not empty")
            }

            question.imageUrl.isBlank() -> {
                ValidationResult.Invalid("Image URL cannot be Empty")
            }

            question.code < 0 -> {
                ValidationResult.Invalid("Topic Code must be be a whole number")
            }

            else -> ValidationResult.Valid
        }
    }
}