package com.example.data.repository

import com.example.data.database.entity.QuizQuestionEntity
import com.example.data.mapper.toQuizQuestion
import com.example.data.mapper.toQuizQuestionEntity
import com.example.data.util.Constant.QUESTION_COLLECTION_NAME
import com.example.domain.model.QuizQuestion
import com.example.domain.repository.QuizQuestionRepository
import com.example.domain.util.DataError
import com.example.domain.util.Result
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.bson.conversions.Bson

class QuizQuestionRepositoryImpl(db: MongoDatabase) : QuizQuestionRepository {

    private val quizCollection =
        db.getCollection<QuizQuestionEntity>(collectionName = QUESTION_COLLECTION_NAME)

    override suspend fun insertQuestionsInBulk(questions: List<QuizQuestion>): Result<Unit, DataError> {
        return try {
            val questionsEntity = questions.map { it.toQuizQuestionEntity() }
            quizCollection.insertMany(questionsEntity)
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

    override suspend fun upsertQuestion(question: QuizQuestion): Result<Unit, DataError> {
        return try {
            if (question.id == null) {
                quizCollection.insertOne(question.toQuizQuestionEntity())
            } else {
                val updateResult = quizCollection.updateOne(
                    filter = Filters.eq(QuizQuestionEntity::_id.name, question.id),
                    update = Updates.combine(
                        Updates.set(QuizQuestionEntity::question.name, question.question),
                        Updates.set(QuizQuestionEntity::correctAnswer.name, question.correctAnswer),
                        Updates.set(QuizQuestionEntity::incorrectAnswers.name, question.incorrectAnswers),
                        Updates.set(QuizQuestionEntity::explanation.name, question.explanation),
                        Updates.set(QuizQuestionEntity::topicCode.name, question.topicCode),
                    )
                )

                if (updateResult.modifiedCount == 0L) {
                    return Result.Failure(error = DataError.NotFound)
                }
            }
            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(error = DataError.Database)
        }
    }

    override suspend fun getRandomQuestions(topicCode: Int?, limit: Int?): Result<List<QuizQuestion>, DataError> {
        return try {
            val questionLimit = limit?.takeIf { it > 0 } ?: 10
            val filter = Filters.eq(QuizQuestionEntity::topicCode.name, topicCode)
            val matchStage = if (topicCode == null || topicCode == 0) {
                emptyList<Bson>()
            } else {
                listOf(Aggregates.match(filter))
            }
            val pipeline = matchStage + Aggregates.sample(questionLimit)
            val questionList = quizCollection
                .aggregate(pipeline = pipeline)
                .map { it.toQuizQuestion() }
                .toList()

            if (questionList.isNotEmpty()) {
                Result.Success(questionList)
            } else {
                Result.Failure(error = DataError.NotFound)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(error = DataError.Database)
        }
    }

    override suspend fun getAllQuestions(topicCode: Int?): Result<List<QuizQuestion>, DataError> {
        return try {
            val filter = topicCode?.let { Filters.eq(QuizQuestionEntity::topicCode.name, it) }
            val questionList = quizCollection.find(filter = filter ?: Filters.empty())
                .map { it.toQuizQuestion() }
                .toList()

            if (questionList.isNotEmpty()) {
                Result.Success(questionList)
            } else {
                Result.Failure(error = DataError.NotFound)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(error = DataError.Database)
        }
    }

    override suspend fun getQuestionById(id: String?): Result<QuizQuestion, DataError> {
        if (id.isNullOrBlank()) return Result.Failure(DataError.Validation)
        return try {
            val quizQuestionEntity =
                quizCollection.find(filter = Filters.eq(QuizQuestionEntity::_id.name, id)).firstOrNull()
            if (quizQuestionEntity != null) {
                Result.Success(quizQuestionEntity.toQuizQuestion())
            } else {
                Result.Failure(error = DataError.NotFound)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(error = DataError.Database)
        }
    }

    override suspend fun deleteQuestionById(id: String?): Result<Unit, DataError> {
        if (id.isNullOrBlank()) return Result.Failure(DataError.Validation)
        return try {
            val deleteResult = quizCollection.deleteOne(filter = Filters.eq(QuizQuestionEntity::_id.name, id))

            if (deleteResult.deletedCount == 1L) {
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