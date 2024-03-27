package school.project.soulmate.stomp.controller

import lombok.RequiredArgsConstructor
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import school.project.soulmate.common.dto.BaseResponse
import school.project.soulmate.stomp.dto.ChatMessageDto
import school.project.soulmate.stomp.dto.ChatMessageResponseDto
import school.project.soulmate.stomp.service.ChatMessageService
import java.util.*

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/chat")
class ChatController(
    val messagingTemplate: SimpMessageSendingOperations,
    val chatMessageService: ChatMessageService,
) {
    @MessageMapping("/message")
    fun message(message: ChatMessageDto): BaseResponse<Unit> {
        var resultMsg =
            if (message == null) {
                "잘못된 메시지 입니다."
            } else {
                chatMessageService.saveMessage(message)
            }
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.chatRoom, message)
        return BaseResponse(message = resultMsg)
    }

    @GetMapping("/messages")
    fun getMessages(
        @RequestParam("roomId") roomId: UUID,
    ): BaseResponse<List<ChatMessageResponseDto>> {
        var resultMsg = chatMessageService.loadMessages(roomId)
        return BaseResponse(data = resultMsg)
    }
}
