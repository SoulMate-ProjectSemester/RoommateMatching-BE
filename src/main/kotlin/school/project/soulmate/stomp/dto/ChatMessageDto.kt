package school.project.soulmate.stomp.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class ChatMessageDto(
    var id: Long? = null,
    @field:NotBlank
    @JsonProperty("userId")
    var sender: Long,
    @field:NotBlank
    @JsonProperty("messageText")
    val content: String,
)
