package com.example.data.repository

import com.example.data.database.entity.QuizQuestionEntity
import com.example.data.database.entity.QuizTopicEntity
import com.example.data.mapper.toQuizTopic
import com.example.data.mapper.toQuizTopicEntity
import com.example.data.util.Constant.TOPIC_COLLECTION_NAME
import com.example.domain.model.QuizTopic
import com.example.domain.repository.QuizTopicRepository
import com.example.domain.util.DataError
import com.example.domain.util.Result
import com.mongodb.client.model.Filters
import com.mongodb.client.model.Sorts
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList

class QuizTopicRepositoryImpl(db: MongoDatabase) : QuizTopicRepository {

    private val topicCollection =
        db.getCollection<QuizTopicEntity>(collectionName = TOPIC_COLLECTION_NAME)

    override suspend fun getAllTopics(): Result<List<QuizTopic>, DataError> {
        return try {
            val sortQuery = Sorts.ascending(QuizTopicEntity::code.name)
            val topics = topicCollection.find().sort(sortQuery).map { it.toQuizTopic() }.toList()
            if (topics.isNotEmpty()) {
                Result.Success(topics)
            } else {
                Result.Failure(error = DataError.NotFound)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(error = DataError.Database)
        }
    }

    override suspend fun upsertTopic(quizTopic: QuizTopic): Result<Unit, DataError> {
        return try {
            if (quizTopic.id == null) {
                topicCollection.insertOne(quizTopic.toQuizTopicEntity())
            } else {
                val updateResult = topicCollection.updateOne(
                    filter = Filters.eq(QuizTopicEntity::_id.name, quizTopic.id),
                    update = Updates.combine(
                        Updates.set(QuizTopicEntity::name.name, quizTopic.name),
                        Updates.set(QuizTopicEntity::imageUrl.name, quizTopic.imageUrl),
                        Updates.set(QuizTopicEntity::code.name, quizTopic.code),
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

    override suspend fun getTopicById(id: String?): Result<QuizTopic, DataError> {
        if (id.isNullOrBlank()) return Result.Failure(DataError.Validation)
        return try {
            val topicEntity = topicCollection.find(filter = Filters.eq(QuizTopicEntity::_id.name, id)).firstOrNull()
            if (topicEntity != null) {
                Result.Success(topicEntity.toQuizTopic())
            } else {
                Result.Failure(error = DataError.NotFound)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(error = DataError.Database)
        }
    }

    override suspend fun deleteTopicById(id: String?): Result<Unit, DataError> {
        if (id.isNullOrBlank()) return Result.Failure(DataError.Validation)
        return try {
            val deleteResult = topicCollection.deleteOne(filter = Filters.eq(QuizTopicEntity::_id.name, id))

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