package school.project.soulmate.stomp.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import school.project.soulmate.common.exception.InvalidInputException
import school.project.soulmate.member.entity.Member
import school.project.soulmate.member.repository.MemberRepository
import school.project.soulmate.stomp.dto.ChatMessageDto
import school.project.soulmate.stomp.dto.ChatMessageResponseDto
import school.project.soulmate.stomp.entity.ChatMessage
import school.project.soulmate.stomp.entity.ChatRoom
import school.project.soulmate.stomp.repository.ChatMessageRepository
import school.project.soulmate.stomp.repository.ChatRoomRepository
import java.time.LocalDateTime
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

    fun loadMessages(chatRoom: UUID): List<ChatMessageResponseDto> {
        val findRoom: ChatRoom = chatRoomRepository.findByIdOrNull(chatRoom) ?: throw InvalidInputException("채팅방이 존재하지 않습니다.")
        val messages: List<ChatMessage> = chatMessageRepository.findAllByChatRoom(findRoom)
        return messages.map { messages ->
            ChatMessageResponseDto(
                id = messages.id,
                sender = messages.sender.id!!, // Member 엔티티에서 ID를 가져옵니다.
                content = messages.content,
            )
        }
    }
}
