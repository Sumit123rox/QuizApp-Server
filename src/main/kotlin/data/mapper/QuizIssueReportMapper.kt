package com.example.data.mapper

import com.example.data.database.entity.QuizIssueReportEntity
import com.example.domain.model.IssueReport

fun IssueReport.toIssueReportEntity() = QuizIssueReportEntity(
    questionId = questionId,
    issueType = issueType,
    additionalComment = additionalComment,
    userEmail = userEmail,
    timeStamp = timeStamp
)

fun QuizIssueReportEntity.toIssueReport() = IssueReport(
    id = _id,
    questionId = questionId,
    issueType = issueType,
    additionalComment = additionalComment,
    userEmail = userEmail,
    timeStamp = timeStamp
)