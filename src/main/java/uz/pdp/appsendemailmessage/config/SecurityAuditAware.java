package uz.pdp.appsendemailmessage.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.pdp.appsendemailmessage.entity.UserEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by
 * Sahobiddin Abbosaliyev
 * 7/11/2021
 */
public class SecurityAuditAware implements AuditorAware<UUID> {
    //kim yozyotganini bilib turuvchi ya'ni kuzatuvchi method

    @Override
    public Optional<UUID> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
            UserEntity userEntity = (UserEntity) authentication.getPrincipal();
            return Optional.of(userEntity.getId());
        }
        return Optional.empty();
    }
}
