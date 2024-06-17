package school.project.soulmate.keyword.repository


import org.springframework.data.jpa.repository.JpaRepository
import school.project.soulmate.keyword.entity.Keywords
import school.project.soulmate.member.entity.Member

interface KeywordsRepository : JpaRepository<Keywords, Long> {
    fun deleteByMember(member: Member)
    fun findByMember(member: Member): List<Keywords>
}