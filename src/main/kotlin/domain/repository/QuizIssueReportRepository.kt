package com.example.domain.repository

import com.example.domain.model.IssueReport
import com.example.domain.util.DataError
import com.example.domain.util.Result

interface QuizIssueReportRepository {
    suspend fun getAllIssueReports(): Result<List<IssueReport>, DataError>
    suspend fun insertIssueReport(issueReport: IssueReport): Result<Unit, DataError>
    suspend fun deleteIssueReportById(id: String?): Result<Unit, DataError>
}