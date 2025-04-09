package com.example.presentation.routes.quiz_topic

import io.ktor.resources.*

@Resource("quiz/topics")
class QuizTopicRoutesPath {

    @Resource("/{topicId}")
    data class ById(val parent: QuizTopicRoutesPath = QuizTopicRoutesPath(), val topicId: String)
}