import aws.sdk.kotlin.services.sns.SnsClient
import aws.sdk.kotlin.services.sns.model.CreateTopicRequest
import aws.sdk.kotlin.services.sns.model.ListTopicsRequest
import aws.sdk.kotlin.services.sns.model.PublishRequest
import com.example.responses.MessagePublishedResponse
import com.example.responses.TopicCreatedResponse
import com.example.responses.TopicsListResponse

class SnsService {
    private val defaultAwsRegion = System.getenv("AWS_REGION")
    private val awsAccountId = System.getenv("AWS_ACCOUNT_ID")
    private val defaultTopicName = System.getenv("DEFAULT_TOPIC_NAME")
    private val topicArnBase = "arn:aws:sns:"

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

    private fun generateTopicArn(
        accountId: String = awsAccountId,
        region: String = defaultAwsRegion,
        topicName: String = defaultTopicName,
    ): String {
        return "$topicArnBase:$region:$accountId:$topicName"
    }
}