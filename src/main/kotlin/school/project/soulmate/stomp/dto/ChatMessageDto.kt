package school.project.soulmate.stomp.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import school.project.soulmate.member.entity.Member
import school.project.soulmate.stomp.entity.ChatMessage
import school.project.soulmate.stomp.entity.ChatRoom

data class ChatMessageDto(
    var id: Long? = null,
    @field:NotBlank
    @JsonProperty("userId")
    var sender: ChatRoom,
    @field:NotBlank
    @JsonProperty("chatRoomId")
    var chatRoom: Member,
    @field:NotBlank
    @JsonProperty("messageText")
    val content: String,
) {
    fun toEntity(): ChatMessage = ChatMessage(id, sender, chatRoom, content)
}
