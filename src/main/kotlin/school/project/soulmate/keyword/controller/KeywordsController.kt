package school.project.soulmate.keyword.controller

import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import school.project.soulmate.common.dto.BaseResponse
import school.project.soulmate.common.dto.CustomUser
import school.project.soulmate.keyword.dto.KeywordDtoResponse
import school.project.soulmate.keyword.dto.KeywordsDto
import school.project.soulmate.keyword.service.KeywordsService

@RestController
@RequestMapping("/api/keyword")
class KeywordsController(
    private val keywordService: KeywordsService,
) {
    /**
     * 키워드 저장
     */
    @PostMapping("/new")
    fun saveKeyword(
        @RequestBody @Valid keywordsDTO: KeywordsDto,
    ): BaseResponse<Unit> {
        val userId: Long? = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val resultMsg: String = keywordService.saveKeyword(keywordsDTO, userId)
        return BaseResponse(message = resultMsg)
    }

    /**
     * 키워드 불러오기
     */
    @GetMapping("/keywords")
    fun findKeyword(): BaseResponse<KeywordDtoResponse> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val resultData = keywordService.findKeyword(userId)
        return BaseResponse(data = resultData)
    }

    /**
     * 키워드 수정하기
     */
    @PutMapping("/keywords")
    fun editKeyword(
        @RequestBody @Valid keywordsDto: KeywordsDto,
    ): BaseResponse<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val resultMsg = keywordService.updateKeyword(keywordsDto, userId)
        return BaseResponse(message = resultMsg)
    }
}
