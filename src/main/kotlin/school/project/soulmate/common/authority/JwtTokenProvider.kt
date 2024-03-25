package school.project.soulmate.common.authority

import io.jsonwebtoken.*
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import school.project.soulmate.common.dto.CustomUser
import java.util.Date

const val ACCESS_EXPIRATION_MILLISECONDS: Long = 1000 * 60 * 30 // 30분
//const val ACCESS_EXPIRATION_MILLISECONDS: Long = 1000 * 2 // 20초
const val REFRESH_EXPIRATION_MILLISECONDS: Long = 1000 * 60 * 60 * 24 // 24시간

@Component
class JwtTokenProvider {
    @Value("\${jwt.secret}")
    lateinit var secretKey: String

    private val key by lazy { Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey)) }

    /**
     * Access Token 생성
     */
    fun createAccessToken(authentication: Authentication): String {
        val authorities: String =
            authentication
                .authorities
                .joinToString(",", transform = GrantedAuthority::getAuthority)

        val now = Date()
        val accessExpiration = Date(now.time + ACCESS_EXPIRATION_MILLISECONDS)

        // Access Token
        return Jwts
            .builder()
            .setSubject(authentication.name)
            .claim("auth", authorities)
            .claim("userId", (authentication.principal as CustomUser).userId)
            .setIssuedAt(now)
            .setExpiration(accessExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    /**
     * Refresh token 생성
     */

    fun createRefreshToken(authentication: Authentication): String {
        val authorities: String =
            authentication
                .authorities
                .joinToString(",", transform = GrantedAuthority::getAuthority)
        val now = Date()
        val refreshExpiration = Date(now.time + REFRESH_EXPIRATION_MILLISECONDS)

        return Jwts.builder()
            .setSubject(authentication.name)
            .claim("auth", authorities)
            .claim("userId", (authentication.principal as CustomUser).userId)
            .setIssuedAt(now)
            .setExpiration(refreshExpiration)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    /**
     * Token 정보 추출
     */

    fun getAuthentication(token: String): Authentication {
        val claims: Claims = getClaims(token)

        // claims에 있는 auth를 가져온다
        val auth = claims["auth"] ?: throw RuntimeException("잘못된 토큰입니다.")
        val userId = claims["userId"] ?: throw RuntimeException("잘못된 토큰입니다.")

        // 권한 정보 추출
        val authorities: Collection<GrantedAuthority> =
            (auth as String)
                .split(",")
                .map { SimpleGrantedAuthority(it) }

        val principal: UserDetails = CustomUser(userId.toString().toLong(), claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, "", authorities)
    }

    /**
     * Token 검증
     */
    fun validateToken(token: String): Boolean {
        try {
            getClaims(token)
            return true
        } catch (e: Exception) {
            when (e) {
                is SecurityException -> {}
                is MalformedJwtException -> {}
                is ExpiredJwtException -> {}
                is UnsupportedJwtException -> {}
                is IllegalArgumentException -> {}
                else -> {}
            }
            println(e.message)
        }
        return false
    }

    private fun getClaims(token: String): Claims =
        Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

    /**
     * 토큰 만료 확인
     */
    fun isTokenExpired(token: String): Boolean {
        val claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).body
        return claims.expiration.before(Date())
    }
}
