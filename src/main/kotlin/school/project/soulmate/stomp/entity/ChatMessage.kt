package school.project.soulmate.stomp.entity

import BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
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
) : BaseEntity()
