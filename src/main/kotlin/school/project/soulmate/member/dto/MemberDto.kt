package school.project.soulmate.member.dto

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import school.project.soulmate.common.annotation.ValidEnum
import school.project.soulmate.common.status.Gender
import school.project.soulmate.member.entity.Member
import java.time.LocalDate
import java.time.format.DateTimeFormatter

data class MemberDtoRequest(
    var id: Long?,

    @field:NotBlank
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @field:Pattern(
        regexp="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}\$",
        message = "영문, 숫자, 특수문자를 포함한 8~20자리로 입력해주세요"
    )
    @JsonProperty("password")
    private val _password: String?,

    @field:NotBlank
    @JsonProperty("name")
    private val _name: String?,

    @field:NotBlank
    @field:Pattern(
        regexp = "^([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))$",
        message = "날짜형식(YYYY-MM-DD)을 확인해주세요"
    )
    @JsonProperty("birthDate")
    private val _birthDate: String?,

    @field:NotBlank
    @field:ValidEnum(enumClass = Gender::class,
        message = "MAN 이나 WOMAN 중 하나를 선택해주세요")
    @JsonProperty("gender")
    private val _gender: String?,

    @field:NotBlank
    @JsonProperty("studentNumber")
    private val _studentNumber: String?,

    @field:NotBlank
    @JsonProperty("college")
    private val _college: String?,

    @field:NotBlank
    @JsonProperty("major")
    private val _major: String?,

    @field:NotBlank
    @field:Email
    @JsonProperty("email")
    private val _email: String?,
) {
    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
    val name: String
        get() = _name!!
    private val birthDate: LocalDate
        get() = _birthDate!!.toLocalDate()
    private val gender: Gender
        get() = Gender.valueOf(_gender!!)
    private val studentNumber: String
        get() = _studentNumber!!
    private val college: String
        get() = _college!!
    private val major: String
        get() = _major!!
    val email: String
        get() = _email!!
    private fun String.toLocalDate(): LocalDate =
        LocalDate.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    fun toEntity(): Member = Member(id, loginId, password, name, birthDate, gender, studentNumber, college, major, email)
}

data class LoginDto(
    @field:NotBlank
    @JsonProperty("loginId")
    private val _loginId: String?,

    @field:NotBlank
    @JsonProperty("password")
    private val _password: String?,
) {
    val loginId: String
        get() = _loginId!!
    val password: String
        get() = _password!!
}

data class MemberDtoResponse(
    val id: Long,
    val loginId: String,
    val name: String,
    val birthDate: String,
    val gender: String,
    val studentNumber: String,
    val college: String,
    val major: String,
    val email: String,
)
