package school.project.soulmate.member.entity

import school.project.soulmate.common.status.Gender
import school.project.soulmate.common.status.ROLE
import school.project.soulmate.member.dto.MemberDtoResponse
import jakarta.persistence.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity
@Table(
    uniqueConstraints = [UniqueConstraint(name = "uk_member_login_id", columnNames = ["loginId"])]
)
class Member (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false, length = 30, updatable = false)
    val loginId: String,

    @Column(nullable = false, length = 100)
    val password: String,

    @Column(nullable = false, length = 10)
    val name: String,

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    val birthDate: LocalDate,

    @Column(nullable = false, length = 5)
    @Enumerated(EnumType.STRING)
    val gender: Gender,

    @Column(nullable = false, length = 15)
    val studentNumber: String,

    @Column(nullable = false, length = 30)
    val college: String,

    @Column(nullable = false, length = 30)
    val major: String,

    @Column(nullable = false, length = 30)
    val email: String,
    ){
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "member")
    val memberRole: List<MemberRole>? = null

    // birthDate를 string 으로 바꾸기 위한 함수
    private fun LocalDate.formatDate(): String =
        this.format(DateTimeFormatter.ofPattern("yyyyMMdd"))

    fun toDto(): MemberDtoResponse =
        MemberDtoResponse(id!!, loginId, name, birthDate.formatDate(), gender.desc, studentNumber, college, major, email)
}

@Entity
class MemberRole(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    val role: ROLE,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(foreignKey = ForeignKey(name = "fk_member_role_member_id"))
    val member: Member,
    )
