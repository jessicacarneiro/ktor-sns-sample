import kotlinx.serialization.Serializable

@Serializable
data class NewTopicRequest(
    val name: String,
)