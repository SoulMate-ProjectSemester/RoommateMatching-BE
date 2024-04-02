package school.project.soulmate.stomp.dto

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Getter
import lombok.Setter
import java.time.LocalDateTime

@Getter
@Setter
data class ChatMessageDto(
    var id: Long? = null,
    @JsonProperty("userId")
    var sender: Long,
    @JsonProperty("messageText")
    val content: String,
    val timestamp: LocalDateTime,
)

data class ChatMessageDtoResponse(
    var id: Long? = null,
    @JsonProperty("userId")
    var sender: Long,
    @JsonProperty("messageText")
    val content: String,
    val timestamp: String,
)
