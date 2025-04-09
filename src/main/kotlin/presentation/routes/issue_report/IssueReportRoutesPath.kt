package com.example.presentation.routes.issue_report

import com.example.presentation.routes.quiz_question.QuizQuestionRoutesPath
import io.ktor.resources.*

@Resource("/report/issues")
class IssueReportRoutesPath {

    @Resource("/{issueId}")
    data class ById(val parent: IssueReportRoutesPath = IssueReportRoutesPath(), val issueId: String)
}