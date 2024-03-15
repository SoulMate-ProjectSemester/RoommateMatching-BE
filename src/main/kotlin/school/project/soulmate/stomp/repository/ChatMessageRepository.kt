package school.project.soulmate.stomp.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import school.project.soulmate.stomp.entity.ChatMessage

@Repository
interface ChatMessageRepository : JpaRepository<ChatMessage, Long> {
}