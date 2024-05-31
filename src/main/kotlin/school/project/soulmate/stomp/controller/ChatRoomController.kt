package school.project.soulmate.stomp.controller

import lombok.RequiredArgsConstructor
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import school.project.soulmate.common.dto.BaseResponse
import school.project.soulmate.stomp.dto.ChatRoomDto
import school.project.soulmate.stomp.dto.ChatRoomInfoDto
import school.project.soulmate.stomp.dto.LeaveRoomDto
import school.project.soulmate.stomp.entity.ChatRoom
import school.project.soulmate.stomp.service.ChatRoomService
import java.util.UUID

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/room")
class ChatRoomController(
    val chatRoomService: ChatRoomService,
) {
    /**
     * 채팅방 생성
     */
    @PostMapping("/new")
    fun createRoom(
        @RequestBody chatRoomDto: ChatRoomDto,
    ): BaseResponse<ChatRoom> {
        val newChatRoom = chatRoomService.createChatRoom(chatRoomDto)
        return BaseResponse(data = newChatRoom)
    }

    /**
     * 단일 채팅방 조회
     */
    @GetMapping("/search/{roomId}")
    fun chatRoom(
        @PathVariable roomId: UUID,
    ): BaseResponse<ChatRoomInfoDto>  {
        return BaseResponse(data = chatRoomService.findRoom(roomId))
    }

    /**
     * 모든 채팅방 조회
     */
    @GetMapping("/rooms")
    fun rooms(
        @RequestParam("loginId") loginId: String,
    ): BaseResponse<List<ChatRoomInfoDto>> {
        return BaseResponse(data = chatRoomService.findRooms(loginId))
    }

    /**
     * 채팅방 나가기
     */
    @DeleteMapping("/quit")
    fun quitRoom(
        @RequestBody leaveRoomDto: LeaveRoomDto,
    ): BaseResponse<Unit> {
        val resultMsg = chatRoomService.leaveChatRoom(leaveRoomDto)
        return BaseResponse(message = resultMsg)
    }
}
