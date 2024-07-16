package school.project.soulmate.stomp.dto

import com.fasterxml.jackson.annotation.JsonProperty
import lombok.Getter
import lombok.Setter
import school.project.soulmate.stomp.entity.ChatMessage
import java.time.format.DateTimeFormatter

@Getter
@Setter
data class ChatMessageDto(
    var id: Long? = null,
    @JsonProperty("userId")
    var sender: Long,
    @JsonProperty("messageText")
    val content: String,
)

data class ChatMessageDtoResponse(
    var id: Long? = null,
    @JsonProperty("userId")
    var sender: Long?,
    @JsonProperty("messageText")
    val content: String,
    val timestamp: String,
){
    constructor(chatMessage: ChatMessage) : this (
        id = chatMessage.id,
        sender = chatMessage.sender.id,
        content = chatMessage.content,
        timestamp = chatMessage.timestamp.format(DateTimeFormatter.ofPattern("MMM d, yyyy, h:mm a"))
    )
}
