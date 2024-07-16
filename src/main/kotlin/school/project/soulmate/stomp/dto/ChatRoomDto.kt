package school.project.soulmate.stomp.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank
import lombok.Getter
import school.project.soulmate.stomp.entity.ChatRoom
import java.time.LocalDate
import java.util.UUID

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

data class ChatRoomInfoDto(
    val roomId: UUID?,
    val roomName: String?,
    val createDate: LocalDate,
    val members: List<MemberInfoDto>, // 방에 포함된 멤버들의 정보를 담을 리스트
){
    constructor(chatRoom: ChatRoom, members: MutableList<MemberInfoDto>) : this(
        roomId = chatRoom.roomId,
        roomName = chatRoom.roomName,
        createDate = chatRoom.createDate,
        members = members,
    )
}

data class MemberInfoDto(
    val memberId: Long?,
    val memberName: String?, // 멤버의 식별 정보와 이름 등 추가 정보 포함 가능
)
