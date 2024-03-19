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
    fun createChatRoom(chatRoomDto: ChatRoomDto) {
        val chatRooms = chatRoomDto.toEntityList()
        chatRoomRepository.saveAll(chatRooms)
    }
}