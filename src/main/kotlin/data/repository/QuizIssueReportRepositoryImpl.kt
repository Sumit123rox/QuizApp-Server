package com.example.data.repository

import com.example.data.database.entity.QuizIssueReportEntity
import com.example.data.mapper.toIssueReport
import com.example.data.mapper.toIssueReportEntity
import com.example.data.util.Constant.ISSUE_REPORT_COLLECTION_NAME
import com.example.domain.model.IssueReport
import com.example.domain.repository.QuizIssueReportRepository
import com.example.domain.util.DataError
import com.example.domain.util.Result
import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class QuizIssueReportRepositoryImpl(db: MongoDatabase) : QuizIssueReportRepository {

    private val reportCollection =
        db.getCollection<QuizIssueReportEntity>(collectionName = ISSUE_REPORT_COLLECTION_NAME)

    override suspend fun getAllIssueReports(): Result<List<IssueReport>, DataError> {
        return try {
            val report = reportCollection.find().map { it.toIssueReport() }.toList()

            if (report.isNotEmpty()) {
                Result.Success(report)
            } else {
                Result.Failure(error = DataError.NotFound)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(error = DataError.Database)
        }
    }

    override suspend fun insertIssueReport(issueReport: IssueReport): Result<Unit, DataError> {
        return try {
            reportCollection.insertOne(issueReport.toIssueReportEntity())
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(error = DataError.Database)
        }
    }

    override suspend fun deleteIssueReportById(id: String?): Result<Unit, DataError> {
        if (id.isNullOrBlank()) return Result.Failure(DataError.Validation)
        return try {
            val deleteResult = reportCollection.deleteOne(filter = Filters.eq(QuizIssueReportEntity::_id.name, id))
            if (deleteResult.deletedCount > 0) {
                Result.Success(Unit)
            } else {
                Result.Failure(error = DataError.NotFound)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(error = DataError.Database)
        }
    }
}