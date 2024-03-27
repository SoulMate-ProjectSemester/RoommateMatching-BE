package school.project.soulmate.stomp.repository

import org.springframework.data.jpa.repository.JpaRepository
import school.project.soulmate.stomp.entity.ChatMessage
import school.project.soulmate.stomp.entity.ChatRoom

interface ChatMessageRepository : JpaRepository<ChatMessage, Long> {
    fun findAllByChatRoom(chatRoom: ChatRoom): List<ChatMessage>
}
