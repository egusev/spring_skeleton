package ru.erfolk.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.erfolk.actors.Actor;
import ru.erfolk.entities.Right;
import ru.erfolk.entities.Role;
import ru.erfolk.services.RoleService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Component
@Slf4j
public class AuthorizationService {

    @Autowired
    private RoleService roleService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean check(Authentication authentication, HttpServletRequest request) {
        log.debug("authorize {} for {}", authentication.getName(), request.getServletPath());
        if (!(authentication.getPrincipal() instanceof Actor)) {
            return false;
        }

        Actor actor = (Actor) authentication.getPrincipal();
        if (actor.getUser() == null) {
            return false;
        }

        Role role = roleService.findById(actor.getUser().getId());

        for (Right right : role.getRights()) {
            if (request.getMethod().equalsIgnoreCase(right.getMethod())
                    && request.getServletPath().matches(right.getPath())) {
                if (right.getPermission() > 0) {
                    return true;
                } else if (right.getPermission() < 0) {
                    return false;
                }
            }
        }

        return false;
    }
}
