package school.project.soulmate.member.repository

import school.project.soulmate.member.entity.Member
import school.project.soulmate.member.entity.MemberRole
import org.springframework.data.jpa.repository.JpaRepository

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByLoginId(loginId: String): Member?
}

interface MemberRoleRepository : JpaRepository<MemberRole, Long>
