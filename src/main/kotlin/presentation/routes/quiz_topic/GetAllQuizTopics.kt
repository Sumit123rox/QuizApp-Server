package com.example.presentation.routes.quiz_topic

import com.example.domain.repository.QuizTopicRepository
import com.example.domain.util.onFailure
import com.example.domain.util.onSuccess
import com.example.presentation.util.respondWithError
import io.ktor.http.*
import io.ktor.server.resources.*
import io.ktor.server.routing.*
import io.ktor.server.response.*

fun Route.getAllQuizTopics(
    repository: QuizTopicRepository
) {
    get<QuizTopicRoutesPath> {
        repository.getAllTopics()
            .onSuccess { topics ->
                call.respond(message = topics, status = HttpStatusCode.OK)
            }.onFailure { error ->
                respondWithError(error)
            }
    }
}