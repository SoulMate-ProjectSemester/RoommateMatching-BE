package school.project.soulmate.stomp.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import school.project.soulmate.member.entity.Member
import school.project.soulmate.member.repository.MemberRepository
import school.project.soulmate.stomp.dto.ChatMessageDto
import school.project.soulmate.stomp.entity.ChatMessage
import school.project.soulmate.stomp.entity.ChatRoom
import school.project.soulmate.stomp.repository.ChatMessageRepository
import school.project.soulmate.stomp.repository.ChatRoomRepository
import java.time.LocalDateTime

@Service
class ChatMessageService(
    val chatMessageRepository: ChatMessageRepository,
    val chatRoomRepository: ChatRoomRepository,
    val memberRepository: MemberRepository,
) {
    fun saveMessage(message: ChatMessageDto): String {
        val findChatRoom: ChatRoom = chatRoomRepository.findByIdOrNull(message.chatRoom)!!
        val findSender: Member = memberRepository.findByIdOrNull(message.sender)!!
        if (findSender == null || findChatRoom == null)
            {
                return "잘못된 아이디거나 채팅방입니다"
            } else {
            val chatMessageEntity = ChatMessage(message.id, findChatRoom, findSender, message.content, LocalDateTime.now())
            chatMessageRepository.save(chatMessageEntity)
        }
        return "메시지가 저장되었습니다."
    }
}
