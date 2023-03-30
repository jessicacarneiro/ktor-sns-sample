import aws.sdk.kotlin.services.sns.SnsClient
import aws.sdk.kotlin.services.sns.model.CreateTopicRequest
import aws.sdk.kotlin.services.sns.model.PublishRequest

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

    suspend fun pubTopic(topicArnVal: String, messageVal: String): String {

        val request = PublishRequest {
            message = messageVal
            topicArn = topicArnVal
        }

        SnsClient { region = "us-east-1" }.use { snsClient ->
            val result = snsClient.publish(request)
            return "${result.messageId} message sent."
        }
    }


}