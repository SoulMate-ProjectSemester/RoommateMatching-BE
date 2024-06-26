package school.project.soulmate.stomp.controller

import lombok.RequiredArgsConstructor
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import school.project.soulmate.common.dto.BaseResponse
import school.project.soulmate.stomp.dto.ChatMessageDto
import school.project.soulmate.stomp.dto.ChatMessageDtoResponse
import school.project.soulmate.stomp.service.ChatMessageService
import java.util.UUID

@RequiredArgsConstructor
@RestController
class ChatController(
    val messagingTemplate: SimpMessageSendingOperations,
    val chatMessageService: ChatMessageService,
) {
    @MessageMapping("/{roomId}") // request: /pub/{roomId}
    fun message(
        @DestinationVariable roomId: UUID,
        message: ChatMessageDto,
    ): ChatMessageDto {
        chatMessageService.saveMessage(roomId, message)
        messagingTemplate.convertAndSend("/sub/$roomId", message)
        return message
    }

    /**
     * 메시지 가져오기
     */
    @GetMapping("/api/chat/messages")
    fun getMessages(
        @RequestParam("roomId") roomId: UUID,
    ): BaseResponse<List<ChatMessageDtoResponse>> {
        var resultMsg = chatMessageService.loadMessages(roomId)
        return BaseResponse(data = resultMsg)
    }
}
