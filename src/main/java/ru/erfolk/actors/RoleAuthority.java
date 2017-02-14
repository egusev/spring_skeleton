package ru.erfolk.actors;

import org.springframework.security.core.GrantedAuthority;
import ru.erfolk.entities.Role;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
public class RoleAuthority implements GrantedAuthority {
    private String authority;

    public RoleAuthority(Role role) {
        authority = getAuthority(role);
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public static String getAuthority(Role role) {
        return ("role_" + role.getCode()).toUpperCase();
    }
}
