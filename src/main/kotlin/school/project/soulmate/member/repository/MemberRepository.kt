package school.project.soulmate.member.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import school.project.soulmate.common.status.Gender
import school.project.soulmate.member.entity.Member
import school.project.soulmate.member.entity.MemberRole

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByLoginId(loginId: String): Member?
    @Query("SELECT m FROM Member m WHERE m.gender = :gender AND m.id <> :memberId ORDER BY function('RAND')")
    fun findRandomMemberSameGender(@Param("gender") gender: Gender, @Param("memberId") memberId: Long, pageable: Pageable): List<Member>
}

interface MemberRoleRepository : JpaRepository<MemberRole, Long>
