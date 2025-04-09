package com.example.data.mapper

import com.example.data.database.entity.QuizTopicEntity
import com.example.domain.model.QuizTopic

fun QuizTopicEntity.toQuizTopic() = QuizTopic(
    id = _id,
    name = name,
    imageUrl = imageUrl,
    code = code
)

fun QuizTopic.toQuizTopicEntity() = QuizTopicEntity(
    name = name,
    imageUrl = imageUrl,
    code = code
)