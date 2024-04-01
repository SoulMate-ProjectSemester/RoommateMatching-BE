package school.project.soulmate.stomp.controller

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import school.project.soulmate.common.dto.BaseResponse
import school.project.soulmate.stomp.dto.ChatRoomDto
import school.project.soulmate.stomp.dto.ChatRoomInfoDto
import school.project.soulmate.stomp.dto.LeaveRoomDto
import school.project.soulmate.stomp.entity.ChatRoom
import school.project.soulmate.stomp.service.ChatRoomService
import java.util.*

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
    ): BaseResponse<ChatRoom> {
        val newChatRoom = chatRoomService.createChatRoom(chatRoomDto)
        return BaseResponse(data = newChatRoom)
    }

    // 채팅방 화면
    @GetMapping("/{roomId}")
    fun chat(
        @PathVariable roomId: UUID,
    ): ModelAndView {
        val modelAndView = ModelAndView()
        modelAndView.viewName = "chat"
        return modelAndView
    }

    // 채팅방 조회
    @GetMapping("/rooms")
    fun rooms(
        @RequestParam("loginId") loginId: String,
    ): BaseResponse<List<ChatRoomInfoDto>> {
        return BaseResponse(data = chatRoomService.findRooms(loginId))
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
