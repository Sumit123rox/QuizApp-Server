package com.example.presentation.validator

import com.example.domain.model.IssueReport
import io.ktor.server.plugins.requestvalidation.*

fun RequestValidationConfig.validateQuizIssueReport() {

    val emailRegex = Regex(pattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")

    validate<IssueReport> { issueReport ->
        when {
            issueReport.questionId.isBlank() -> {
                ValidationResult.Invalid("Question must not empty")
            }

            issueReport.issueType.isBlank() -> {
                ValidationResult.Invalid("Issue Type must not be Empty")
            }

            issueReport.timeStamp.isBlank() -> {
                ValidationResult.Invalid("Timestamp must not be Empty")
            }

            issueReport.additionalComment != null && issueReport.additionalComment.length < 5 -> {
                ValidationResult.Invalid("Additional Comment must be at least 5 characters long")
            }

            issueReport.userEmail != null && !emailRegex.matches(issueReport.userEmail) -> {
                ValidationResult.Invalid("Invalid Email Address")
            }

            else -> ValidationResult.Valid
        }
    }
}