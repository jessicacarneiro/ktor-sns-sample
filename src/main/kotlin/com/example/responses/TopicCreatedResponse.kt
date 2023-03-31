package com.example.responses

import aws.sdk.kotlin.services.sns.model.CreateTopicResponse
import kotlinx.serialization.Serializable

@Serializable
data class TopicCreatedResponse(
    val topicArn: String,
) {
    companion object {
        fun from(createTopicResponse: CreateTopicResponse): TopicCreatedResponse {
            return TopicCreatedResponse(topicArn = createTopicResponse.topicArn ?: "")
        }
    }
}