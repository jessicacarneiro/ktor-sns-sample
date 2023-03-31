package com.example.responses

import aws.sdk.kotlin.services.sns.model.PublishResponse
import kotlinx.serialization.Serializable

@Serializable
data class MessagePublishedResponse(
    val id: String,
    val sequenceNumber: String?,
) {
    companion object {
        fun from(publishResponse: PublishResponse): MessagePublishedResponse {
            return MessagePublishedResponse(
                id = publishResponse.messageId ?: "",
                sequenceNumber = publishResponse.sequenceNumber
            )
        }
    }
}
