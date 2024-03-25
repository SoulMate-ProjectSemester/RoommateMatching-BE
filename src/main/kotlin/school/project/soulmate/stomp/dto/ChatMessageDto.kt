package school.project.soulmate.stomp.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import java.util.UUID

data class ChatMessageDto(
    var id: Long? = null,
    @field:NotBlank
    @JsonProperty("userId")
    var sender: Long,
    @field:NotBlank
    @JsonProperty("chatRoomId")
    var chatRoom: UUID,
    @field:NotBlank
    @JsonProperty("messageText")
    val content: String,
    @JsonProperty("messageType")
    val messageType: String, // 메시지 타입 (예: "CHAT", "JOIN", "LEAVE" 등)
)
