package school.project.soulmate.keyword.entity

import jakarta.persistence.ElementCollection
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import school.project.soulmate.member.entity.Member

@Entity
class Keyword(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,
    @ElementCollection(fetch = FetchType.LAZY)
    val keywordSet: MutableSet<String> = HashSet(),
)
