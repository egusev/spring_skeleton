package ru.erfolk.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.erfolk.actors.Actor;
import ru.erfolk.entities.User;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<User> {

    public User getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Actor)) {
            return null;
        }

        return ((Actor) authentication.getPrincipal()).getUser();
    }
}