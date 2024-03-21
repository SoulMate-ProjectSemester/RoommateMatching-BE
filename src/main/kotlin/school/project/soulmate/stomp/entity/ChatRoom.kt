package school.project.soulmate.stomp.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class ChatRoom(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @Column(nullable = false, length = 36, updatable = false)
    val roomId: String,
    @Column(nullable = false, length = 10)
    val userId: String,
    @Column(length = 15)
    val roomName: String?,
    @Column(nullable = false)
    val createDate: LocalDate,
)
