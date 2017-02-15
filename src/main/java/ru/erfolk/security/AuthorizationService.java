package ru.erfolk.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.erfolk.actors.Actor;
import ru.erfolk.entities.Permission;
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

        Role role = roleService.findById(actor.getUser().getRole().getId());

        for (Permission permission : role.getRights()) {
            if (request.getMethod().matches(permission.getMethod())
                    && request.getServletPath().matches(permission.getPath())) {
                if (permission.getPermission() > 0) {
                    return true;
                } else if (permission.getPermission() < 0) {
                    return false;
                }
            }
        }

        return false;
    }
}
