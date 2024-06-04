package school.project.soulmate.keyword.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import school.project.soulmate.common.exception.InvalidInputException
import school.project.soulmate.keyword.dto.KeywordDto
import school.project.soulmate.keyword.dto.KeywordDtoResponse
import school.project.soulmate.keyword.entity.Keyword
import school.project.soulmate.keyword.repository.KeywordRepository
import school.project.soulmate.member.entity.Member
import school.project.soulmate.member.repository.MemberRepository

@Service
class KeywordService(
    private val keywordRepository: KeywordRepository,
    private val memberRepository: MemberRepository,
) {
    @Transactional
    fun saveKeyword(
        keywordDto: KeywordDto,
        userId: Long?,
    ): String {
        val findMember: Member = memberRepository.findByIdOrNull(userId) ?: throw InvalidInputException("유저가 존재하지 않습니다.")

        val keyword: Keyword = keywordDto.toEntity(findMember)
        keywordRepository.save(keyword)
        return "키워드가 저장되었습니다"
    }

    fun findKeyword(memberId: Long?): KeywordDtoResponse {
        val findMember: Member = memberRepository.findByIdOrNull(memberId) ?: throw InvalidInputException("유저가 존재하지 않습니다.")
        val keyword: Keyword? = keywordRepository.findByMember(findMember)

        return KeywordDtoResponse(
            member = findMember.id,
            keywordSet = keyword?.keywordSet,
        )
    }

    @Transactional
    fun updateKeyword(
        keywordDto: KeywordDto,
        userId: Long?,
    ): String {
        val member = memberRepository.findByIdOrNull(userId) ?: throw InvalidInputException("유저가 존재하지 않습니다.")
        val keyword = keywordRepository.findByMember(member) ?: throw InvalidInputException("유저의 키워드가 존재하지 않습니다.")

        // keywordSet 업데이트
        keyword.keywordSet.clear()
        keyword.keywordSet.addAll(keywordDto.keywordSet)

        // 필요한 경우, 다른 필드 업데이트
        // 예: keyword.member = member

        // 변경 사항 저장
        keywordRepository.save(keyword)

        return "키워드가 업데이트되었습니다"
    }
}
