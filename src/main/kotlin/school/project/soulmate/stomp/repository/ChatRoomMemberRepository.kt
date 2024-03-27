package school.project.soulmate.stomp.repository

import org.springframework.data.jpa.repository.JpaRepository
import school.project.soulmate.member.entity.Member
import school.project.soulmate.stomp.entity.ChatRoom
import school.project.soulmate.stomp.entity.ChatRoomMember

interface ChatRoomMemberRepository : JpaRepository<ChatRoomMember, Long> {
    fun findAllByMember(member: Member?): List<ChatRoomMember>

    fun deleteByChatRoomAndMember(
        chatRoom: ChatRoom,
        member: Member,
    )
}
