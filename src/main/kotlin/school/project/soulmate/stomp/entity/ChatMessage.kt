package school.project.soulmate.stomp.entity

import jakarta.persistence.*
import lombok.Getter
import java.time.LocalDateTime

@Entity
@Getter
class ChatMessage (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false, length = 30)
    var senderId: String, // 외래 키 관계를 설정할 엔터티 타입

    @Column(nullable = false, length = 36)
    var chatRoomId: String, // 외래 키 관계를 설정할 엔터티 타입

    @Column(nullable = false, length = 300)
    val messageText: String,

    @Column(nullable = false)
    val messageTime: LocalDateTime,
    )
