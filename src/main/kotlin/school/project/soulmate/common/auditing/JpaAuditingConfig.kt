//package school.project.soulmate.common.auditing
//
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.data.domain.AuditorAware
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing
//
//@Configuration
//@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
//class JpaAuditingConfig {
//    @Bean
//    fun auditorProvider(): AuditorAware<String> {
//        return SpringSecurityAuditorAware()
//    }
//}
