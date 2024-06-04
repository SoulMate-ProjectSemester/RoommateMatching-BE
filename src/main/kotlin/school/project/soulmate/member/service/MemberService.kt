package school.project.soulmate.member.service

import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import school.project.soulmate.common.authority.JwtTokenProvider
import school.project.soulmate.common.authority.TokenInfo
import school.project.soulmate.common.exception.InvalidInputException
import school.project.soulmate.common.repository.MemberRefreshTokenRepository
import school.project.soulmate.common.service.SignService
import school.project.soulmate.common.status.ROLE
import school.project.soulmate.member.dto.LoginDto
import school.project.soulmate.member.dto.MemberDtoRequest
import school.project.soulmate.member.dto.MemberDtoResponse
import school.project.soulmate.member.dto.MemberListDto
import school.project.soulmate.member.entity.Member
import school.project.soulmate.member.entity.MemberRole
import school.project.soulmate.member.repository.MemberRepository
import school.project.soulmate.member.repository.MemberRoleRepository

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
    private val signService: SignService,
    private val memberRefreshTokenRepository: MemberRefreshTokenRepository,
) {
    /**
     * 회원가입
     */
    @Transactional
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        var member = memberRepository.findByLoginId(memberDtoRequest.loginId)
        if (member != null) {
            throw InvalidInputException("loginId", "이미 등록된 ID 입니다.")
        }
        member = memberDtoRequest.toEntity()
        memberRepository.save(member)

        val memberRole = MemberRole(null, ROLE.MEMBER, member)
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다."
    }

    /**
     * 로그인 -> 토큰 발행
     */
    @Transactional
    fun login(loginDto: LoginDto): TokenInfo {
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.loginId, loginDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        // 권한 출력
        println("User Authorities: ${authentication.authorities}")

        val accessToken = jwtTokenProvider.createAccessToken(authentication)
        val refreshToken = jwtTokenProvider.createRefreshToken(authentication)

        val member = memberRepository.findByLoginId(loginDto.loginId)

        // Refresh Token 저장
        if (member != null) {
            signService.saveRefreshToken(member, refreshToken)
        }

        return TokenInfo("Bearer", accessToken, refreshToken)
    }

    /**
     * 내정보 조회
     */
    fun searchMyInfo(userId: Long?): MemberDtoResponse {
        val member: Member = memberRepository.findByIdOrNull(userId) ?: throw InvalidInputException("id", "회원번호($userId)가 존재하지 않습니다.")
        return member.toDto()
    }

    /**
     * 내 정보 수정
     */
    @Transactional
    fun saveMyInfo(memberDtoRequest: MemberDtoRequest): String {
        val member: Member = memberDtoRequest.toEntity()
        memberRepository.save(member)
        return "수정 완료되었습니다."
    }

    /**
     * 로그아웃
     */
    @Transactional
    fun deleteRefToken(loginId: Long): String {
        memberRefreshTokenRepository.deleteById(loginId)
        return "로그아웃 되었습니다."
    }

    /**
     * 친구추천
     */
    @Transactional
    fun findFriendsList(userId: Long?): List<MemberListDto> {
        val pageable: Pageable = PageRequest.of(0, 10)  // 첫 번째 페이지에서 10개의 항목을 요청
        val findMember: Member = memberRepository.findByIdOrNull(userId) ?: throw InvalidInputException("유저를 찾을 수 없습니다.")
        val members: List<Member> = memberRepository.findRandomMemberSameGender(findMember.gender, findMember.id, pageable)
        return members.map { member ->
            MemberListDto(
                id = member.id,
                name = member.name,
                loginId = member.loginId,
                studentNumber = member.studentNumber,
                major = member.major
            )
        }
    }

}
