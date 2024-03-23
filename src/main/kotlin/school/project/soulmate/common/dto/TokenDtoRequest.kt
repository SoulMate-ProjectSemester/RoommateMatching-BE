package school.project.soulmate.common.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotBlank

data class TokenDtoRequest(
    @field:NotBlank
    @JsonProperty("expiredMemberId")
    val _memberId: Long?,
    @field:NotBlank
    @JsonProperty("accessToken")
    val accessToken: String,
    @field:NotBlank
    @JsonProperty("refreshToken")
    val refreshToken: String,
) {
    val memberId: Long
        get() = _memberId!!
}
