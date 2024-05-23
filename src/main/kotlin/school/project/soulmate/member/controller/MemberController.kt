package school.project.soulmate.member.controller

import jakarta.validation.Valid
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView
import school.project.soulmate.common.authority.TokenInfo
import school.project.soulmate.common.dto.BaseResponse
import school.project.soulmate.common.dto.CustomUser
import school.project.soulmate.member.dto.LoginDto
import school.project.soulmate.member.dto.MemberDtoRequest
import school.project.soulmate.member.dto.MemberDtoResponse
import school.project.soulmate.member.service.MemberService

@RestController
@RequestMapping("/api/member")
class MemberController(
    private val memberService: MemberService,
) {
    /**
     * 회원가입
     */
    @PostMapping("/signup")
    fun signUp(
        @RequestBody @Valid memberDtoRequest: MemberDtoRequest,
    ): BaseResponse<Unit> {
        val resultMsg: String = memberService.signUp(memberDtoRequest)
        return BaseResponse(message = resultMsg)
    }

    /**
     * 로그인
     */
    @GetMapping("/login")
    fun login(): ModelAndView {
        val modelAndView = ModelAndView()
        modelAndView.viewName = "signin_signup"

        val memberDtoRequest: MemberDtoRequest? = null
        modelAndView.addObject("memberDtoRequest", memberDtoRequest)

        return modelAndView
    }

    @PostMapping("/login")
    fun login(
        @RequestBody @Valid loginDto: LoginDto,
    ): BaseResponse<TokenInfo> {
        val tokenInfo = memberService.login(loginDto)
        return BaseResponse(data = tokenInfo)
    }

    /**
     * 내 정보 보기
     */
    @GetMapping("/info")
    fun searchMyInfo(): BaseResponse<MemberDtoResponse> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        val response = memberService.searchMyInfo(userId)
        return BaseResponse(data = response)
    }

    /**
     * 내 정보 수정
     */
    @PutMapping("/info_edit")
    fun saveMyInfo(
        @RequestBody @Valid memberDtoRequest: MemberDtoRequest,
    ): BaseResponse<Unit> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as CustomUser).userId
        memberDtoRequest.id = userId
        val resultMsg: String = memberService.saveMyInfo(memberDtoRequest)
        return BaseResponse(message = resultMsg)
    }

    @GetMapping("/info_edit")
    fun info(): ModelAndView {
        val modelAndView = ModelAndView()
        modelAndView.viewName = "profileEdit"

        val memberDtoRequest: MemberDtoRequest? = null
        modelAndView.addObject("memberDtoRequest", memberDtoRequest)
        return modelAndView
    }

    // 채팅방 리스트 보기
    @GetMapping("/chat_list")
    fun chatList(): ModelAndView {
        val modelAndView = ModelAndView()
        modelAndView.viewName = "ChatList"
        return modelAndView
    }

    /**
     * 로그아웃
     */
    @DeleteMapping("/logout")
    fun logout(
        @RequestParam("loginId") loginId: Long,
    ): BaseResponse<Unit> {
        val resultMsg: String = memberService.deleteRefToken(loginId)
        return BaseResponse(message = resultMsg)
    }

    //keyword 입력페이지를 위한 임시 controller
    @GetMapping("/keyword")
    fun keywordSelect(): ModelAndView {
        val modelAndView = ModelAndView()
        modelAndView.viewName = "keywordSelect"
        return modelAndView
    }

    //keyword 수정페이지를 위한 임시 controller
    @GetMapping("/keyword_edit")
    fun keywordEdit():ModelAndView{
        val modelAndView=ModelAndView()
        modelAndView.viewName="keywordEdit"
        return modelAndView
    }
}
