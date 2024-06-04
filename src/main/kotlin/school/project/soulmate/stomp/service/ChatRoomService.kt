package school.project.soulmate.stomp.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import school.project.soulmate.common.exception.InvalidInputException
import school.project.soulmate.member.entity.Member
import school.project.soulmate.member.repository.MemberRepository
import school.project.soulmate.stomp.dto.ChatRoomDto
import school.project.soulmate.stomp.dto.ChatRoomInfoDto
import school.project.soulmate.stomp.dto.LeaveRoomDto
import school.project.soulmate.stomp.dto.MemberInfoDto
import school.project.soulmate.stomp.entity.ChatRoom
import school.project.soulmate.stomp.entity.ChatRoomMember
import school.project.soulmate.stomp.repository.ChatRoomMemberRepository
import school.project.soulmate.stomp.repository.ChatRoomRepository
import java.util.*

@Service
class ChatRoomService(
    val chatRoomRepository: ChatRoomRepository,
    val memberRepository: MemberRepository,
    val chatRoomMemberRepository: ChatRoomMemberRepository,
) {
    @Transactional
    fun createChatRoom(chatRoomDto: ChatRoomDto): ChatRoom {
        // 사용자 조회
        val loginMember: Member = memberRepository.findByLoginId(chatRoomDto.loginId) ?: throw InvalidInputException("유저를 찾을 수 없습니다.")
        val userMember: Member = memberRepository.findByLoginId(chatRoomDto.userId) ?: throw InvalidInputException("유저를 찾을 수 없습니다.")

        // 채팅방 생성
        val chatRoom =
            ChatRoom(
                roomName = chatRoomDto.roomName,
                createDate = chatRoomDto.createDate,
            )
        chatRoomRepository.save(chatRoom)

        // 채팅방 멤버 추가
        val loginChatRoomMember = ChatRoomMember(chatRoom = chatRoom, member = loginMember)
        val userChatRoomMember = ChatRoomMember(chatRoom = chatRoom, member = userMember)
        chatRoomMemberRepository.saveAll(listOf(loginChatRoomMember, userChatRoomMember))

        return chatRoom
    }

    // 로그인한 유저가 첫번째, 나머지 유저는 이름으로 오름차순
    fun findRoom(roomId: UUID, userId: Long?): ChatRoomInfoDto {
        val findMember: Member = memberRepository.findByIdOrNull(userId) ?: throw InvalidInputException("유저를 찾을 수 없습니다.")

        val findRoom: ChatRoom = chatRoomRepository.findByRoomId(roomId)
        val chatRoomMembers: List<ChatRoomMember> = chatRoomMemberRepository.findAllByChatRoom(findRoom)

        // 채팅방의 멤버 리스트에서 findMember가 아닌 멤버들을 MemberInfoDto로 매핑하고 이름으로 정렬
        val members: MutableList<MemberInfoDto> = mapAndSortChatRoomMembers(chatRoomMembers, findMember.id)

        // findMember를 첫 번째 요소로 추가
        members.add(0, MemberInfoDto(memberId = findMember.id, memberName = findMember.name))

        // ChatRoomInfoDto 객체 생성 및 반환
        return ChatRoomInfoDto(
            roomId = roomId,
            roomName = findRoom.roomName,
            createDate = findRoom.createDate,
            members = members,
        )
    }

    // 로그인한 유저 제외하고 이름으로 오름차순
    fun findRooms(loginId: String): List<ChatRoomInfoDto> {
        val findMember: Member = memberRepository.findByLoginId(loginId) ?: throw InvalidInputException("유저를 찾을 수 없습니다.")
        val chatRoomMembers: List<ChatRoomMember> = chatRoomMemberRepository.findAllByMember(findMember)

        return chatRoomMembers.map { chatRoomMember ->
            val chatRoom = chatRoomMember.chatRoom
            val roomMembers: List<ChatRoomMember> = chatRoomMemberRepository.findAllByChatRoom(chatRoom)

            // 채팅방의 멤버 리스트에서 findMember가 아닌 멤버들을 MemberInfoDto로 매핑하고 이름으로 정렬
            val members: MutableList<MemberInfoDto> = mapAndSortChatRoomMembers(roomMembers, findMember.id)

            ChatRoomInfoDto(
                roomId = chatRoomMember.chatRoom.roomId!!,
                roomName = chatRoomMember.chatRoom.roomName,
                createDate = chatRoomMember.chatRoom.createDate,
                members = members,
            )
        }
    }

    fun mapAndSortChatRoomMembers(chatRoomMembers: List<ChatRoomMember>, memberId: Long?): MutableList<MemberInfoDto> {
        return chatRoomMembers
            .filter { member -> member.member.id != memberId }
            .map { member ->
                MemberInfoDto(memberId = member.member.id, memberName = member.member.name)
            }
            .sortedBy { it.memberName }
            .toMutableList()
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
