package school.project.soulmate.stomp.controller

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import school.project.soulmate.stomp.dto.ChatRoomDto
import school.project.soulmate.stomp.entity.ChatRoom
import school.project.soulmate.stomp.repository.ChatRoomRepository
import school.project.soulmate.stomp.service.ChatRoomService

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
class ChatRoomController(
    val chatRoomService: ChatRoomService,
    val chatRoomRepository: ChatRoomRepository,
) {
    // 채팅방 생성
    @PostMapping("/room")
    fun createRoom(@RequestBody chatRoomDto: ChatRoomDto) {
        chatRoomService.createChatRoom(chatRoomDto)
    }

    // 채팅방 조회
    @GetMapping("/rooms")
    fun rooms(@RequestParam("loginId") loginId: String): List<ChatRoom>? {
        return chatRoomRepository.findAllByUserIdOrderByCreateDateAsc(loginId)
    }
}