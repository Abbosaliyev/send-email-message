package uz.pdp.appsendemailmessage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by
 * Sahobiddin Abbosaliyev
 * 7/11/2021
 */
@Component
@EnableJpaAuditing
public class SecurityAuditAwareConfig {

    @Bean
    AuditorAware<UUID> auditorAware() {
        return new SecurityAuditAware();
    }
}
