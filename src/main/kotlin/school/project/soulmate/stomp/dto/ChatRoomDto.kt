@file:Suppress("ktlint:standard:no-wildcard-imports")

package school.project.soulmate.stomp.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import lombok.Getter
import java.time.LocalDate
import java.util.*

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

data class LeaveRoomDto(
    @field:NotBlank
    @JsonProperty("loginId")
    val loginId: Long,
    @field:NotBlank
    @JsonProperty("roomId")
    val roomId: UUID,
)
