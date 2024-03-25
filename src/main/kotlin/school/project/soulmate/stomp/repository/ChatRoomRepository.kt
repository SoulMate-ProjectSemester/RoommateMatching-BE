package school.project.soulmate.stomp.repository

import org.springframework.data.jpa.repository.JpaRepository
import school.project.soulmate.stomp.entity.ChatRoom
import java.util.UUID

interface ChatRoomRepository : JpaRepository<ChatRoom, UUID>
