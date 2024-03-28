package school.project.soulmate.stomp.entity

import jakarta.persistence.*
import school.project.soulmate.member.entity.Member

@Entity
class ChatRoomMember(
    @Id
    @GeneratedValue
    val id: Long? = null,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    val member: Member,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    val chatRoom: ChatRoom,
)
