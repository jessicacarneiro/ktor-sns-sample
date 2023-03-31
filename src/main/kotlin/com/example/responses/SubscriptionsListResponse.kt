package com.example.responses

import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionsListResponse(
    val subscriptions: List<Subscription>,
)

@Serializable
data class Subscription(
    val topicArn: String?,
    val protocol: String?,
    val owner: String?,
    val endpoint: String?,
    val subscriptionArn: String?,
)
