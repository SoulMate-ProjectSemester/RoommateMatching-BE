package school.project.soulmate.stomp.entity

import jakarta.persistence.*
import school.project.soulmate.member.entity.Member
import java.time.LocalDate

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_member_login_id", columnNames = ["loginId"])]
)
class User_Room (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @ManyToOne // 다른 엔터티를 참조하는 경우
    @JoinColumn(name = "fk_UserId") // 외래 키 컬럼명 지정
    var UserId: Member, // 외래 키 관계를 설정할 엔터티 타입

    @ManyToOne // 다른 엔터티를 참조하는 경우
    @JoinColumn(name = "fk_RoomId") // 외래 키 컬럼명 지정
    var RoomId: Room, // 외래 키 관계를 설정할 엔터티 타입

    @Column(nullable = false, length = 30, updatable = false)
    val loginId: String,

    @Column(nullable = false, length = 100)
    val password: String,

    )