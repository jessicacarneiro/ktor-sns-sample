import aws.sdk.kotlin.services.sns.SnsClient
import aws.sdk.kotlin.services.sns.model.*
import com.example.responses.*
import com.example.responses.Subscription

class SnsService {
    private val defaultAwsRegion = System.getenv("AWS_REGION")
    private val awsAccountId = System.getenv("AWS_ACCOUNT_ID")
    private val defaultTopicName = System.getenv("DEFAULT_TOPIC_NAME")
    private val topicArnBase = "arn:aws:sns"

    suspend fun createSNSTopic(topicName: String, awsRegion: String?): TopicCreatedResponse {

        val request = CreateTopicRequest {
            name = topicName
        }

        SnsClient { region = awsRegion ?: defaultAwsRegion }.use { snsClient ->
            return TopicCreatedResponse.from(snsClient.createTopic(request))
        }
    }

    suspend fun listSNSTopics(awsRegion: String?): TopicsListResponse {

        SnsClient { region = awsRegion ?: defaultAwsRegion }.use { snsClient ->
            return TopicsListResponse.from(snsClient.listTopics(ListTopicsRequest { }))
        }
    }

    suspend fun pubTopic(
        topicArnVal: String,
        messageVal: String,
        awsRegion: String?,
    ): MessagePublishedResponse {

        val request = PublishRequest {
            message = messageVal
            topicArn = topicArnVal
        }

        SnsClient { region = awsRegion ?: defaultAwsRegion }.use { snsClient ->
            return MessagePublishedResponse.from(snsClient.publish(request))
        }
    }

    suspend fun deleteSNSTopic(topicName: String, awsRegion: String?): String {

        val request = DeleteTopicRequest {
            topicArn = generateTopicArn(region = awsRegion ?: defaultAwsRegion, topicName = topicName)
        }

        SnsClient { region = awsRegion ?: defaultAwsRegion }.use { snsClient ->
            snsClient.deleteTopic(request)
            return "$topicName was successfully deleted."
        }
    }

    suspend fun getSNSTopicAttributes(topicName: String, awsRegion: String?): TopicAttributesListResponse {

        val request = GetTopicAttributesRequest {
            topicArn = generateTopicArn(region = awsRegion ?: defaultAwsRegion, topicName = topicName)
        }

        SnsClient { region = awsRegion ?: defaultAwsRegion }.use { snsClient ->
            val result = snsClient.getTopicAttributes(request)
            return TopicAttributesListResponse.from(result)
        }
    }

    suspend fun listSNSSubscriptions(awsRegion: String?): SubscriptionsListResponse {

        SnsClient { region = awsRegion ?: defaultAwsRegion }.use { snsClient ->
            val response = snsClient.listSubscriptions(ListSubscriptionsRequest {})
            return SubscriptionsListResponse(
                subscriptions = response.subscriptions?.map { sub ->
                    Subscription(
                        topicArn = sub.topicArn,
                        protocol = sub.protocol,
                        owner = sub.owner,
                        endpoint = sub.endpoint,
                        subscriptionArn = sub.subscriptionArn,
                    )
                }
                    ?: emptyList()
            )
        }
    }

    private fun generateTopicArn(
        region: String = defaultAwsRegion,
        topicName: String = defaultTopicName,
    ): String {
        return "$topicArnBase:$region:$awsAccountId:$topicName"
    }
}