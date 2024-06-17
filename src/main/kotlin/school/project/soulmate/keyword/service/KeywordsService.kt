package school.project.soulmate.keyword.service

import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import school.project.soulmate.common.exception.InvalidInputException
import school.project.soulmate.keyword.dto.KeywordDtoResponse
import school.project.soulmate.keyword.dto.KeywordsDto
import school.project.soulmate.keyword.entity.Keywords
import school.project.soulmate.keyword.repository.KeywordsRepository
import school.project.soulmate.member.entity.Member
import school.project.soulmate.member.repository.MemberRepository

@Service
class KeywordsService(
    private val keywordsRepository: KeywordsRepository,
    private val memberRepository: MemberRepository,
) {
    @Transactional
    fun saveKeyword(
        keywordDto: KeywordsDto,
        userId: Long?,
    ): String {
        val findMember: Member = memberRepository.findByIdOrNull(userId) ?: throw InvalidInputException("유저가 존재하지 않습니다.")

        // Create and save each Keyword individually
        val newKeywords = KeywordsDto.toEntity(keywordDto, findMember)
        keywordsRepository.saveAll(newKeywords)

        return "키워드가 저장되었습니다"
    }

    fun findKeyword(userId: Long?): KeywordDtoResponse {
        val findMember: Member = memberRepository.findByIdOrNull(userId) ?: throw InvalidInputException("유저가 존재하지 않습니다.")
        val keywords: List<Keywords> = keywordsRepository.findByMember(findMember)

        return KeywordDtoResponse(
            memberId = findMember.id,
            keywordSet = keywords.map { it.value }.toSet()
        )
    }

    @Transactional
    fun updateKeyword(
        keywordDto: KeywordsDto,
        userId: Long?,
    ): String {
        val member: Member = memberRepository.findByIdOrNull(userId) ?: throw InvalidInputException("유저가 존재하지 않습니다.")

        // Remove existing keywords
        keywordsRepository.deleteByMember(member)

        // Add new keywords
        val newKeywords = KeywordsDto.toEntity(keywordDto, member)
        keywordsRepository.saveAll(newKeywords)

        return "키워드가 업데이트되었습니다"
    }
}
