package com.example.presentation.routes.quiz_topic

import com.example.domain.repository.QuizTopicRepository
import com.example.domain.util.onFailure
import com.example.domain.util.onSuccess
import com.example.presentation.util.respondWithError
import io.ktor.http.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.deleteQuizTopicById(
    repository: QuizTopicRepository,
) {
    delete<QuizTopicRoutesPath.ById> { path ->
        try {
            repository.deleteTopicById(path.topicId)
                .onSuccess {
                    call.respond(message = "Question Deleted Successfully", status = HttpStatusCode.NoContent)
                }.onFailure { error ->
                    respondWithError(error)
                }
        } catch (e: IllegalArgumentException) {
            call.respond(message = "Id is not correct", status = HttpStatusCode.BadRequest)
        } catch (e: Exception) {
            call.respond(message = e.message ?: "Error Occurred", status = HttpStatusCode.InternalServerError)
        }
    }
}

