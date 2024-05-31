
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
import org.springframework.security.web.AuthenticationEntryPoint
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
                it.requestMatchers(
                    "/api/member/signup",
                    "/api/member/login",
                    "/api/auth/refresh",
                ).anonymous() // 해당 url에 접속하는 사용자는 인증되지 않은 사용자.
                    .requestMatchers(
                        "/api/member/**",
                        "/api/keyword/**",
                        "/api/room/**",
                        "/api/chat/**",
                    ).hasRole("MEMBER")
                    .requestMatchers("/ws-stomp/**").permitAll()
                    .requestMatchers("/api/page/**").permitAll()
            }
            // 로그인 인증하지 않은 사용자 URL 리디렉션
            .exceptionHandling {it.authenticationEntryPoint(customAuthenticationEntryPoint())}
            .addFilterBefore(
                // 필터 순서 설정. 앞에 필터가 실행되야 뒤에 있는 필터가 실행된다
                JwtAuthenticationFilter(jwtTokenProvider),
                UsernamePasswordAuthenticationFilter::class.java,
            )

        return http.build()
    }

    @Bean
    fun customAuthenticationEntryPoint(): AuthenticationEntryPoint {
        return CustomAuthenticationEntryPoint()
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