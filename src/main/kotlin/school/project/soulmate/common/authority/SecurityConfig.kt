package school.project.soulmate.common.authority

import org.springframework.boot.autoconfigure.security.servlet.PathRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtTokenProvider: JwtTokenProvider,
) {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // 세션을 사용하지 않음
            .authorizeHttpRequests {
                @Suppress("ktlint:standard:max-line-length")
                it.requestMatchers("/api/member/signup", "/api/member/login", "/api/member/info_edit", "/api/member/chat_list", "/api/member/chat", "/api/member/keyword").anonymous() // 해당 url에 접속하는 사용자는 인증되지 않은 사용자.
                    .requestMatchers("/api/member/**").hasRole("MEMBER") // 멤버 권한이 있어야 들어갈 수 있음
                    .requestMatchers("/ws-stomp/**").permitAll()
                    .requestMatchers("/api/auth/refresh").permitAll()
                    .anyRequest().permitAll() // 나머지 url은 접근 가능
            }
            .addFilterBefore( // 필터 순서 설정. 앞에 필터가 실행되야 뒤에 있는 필터가 실행된다
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java,
            )

        return http.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder()

    // 정적 파일 적용
    @Bean
    fun webSecurityCustomizer(): WebSecurityCustomizer {
        return WebSecurityCustomizer { web: WebSecurity ->
            web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
        }
    }
}