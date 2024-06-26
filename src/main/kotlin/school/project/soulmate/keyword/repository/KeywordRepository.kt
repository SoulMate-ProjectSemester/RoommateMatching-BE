package school.project.soulmate.keyword.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import school.project.soulmate.keyword.entity.Keyword
import school.project.soulmate.member.entity.Member

@Repository
interface KeywordRepository : JpaRepository<Keyword, Long> {
    fun findByMember(member: Member): Keyword?
}
