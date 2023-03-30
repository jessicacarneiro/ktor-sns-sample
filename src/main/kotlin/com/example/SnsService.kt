import aws.sdk.kotlin.services.sns.SnsClient
import aws.sdk.kotlin.services.sns.model.CreateTopicRequest
import aws.sdk.kotlin.services.sns.model.ListTopicsRequest

class SnsService {
    suspend fun createSNSTopic(topicName: String): String {

        val request = CreateTopicRequest {
            name = topicName
        }

        SnsClient { region = "us-east-1" }.use { snsClient ->
            val result = snsClient.createTopic(request)
            return result.topicArn.toString()
        }
    }
}