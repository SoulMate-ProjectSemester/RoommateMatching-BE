package school.project.soulmate.gptApi.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import school.project.soulmate.member.entity.Member
import java.time.LocalDate
import java.util.UUID

@Entity
class AssistantEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,
    val assistantId: UUID,
    val createDate: LocalDate,
)
