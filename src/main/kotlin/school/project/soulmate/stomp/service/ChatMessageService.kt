package school.project.soulmate.stomp.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import school.project.soulmate.common.exception.InvalidInputException
import school.project.soulmate.member.entity.Member
import school.project.soulmate.member.repository.MemberRepository
import school.project.soulmate.stomp.dto.ChatMessageDto
import school.project.soulmate.stomp.dto.ChatMessageDtoResponse
import school.project.soulmate.stomp.entity.ChatMessage
import school.project.soulmate.stomp.entity.ChatRoom
import school.project.soulmate.stomp.repository.ChatMessageRepository
import school.project.soulmate.stomp.repository.ChatRoomRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

@Service
class ChatMessageService(
    val chatMessageRepository: ChatMessageRepository,
    val chatRoomRepository: ChatRoomRepository,
    val memberRepository: MemberRepository,
) {
    @Transactional
    fun saveMessage(
        roomId: UUID,
        message: ChatMessageDto,
    ): String {
        val findChatRoom: ChatRoom = chatRoomRepository.findByIdOrNull(roomId) ?: throw InvalidInputException("채팅방이 존재하지 않습니다.")
        val findSender: Member = memberRepository.findByIdOrNull(message.sender) ?: throw InvalidInputException("유저가 존재하지 않습니다.")
        val chatMessageEntity = ChatMessage(message.id, findChatRoom, findSender, message.content, LocalDateTime.now())
        chatMessageRepository.save(chatMessageEntity)
        return "메시지가 저장되었습니다."
    }

    fun loadMessages(chatRoom: UUID): List<ChatMessageDtoResponse> {
        val findRoom: ChatRoom = chatRoomRepository.findByIdOrNull(chatRoom) ?: throw InvalidInputException("채팅방이 존재하지 않습니다.")
        val roomMessages: List<ChatMessage> = chatMessageRepository.findAllByChatRoom(findRoom)
        // 원하는 날짜와 시간 형식을 정의
        val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy, h:mm a")
        return roomMessages.map { message ->
            ChatMessageDtoResponse(
                id = message.id,
                sender = message.sender.id!!, // Member 엔티티에서 ID를 가져옵니다.
                content = message.content,
                timestamp = message.timestamp.format(formatter),
            )
        }
    }
}
