package school.project.soulmate.stomp.repository

import org.springframework.data.jpa.repository.JpaRepository
import school.project.soulmate.stomp.entity.ChatMessage

interface ChatMessageRepository : JpaRepository<ChatMessage, Long>
