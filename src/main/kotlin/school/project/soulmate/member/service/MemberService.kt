package school.project.soulmate.member.service

import school.project.soulmate.common.authority.JwtTokenProvider
import school.project.soulmate.common.authority.TokenInfo
import school.project.soulmate.common.exception.InvalidInputException
import school.project.soulmate.common.status.ROLE
import school.project.soulmate.member.entity.Member
import school.project.soulmate.member.entity.MemberRole
import school.project.soulmate.member.repository.MemberRepository
import school.project.soulmate.member.repository.MemberRoleRepository
import school.project.soulmate.member.dto.LoginDto
import school.project.soulmate.member.dto.MemberDtoRequest
import school.project.soulmate.member.dto.MemberDtoResponse

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val memberRoleRepository: MemberRoleRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    /**
     * 회원가입
     */
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        var member = memberRepository.findByLoginId(memberDtoRequest.loginId)
        if (member != null) {
            throw InvalidInputException("loginId", "이미 등록된 ID 입니다.")
        }
        member = memberDtoRequest.toEntity()
        memberRepository.save(member)

        val memberRole : MemberRole = MemberRole(null, ROLE.MEMBER, member)
        memberRoleRepository.save(memberRole)

        return "회원가입이 완료되었습니다."
    }

    /**
     * 로그인 -> 토큰 발행
     */
    fun login(loginDto: LoginDto): TokenInfo {  // 사용자에게 받은 정보를 TokenInfo에 담아서 전달
        val authenticationToken = UsernamePasswordAuthenticationToken(loginDto.loginId, loginDto.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)

        return jwtTokenProvider.createToken(authentication)
    }

    /**
     * 내정보 조회
     */
    fun searchMyInfo(id: Long): MemberDtoResponse {
        val member: Member = memberRepository.findByIdOrNull(id) ?: throw InvalidInputException("id", "회원번호(${id})가 존재하지 않습니다.")
        return member.toDto()
    }

    /**
     * 내 정보 수정
     */
    fun saveMyInfo(memberDtoRequest: MemberDtoRequest): String{
        val member: Member = memberDtoRequest.toEntity()
        memberRepository.save(member)
        return "수정 완료되었습니다."
    }
}
