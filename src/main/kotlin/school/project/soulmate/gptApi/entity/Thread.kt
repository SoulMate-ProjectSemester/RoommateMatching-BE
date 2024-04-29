package school.project.soulmate.gptApi.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import school.project.soulmate.member.entity.Member
import java.time.LocalDate

@Entity
class Thread(
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,
    val threadId: String,
    val createDate: LocalDate,
)
