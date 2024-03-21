package school.project.soulmate.stomp.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import school.project.soulmate.stomp.dto.ChatRoomDto
import school.project.soulmate.stomp.repository.ChatRoomRepository

@Service
class ChatRoomService(
    val chatRoomRepository: ChatRoomRepository,
) {
    @Transactional
    fun createChatRoom(chatRoomDto: ChatRoomDto): String {
        val chatRooms = chatRoomDto.toEntityList()
        chatRoomRepository.saveAll(chatRooms)
        return "채팅방이 생성되었습니다"
    }
}
