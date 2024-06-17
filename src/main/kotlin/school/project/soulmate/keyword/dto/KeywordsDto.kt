package school.project.soulmate.keyword.dto

import school.project.soulmate.keyword.entity.Keywords
import school.project.soulmate.member.entity.Member

data class KeywordsDto(
    val id: Long?,
    val keywordId: Long?,
    val keywordSet: Set<String>?
) {
    companion object {
        fun toEntity(dto: KeywordsDto, member: Member?): List<Keywords> {
            return dto.keywordSet?.map { keywordValue ->
                Keywords(
                    id = dto.id,
                    member = member,
                    value = keywordValue
                )
            } ?: emptyList()
        }
    }
}

data class KeywordDtoResponse(
    val member: Long?,
    val keywordSet: Set<String>?,
)
