package com.example.presentation.config

import com.example.data.repository.QuizIssueReportRepositoryImpl
import com.example.data.repository.QuizQuestionRepositoryImpl
import com.example.data.repository.QuizTopicRepositoryImpl
import com.example.presentation.routes.issue_report.deleteIssueReportById
import com.example.presentation.routes.issue_report.getAllQuizIssueReports
import com.example.presentation.routes.issue_report.insertIssueReport
import com.example.presentation.routes.quiz_question.deleteQuizQuestionById
import com.example.presentation.routes.quiz_question.getAllQuizQuestions
import com.example.presentation.routes.quiz_question.getQuizQuestionById
import com.example.presentation.routes.quiz_question.upsertQuizQuestion
import com.example.presentation.routes.quiz_topic.deleteQuizTopicById
import com.example.presentation.routes.quiz_topic.getAllQuizTopics
import com.example.presentation.routes.quiz_topic.getQuizTopicById
import com.example.presentation.routes.quiz_topic.upsertQuizTopic
import com.example.presentation.routes.root
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configurationRouting() {
    install(Resources)
    val quizQuestionRepository by inject<QuizQuestionRepositoryImpl>()
    val quizTopicRepository by inject<QuizTopicRepositoryImpl>()
    val quizIssueReportRepository by inject<QuizIssueReportRepositoryImpl>()
    routing {
        root()

        //Quiz Questions
        getAllQuizQuestions(repository = quizQuestionRepository)
        upsertQuizQuestion(repository = quizQuestionRepository)
        deleteQuizQuestionById(repository = quizQuestionRepository)
        getQuizQuestionById(repository = quizQuestionRepository)

        //Quiz Topics
        getAllQuizTopics(repository = quizTopicRepository)
        upsertQuizTopic(repository = quizTopicRepository)
        deleteQuizTopicById(repository = quizTopicRepository)
        getQuizTopicById(repository = quizTopicRepository)

        //Quiz Issue Report
        getAllQuizIssueReports(repository = quizIssueReportRepository)
        insertIssueReport(repository = quizIssueReportRepository)
        deleteIssueReportById(repository = quizIssueReportRepository)

        staticResources(
            remotePath = "/images",
            basePackage = "images"
        )
    }
}