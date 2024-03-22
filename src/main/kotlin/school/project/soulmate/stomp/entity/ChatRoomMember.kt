package school.project.soulmate.stomp.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import school.project.soulmate.member.entity.Member

@Entity
class ChatRoomMember(
    @Id
    @GeneratedValue
    val id: Long? = null,
    @ManyToOne
    @JoinColumn(name = "member_id")
    val member: Member,
    @ManyToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "chat_room_id")
    val chatRoom: ChatRoom,
)
