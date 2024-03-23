@file:Suppress("ktlint:standard:no-wildcard-imports")

package school.project.soulmate.stomp.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import school.project.soulmate.member.repository.MemberRepository
import school.project.soulmate.stomp.dto.ChatRoomDto
import school.project.soulmate.stomp.dto.ChatRoomMemberDto
import school.project.soulmate.stomp.entity.ChatRoom
import school.project.soulmate.stomp.entity.ChatRoomMember
import school.project.soulmate.stomp.repository.ChatRoomMemberRepository
import school.project.soulmate.stomp.repository.ChatRoomRepository

@Service
class ChatRoomService(
    val chatRoomRepository: ChatRoomRepository,
    val memberRepository: MemberRepository,
    val chatRoomMemberRepository: ChatRoomMemberRepository,
) {
    @Transactional
    fun createChatRoom(chatRoomDto: ChatRoomDto): String {
        // 사용자 조회
        val loginMember = memberRepository.findByLoginId(chatRoomDto.loginId)
        val userMember = memberRepository.findByLoginId(chatRoomDto.userId)

        // 채팅방 생성
        val chatRoom =
            ChatRoom(
                roomName = chatRoomDto.roomName,
                createDate = chatRoomDto.createDate,
            )
        chatRoomRepository.save(chatRoom)

        // 채팅방 멤버 추가
        val loginChatRoomMember = ChatRoomMember(chatRoom = chatRoom, member = loginMember!!)
        val userChatRoomMember = ChatRoomMember(chatRoom = chatRoom, member = userMember!!)
        chatRoomMemberRepository.saveAll(listOf(loginChatRoomMember, userChatRoomMember))

        return "채팅방이 생성되었습니다"
    }

    fun findRooms(loginId: String): List<ChatRoomMemberDto>? {
        val findMember = memberRepository.findByLoginId(loginId)
        val chatRoomMembers = chatRoomMemberRepository.findAllByMember(findMember)
        return chatRoomMembers.map { member ->
            ChatRoomMemberDto(roomId = member.chatRoom.roomId!!)
        }
    }
}
