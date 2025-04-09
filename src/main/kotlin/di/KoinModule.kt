package com.example.di

import com.example.data.database.DatabaseFactory
import com.example.data.repository.QuizIssueReportRepositoryImpl
import com.example.data.repository.QuizQuestionRepositoryImpl
import com.example.data.repository.QuizTopicRepositoryImpl
import com.example.domain.repository.QuizIssueReportRepository
import com.example.domain.repository.QuizQuestionRepository
import com.example.domain.repository.QuizTopicRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val koinModule = module {
    single { DatabaseFactory.create() }
    singleOf(::QuizQuestionRepositoryImpl).bind<QuizQuestionRepository>()
    singleOf(::QuizTopicRepositoryImpl).bind<QuizTopicRepository>()
    singleOf(::QuizIssueReportRepositoryImpl).bind<QuizIssueReportRepository>()
}