package school.project.soulmate.common.auditing

import org.springframework.data.domain.AuditorAware
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.util.Optional

class SpringSecurityAuditorAware : AuditorAware<String> {
    override fun getCurrentAuditor(): Optional<String> {
        val authentication: Authentication? = SecurityContextHolder.getContext().authentication
        return if (authentication == null || !authentication.isAuthenticated) {
            Optional.empty()
        } else {
            Optional.of(authentication.name)
        }
    }
}
