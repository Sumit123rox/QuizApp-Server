package com.example.presentation.routes.issue_report

import com.example.domain.model.IssueReport
import com.example.domain.repository.QuizIssueReportRepository
import com.example.domain.util.onFailure
import com.example.domain.util.onSuccess
import com.example.presentation.util.respondWithError
import io.ktor.server.request.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route

fun Route.insertIssueReport(
    repository: QuizIssueReportRepository,
) {
    post<IssueReportRoutesPath> {
        val issueReport = call.receive<IssueReport>()
        repository.insertIssueReport(issueReport)
            .onSuccess { _ ->
                call.respond(
                    message = "Issue Report Submitted Successfully",
                    status = io.ktor.http.HttpStatusCode.Created
                )
            }
            .onFailure { error ->
                respondWithError(error)
            }
    }
}