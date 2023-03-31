package com.example.requests

import kotlinx.serialization.Serializable

@Serializable
data class PublishMessageRequest(
    val region: String = System.getenv("AWS_REGION"),
    val message: String,
    val topicArn: String,
)
