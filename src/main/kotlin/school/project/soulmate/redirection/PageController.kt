package school.project.soulmate.redirection

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import school.project.soulmate.member.dto.MemberDtoRequest

@Controller
@RequestMapping("/api/page")
class PageController {
    /**
     * 메인 페이지
     */
    @GetMapping("/main")
    fun mainPage(): String {
        return "index"
    }

    /**
     * 내 정보 수정
     */
    @GetMapping("/info_edit")
    fun info(model: Model): String {
        val memberDtoRequest: MemberDtoRequest? = null
        model.addAttribute("memberDtoRequest", memberDtoRequest)
        return "profileEdit"
    }

    /**
     * 채팅방 리스트
     */
    @GetMapping("/chat_list")
    fun chatList(): String {
        return "ChatList"
    }

    /**
     * 키워드
     */
    @GetMapping("/keyword")
    fun keywordSelect(): String {
        return "keywordSelect"
    }

    @GetMapping("/keyword_edit")
    fun keywordEdit(): String {
        return "keywordEdit"
    }
}
