package school.project.soulmate.common.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import school.project.soulmate.common.authority.TokenInfo
import school.project.soulmate.common.dto.BaseResponse
import school.project.soulmate.common.dto.TokenDtoRequest
import school.project.soulmate.common.service.SignService

@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val signService: SignService,
) {
    @PostMapping("/refresh")
    fun refreshAccessToken(
        @RequestBody tokenRequestDto: TokenDtoRequest,
    ): BaseResponse<TokenInfo> {
        val newAccessToken = signService.newAccessToken(tokenRequestDto)
        return BaseResponse(data = newAccessToken)
    }
}
