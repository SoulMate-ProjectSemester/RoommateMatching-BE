package school.project.soulmate.stomp.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_member_login_id", columnNames = ["loginId"])]
)
class Room (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false, length = 30, updatable = false)
    val loginId: String,

    @Column(nullable = false, length = 100)
    val password: String,

    @Column(nullable = false, length = 10)
    val user_name: String,

    @Column(nullable = false, length = 10)
    val Room_name: String,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val CreationDate: LocalDate,

    @Column(nullable = false, length = 30)
    val email: String,

    )
