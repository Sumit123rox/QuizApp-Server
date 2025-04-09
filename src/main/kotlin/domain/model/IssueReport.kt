package com.example.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class IssueReport(
    val id: String? = null,
    val questionId: String,
    val issueType: String,
    val additionalComment: String? = null,
    val userEmail: String? = null,
    val timeStamp: String,
)
