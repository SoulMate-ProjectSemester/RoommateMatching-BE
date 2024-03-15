package school.project.soulmate.stomp.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import school.project.soulmate.stomp.entity.ChatMessage
import java.time.LocalDateTime

data class ChatMessageDto(
    var id: Long? = null,

    @field:NotBlank
    @JsonProperty("userId")
    var senderId: String,

    @field:NotBlank
    @JsonProperty("chatRoomId")
    var chatRoomId: String,

    @field:NotBlank
    @JsonProperty("messageText")
    val messageText: String,
){
    fun toEntity() : ChatMessage = ChatMessage(id, senderId, chatRoomId, messageText, messageTime = LocalDateTime.now())
}