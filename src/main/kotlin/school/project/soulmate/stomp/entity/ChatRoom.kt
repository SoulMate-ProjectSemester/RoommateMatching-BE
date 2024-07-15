package school.project.soulmate.stomp.entity

import BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import school.project.soulmate.gptApi.entity.RoomThread
import java.time.LocalDate
import java.util.UUID
import kotlin.collections.HashSet

@Entity
class ChatRoom(
    @Column(length = 15)
    val roomName: String?,

    @Column(nullable = false)
    val createDate: LocalDate,
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val roomId: UUID? = null

    @OneToMany(mappedBy = "chatRoom", cascade = [CascadeType.ALL])
    val chatRoomMembers: MutableSet<ChatRoomMember> = HashSet()

    @OneToMany(mappedBy = "chatRoom", cascade = [CascadeType.ALL])
    val messages: MutableSet<ChatMessage> = HashSet()

    @OneToOne(mappedBy = "chatRoom", cascade = [CascadeType.ALL])
    val roomThread: RoomThread? = null
}
