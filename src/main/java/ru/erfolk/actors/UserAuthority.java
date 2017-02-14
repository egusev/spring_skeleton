package ru.erfolk.actors;

import org.springframework.security.core.GrantedAuthority;
import ru.erfolk.entities.User;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
public class UserAuthority implements GrantedAuthority {
    private User user;

    public UserAuthority(User user) {
        this.user = user;
    }

    @Override
    public String getAuthority() {
        return RoleAuthority.getAuthority(user.getRole());
    }
}
