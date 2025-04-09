package com.example.domain.repository

import com.example.domain.model.QuizQuestion
import com.example.domain.util.DataError
import com.example.domain.util.Result

interface QuizQuestionRepository {
    suspend fun insertQuestionsInBulk(questions: List<QuizQuestion>): Result<Unit, DataError>
    suspend fun upsertQuestion(question: QuizQuestion): Result<Unit, DataError>
    suspend fun getAllQuestions(topicCode: Int?): Result<List<QuizQuestion>, DataError>
    suspend fun getRandomQuestions(topicCode: Int?, limit: Int?): Result<List<QuizQuestion>, DataError>
    suspend fun getQuestionById(id: String?): Result<QuizQuestion, DataError>
    suspend fun deleteQuestionById(id: String?): Result<Unit, DataError>
}