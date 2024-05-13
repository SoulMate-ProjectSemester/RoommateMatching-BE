package school.project.soulmate.gptApi.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import school.project.soulmate.stomp.entity.ChatRoom
import java.time.LocalDate

@Entity
class RoomThread(
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chatroom_id")
    var room: ChatRoom,
    val threadId: String,
    val createDate: LocalDate,
)