package school.project.soulmate.keyword.entity

import BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import school.project.soulmate.member.entity.Member

@Entity
class Keywords(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    var member: Member?,
    val value: String
) : BaseEntity()
