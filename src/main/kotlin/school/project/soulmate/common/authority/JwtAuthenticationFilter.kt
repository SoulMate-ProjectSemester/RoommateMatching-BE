package school.project.soulmate.common.authority

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean

class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
) : GenericFilterBean() {

    private val logger = LoggerFactory.getLogger(JwtAuthenticationFilter::class.java)

    override fun doFilter(
        request: ServletRequest?,
        response: ServletResponse?,
        chain: FilterChain?,
    ) {
        val token = resolveToken(request as HttpServletRequest)
        if(token != null){
            logger.info("JWT Token found: $token")
            if(jwtTokenProvider.validateToken(token)){
                val authentication = jwtTokenProvider.getAuthentication(token)
                logger.info("Authenticated user: ${authentication.name}, authorities: ${authentication.authorities}")
                SecurityContextHolder.getContext().authentication = authentication
            } else {
                logger.info("Invalid JWT token")
            }
        } else {
            logger.info("No JWT token found in request headers")
        }
        chain?.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader("Authorization")

        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer"))
            {
                bearerToken.substring(7) // 뒤에 있는 키값만 가져옴
            } else {
            null
        }
    }
}
