package school.project.soulmate

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import school.project.soulmate.common.auditing.SpringSecurityAuditorAware

@SpringBootApplication
@EnableJpaAuditing
class SoulmateApplication {
    @Bean
    fun auditorProvider(): AuditorAware<String> {
        return SpringSecurityAuditorAware()
    }
}

fun main(args: Array<String>) {
    runApplication<SoulmateApplication>(*args)
}