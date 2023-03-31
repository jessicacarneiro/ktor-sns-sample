package com.example.responses

import aws.sdk.kotlin.services.sns.model.ListTopicsResponse
import kotlinx.serialization.Serializable

@Serializable
data class TopicsListResponse(
    val topics: List<String>,
) {
    companion object {
        fun from(listTopics: ListTopicsResponse): TopicsListResponse {
            return TopicsListResponse(listTopics.topics?.mapNotNull { topic -> topic.topicArn } ?: emptyList())
        }
    }
}