package school.project.soulmate.gptApi.entity

import BaseEntity
import jakarta.persistence.Column
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
    var chatRoom: ChatRoom,

    val threadId: String,

    @Column(length = 1000)
    val roomMessage: String,

    val createDate: LocalDate,
) : BaseEntity()