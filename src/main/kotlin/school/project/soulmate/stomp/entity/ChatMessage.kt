@file:Suppress("ktlint:standard:no-wildcard-imports")

package school.project.soulmate.stomp.entity

import jakarta.persistence.*
import lombok.Getter
import school.project.soulmate.member.entity.Member
import java.time.LocalDateTime

@Entity
@Getter
class ChatMessage(
    @Id @GeneratedValue
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    val chatRoom: ChatRoom,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    val sender: Member,
    @Column(nullable = false)
    val content: String,
    @Column(nullable = false)
    val timestamp: LocalDateTime = LocalDateTime.now(),
)
