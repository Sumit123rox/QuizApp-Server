package com.example.domain.repository

import com.example.domain.model.QuizTopic
import com.example.domain.util.DataError
import com.example.domain.util.Result

interface QuizTopicRepository {
    suspend fun getAllTopics(): Result<List<QuizTopic>, DataError>
    suspend fun upsertTopic(quizTopic: QuizTopic): Result<Unit, DataError>
    suspend fun getTopicById(id: String?): Result<QuizTopic, DataError>
    suspend fun deleteTopicById(id: String?): Result<Unit, DataError>
}