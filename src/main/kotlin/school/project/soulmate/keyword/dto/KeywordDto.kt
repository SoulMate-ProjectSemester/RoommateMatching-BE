package school.project.soulmate.keyword.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import lombok.Getter
import school.project.soulmate.keyword.entity.Keyword
import school.project.soulmate.member.entity.Member

@Getter
data class KeywordDto(
    var id: Long?,
    @field:NotEmpty
    @field:Size(min = 3, max = 10) // keywordSet의 크기가 최소 3개, 최대 10개임을 지정
    @JsonProperty("keywordSet")
    val keywordSet: MutableSet<String>,
) {
    fun toEntity(member: Member): Keyword = Keyword(id, member, keywordSet)
}

data class KeywordDtoResponse(
    val member: Long,
    val keywordSet: MutableSet<String>,
)
