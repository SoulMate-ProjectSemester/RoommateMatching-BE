package school.project.soulmate.stomp.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import school.project.soulmate.common.exception.InvalidInputException
import school.project.soulmate.member.repository.MemberRepository
import school.project.soulmate.stomp.dto.ChatRoomDto
import school.project.soulmate.stomp.dto.ChatRoomInfoDto
import school.project.soulmate.stomp.dto.LeaveRoomDto
import school.project.soulmate.stomp.dto.MemberInfoDto
import school.project.soulmate.stomp.entity.ChatRoom
import school.project.soulmate.stomp.entity.ChatRoomMember
import school.project.soulmate.stomp.repository.ChatRoomMemberRepository
import school.project.soulmate.stomp.repository.ChatRoomRepository
import java.util.UUID

@Service
class ChatRoomService(
    val chatRoomRepository: ChatRoomRepository,
    val memberRepository: MemberRepository,
    val chatRoomMemberRepository: ChatRoomMemberRepository,
) {
    @Transactional
    fun createChatRoom(chatRoomDto: ChatRoomDto): ChatRoom {
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

        return chatRoom
    }

    fun findRoom(roomId: UUID): ChatRoomInfoDto {
        val findRoom = chatRoomRepository.findByRoomId(roomId)
        val chatRoomMembers = chatRoomMemberRepository.findAllByChatRoom(findRoom)
        val members =
            chatRoomMembers.map { member ->
                MemberInfoDto(memberId = member.member.id!!, memberName = member.member.name)
            }.toList()
        return ChatRoomInfoDto(
            roomId = roomId,
            roomName = findRoom.roomName,
            createDate = findRoom.createDate,
            members = members,
        )
    }

    fun findRooms(loginId: String): List<ChatRoomInfoDto> {
        val findMember = memberRepository.findByLoginId(loginId)
        val chatRoomMembers = chatRoomMemberRepository.findAllByMember(findMember)

        return chatRoomMembers.map { chatRoomMember ->
            val members =
                chatRoomMember.chatRoom.chatRoomMembers.map { member ->
                    MemberInfoDto(memberId = member.member.id!!, memberName = member.member.name)
                }.toList()

            ChatRoomInfoDto(
                roomId = chatRoomMember.chatRoom.roomId!!,
                roomName = chatRoomMember.chatRoom.roomName,
                createDate = chatRoomMember.chatRoom.createDate,
                members = members,
            )
        }
    }

    @Transactional
    fun leaveChatRoom(leaveRoomDto: LeaveRoomDto): String {
        val chatRoom =
            chatRoomRepository.findByIdOrNull(leaveRoomDto.roomId) ?: throw InvalidInputException("채팅방이 존재하지 않습니다.")

        val member =
            chatRoom.chatRoomMembers.find { it.member.id == leaveRoomDto.loginId }
                ?: throw InvalidInputException("채팅방에 참여되지 않은 유저입니다.")

        // 채팅방 멤버 목록에서 멤버 제거
        chatRoom.chatRoomMembers.remove(member)
        chatRoomMemberRepository.delete(member)

        // 멤버수가 1명일 경우 방 삭제
        if (chatRoom.chatRoomMembers.size <= 1) {
            chatRoomRepository.delete(chatRoom)
            return "채팅방이 삭제되었습니다."
        }
        return "채팅방을 나갔습니다."
    }
}
