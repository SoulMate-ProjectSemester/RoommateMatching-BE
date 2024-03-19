package school.project.soulmate.stomp.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import school.project.soulmate.stomp.entity.ChatRoom

@Repository
interface ChatRoomRepository : JpaRepository<ChatRoom, Long> {
    fun findAllByUserIdOrderByCreateDateAsc(userId: String): List<ChatRoom>?
}