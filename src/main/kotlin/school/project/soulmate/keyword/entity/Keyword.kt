package school.project.soulmate.keyword.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import school.project.soulmate.member.entity.Member

@Entity
class Keyword(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    var member: Member,

    @OneToMany(mappedBy = "keyword", cascade = [CascadeType.ALL], fetch = FetchType.LAZY, orphanRemoval = true)
    val keywords: MutableSet<KeywordDetail> = HashSet()
)

@Entity
class KeywordDetail(
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "keyword_id")
    var keyword: Keyword?,
    val value: String
)

