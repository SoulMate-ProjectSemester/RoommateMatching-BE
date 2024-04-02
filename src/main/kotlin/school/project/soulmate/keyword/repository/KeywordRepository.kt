package school.project.soulmate.keyword.repository

import org.springframework.data.jpa.repository.JpaRepository
import school.project.soulmate.keyword.entity.Keyword
import school.project.soulmate.member.entity.Member

interface KeywordRepository : JpaRepository<Keyword, Long> {
    fun findOneByMember(memberId: Member): Keyword

    fun findByMember(member: Member): Keyword?
}
