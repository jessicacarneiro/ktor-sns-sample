package com.example.responses

import aws.sdk.kotlin.services.sns.model.GetTopicAttributesResponse
import kotlinx.serialization.Serializable

@Serializable
data class TopicAttributesListResponse(
    val attributes: Map<String, String>,
) {
    companion object {
        fun from(result: GetTopicAttributesResponse): TopicAttributesListResponse {
            return TopicAttributesListResponse(result.attributes ?: emptyMap())
        }
    }
}
