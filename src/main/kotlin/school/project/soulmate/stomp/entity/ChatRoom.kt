package school.project.soulmate.stomp.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.util.*
import kotlin.collections.HashSet

@Entity
class ChatRoom(
    @Column(length = 15)
    val roomName: String?,
    @Column(nullable = false)
    val createDate: LocalDate,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val roomId: UUID? = null

    @OneToMany(mappedBy = "chatRoom", cascade = [CascadeType.ALL])
    val chatRoomMembers: MutableSet<ChatRoomMember> = HashSet()

    @OneToMany(mappedBy = "chatRoom", cascade = [CascadeType.ALL])
    val messages: MutableSet<ChatMessage> = HashSet()
}
