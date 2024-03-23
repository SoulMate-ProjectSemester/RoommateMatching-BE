package school.project.soulmate.stomp.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import lombok.Getter
import java.time.LocalDate

@Getter
data class ChatRoomDto(
    var id: Long?,
    @field:NotBlank
    @JsonProperty("loginId")
    val loginId: String,
    @field:NotBlank
    @JsonProperty("userId")
    val userId: String,
    @JsonProperty("roomName")
    val roomName: String?,
    @JsonProperty("createDate")
    val createDate: LocalDate,
)
