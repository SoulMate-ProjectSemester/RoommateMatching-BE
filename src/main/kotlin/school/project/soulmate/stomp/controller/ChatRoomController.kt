package school.project.soulmate.stomp.controller

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import school.project.soulmate.common.dto.BaseResponse
import school.project.soulmate.stomp.dto.ChatRoomDto
import school.project.soulmate.stomp.dto.ChatRoomMemberDto
import school.project.soulmate.stomp.dto.LeaveRoomDto
import school.project.soulmate.stomp.service.ChatRoomService

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/room")
class ChatRoomController(
    val chatRoomService: ChatRoomService,
) {
    // 채팅방 생성
    @PostMapping("/new")
    fun createRoom(
        @RequestBody chatRoomDto: ChatRoomDto,
    ): BaseResponse<Unit> {
        val resultMsg = chatRoomService.createChatRoom(chatRoomDto)
        return BaseResponse(message = resultMsg)
    }

    // 채팅방 조회
    @GetMapping("/rooms")
    fun rooms(
        @RequestParam("loginId") loginId: String,
    ): List<ChatRoomMemberDto>? {
        return chatRoomService.findRooms(loginId)
    }

    // 채팅방 나가기
    @DeleteMapping("/quit")
    fun quitRoom(
        @RequestBody leaveRoomDto: LeaveRoomDto,
    ): BaseResponse<Unit> {
        val resultMsg = chatRoomService.leaveChatRoom(leaveRoomDto)
        return BaseResponse(message = resultMsg)
    }
}
