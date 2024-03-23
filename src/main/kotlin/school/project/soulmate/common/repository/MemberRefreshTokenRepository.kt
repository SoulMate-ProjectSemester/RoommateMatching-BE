package school.project.soulmate.common.repository

import org.springframework.data.jpa.repository.JpaRepository
import school.project.soulmate.common.entity.MemberRefreshToken

interface MemberRefreshTokenRepository : JpaRepository<MemberRefreshToken, Long>