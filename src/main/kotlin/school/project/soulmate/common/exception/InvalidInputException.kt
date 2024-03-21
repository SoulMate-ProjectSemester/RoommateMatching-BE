package school.project.soulmate.common.exception

// @Valid 외에 필드값이 문제가 있어서 exception 을 발생시킬때 사용
class InvalidInputException (
    val fieldName: String = "",
    message: String = "Invalid Input"
) : RuntimeException(message)
