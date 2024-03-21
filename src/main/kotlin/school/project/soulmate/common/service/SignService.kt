package school.project.soulmate.common.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import school.project.soulmate.common.authority.JwtTokenProvider
import school.project.soulmate.common.authority.TokenInfo
import school.project.soulmate.common.dto.TokenDtoRequest
import school.project.soulmate.common.entity.MemberRefreshToken
import school.project.soulmate.common.exception.InvalidInputException
import school.project.soulmate.common.repository.MemberRefreshTokenRepository
import school.project.soulmate.member.entity.Member

@Service
class SignService(
    private val memberRefreshTokenRepository: MemberRefreshTokenRepository,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    @Transactional
    fun saveRefreshToken(
        member: Member,
        refreshToken: String,
    ) {
        memberRefreshTokenRepository.save(MemberRefreshToken(member, refreshToken))
    }

    fun newAccessToken(tokenDtoRequest: TokenDtoRequest): TokenInfo {
        val findRefreshToken = memberRefreshTokenRepository.findById(tokenDtoRequest.memberId).get().refreshToken

        // Refresh token 검증
        if (!jwtTokenProvider.validateToken(findRefreshToken)) {
            throw InvalidInputException("로그인이 만료되었습니다.")
        }

        if (tokenDtoRequest.refreshToken == findRefreshToken) {
            // Refresh token으로부터 사용자 정보 추출
            val authentication = jwtTokenProvider.getAuthentication(findRefreshToken)
            // 새로운 Access token 생성
            val newAccessToken = jwtTokenProvider.createAccessToken(authentication)

            return TokenInfo("Bearer", newAccessToken, findRefreshToken)
        } else {
            throw InvalidInputException("다시 로그인해주세요")
        }
    }
}
