package school.project.soulmate.gptApi.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import school.project.soulmate.member.entity.Member
import java.time.LocalDate

@Entity
class UserThread(
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,
    val threadId: String,

    @Column(length = 1000)
    val userMessage: String,
    val createDate: LocalDate,
)
