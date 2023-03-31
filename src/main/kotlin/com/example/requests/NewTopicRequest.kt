package com.example.requests

import kotlinx.serialization.Serializable

@Serializable
data class NewTopicRequest(
    val region: String = System.getenv("AWS_REGION"),
    val name: String,
)