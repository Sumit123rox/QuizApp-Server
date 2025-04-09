package com.example.presentation.routes.issue_report

import com.example.domain.repository.QuizIssueReportRepository
import com.example.domain.util.onFailure
import com.example.domain.util.onSuccess
import com.example.presentation.util.respondWithError
import io.ktor.http.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getAllQuizIssueReports(
    repository: QuizIssueReportRepository,
) {
    get<IssueReportRoutesPath> {
        repository.getAllIssueReports()
            .onSuccess { reports ->
                if (reports.isEmpty())
                    call.respond(message = "No reports found", status = HttpStatusCode.NotFound)
                else
                    call.respond(message = reports, status = HttpStatusCode.OK)
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}