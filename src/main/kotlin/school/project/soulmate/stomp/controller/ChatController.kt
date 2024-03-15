package school.project.soulmate.stomp.controller

import lombok.RequiredArgsConstructor
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import school.project.soulmate.stomp.dto.ChatMessageDto
import school.project.soulmate.stomp.repository.ChatMessageRepository

@RequiredArgsConstructor
@RestController
@RequestMapping
class ChatController(
    val messagingTemplate: SimpMessageSendingOperations,
    val chatMessageRepository: ChatMessageRepository,

) {
    @MessageMapping("/message")
    fun message(message: ChatMessageDto) {
        // DTO를 엔터티로 변환하는 로직 필요
        val chatMessage = message.toEntity()
        chatMessageRepository.save(chatMessage) // 메시지 저장
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.chatRoomId, message)
    }
}