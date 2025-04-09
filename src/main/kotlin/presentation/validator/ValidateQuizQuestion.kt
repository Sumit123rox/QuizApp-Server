package com.example.presentation.validator

import com.example.domain.model.QuizQuestion
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.validateQuizQuestion() {
    validate<QuizQuestion> { question ->
        when {
            question.question.isBlank() || question.question.length < 5 -> {
                ValidationResult.Invalid("Question must  be at least 5 characters long and not empty")
            }

            question.correctAnswer.isBlank() -> {
                ValidationResult.Invalid("Correct Answer cannot be Empty")
            }

            question.incorrectAnswers.isEmpty() -> {
                ValidationResult.Invalid("There must be at least one incorrect answer")
            }

            question.incorrectAnswers.any { it.isBlank() } -> {
                ValidationResult.Invalid("Incorrect Answer cannot be Empty")
            }

            question.explanation.isBlank() -> {
                ValidationResult.Invalid("Explanation cannot be Empty")
            }

            question.topicCode <= 0 -> {
                ValidationResult.Invalid("Topic Code must be greater than 0")
            }

            else -> ValidationResult.Valid
        }
    }
}