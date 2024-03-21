package school.project.soulmate.stomp.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import school.project.soulmate.stomp.entity.ChatRoom
import java.time.LocalDate
import java.util.*

data class ChatRoomDto(
    var id: Long?,
    @field:NotBlank
    @JsonProperty("loginId")
    val loginId: String?,
    @field:NotBlank
    @JsonProperty("userId")
    val userId: String?,
    @JsonProperty("roomName")
    val roomName: String?,
    @JsonProperty("createDate")
    val createDate: LocalDate,
) {
    fun toEntityList(): List<ChatRoom> {
        val sharedRoomId = UUID.randomUUID().toString() // 같은 roomId를 생성
        val entities = mutableListOf<ChatRoom>()

        // 로그인한 아이디용 ChatRoom 엔티티 생성
        loginId?.let {
            entities.add(ChatRoom(roomId = sharedRoomId, userId = it, createDate = LocalDate.now()))
        }

        // 선택한 아이디용 ChatRoom 엔티티 생성
        userId?.let {
            entities.add(ChatRoom(roomId = sharedRoomId, userId = it, createDate = LocalDate.now()))
        }

        return entities
    }
}
