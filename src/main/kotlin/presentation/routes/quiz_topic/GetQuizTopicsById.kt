package com.example.presentation.routes.quiz_topic

import com.example.domain.repository.QuizTopicRepository
import com.example.domain.util.onFailure
import com.example.domain.util.onSuccess
import com.example.presentation.util.respondWithError
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.getQuizTopicById(repository: QuizTopicRepository) {
    get<QuizTopicRoutesPath.ById> { path ->
        repository.getTopicById(path.topicId)
            .onSuccess { topic ->
                call.respond(message = topic, status = io.ktor.http.HttpStatusCode.OK)
            }.onFailure { error ->
                respondWithError(error)
            }
    }
}