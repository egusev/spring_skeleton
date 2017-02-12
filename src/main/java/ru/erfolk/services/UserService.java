package ru.erfolk.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.erfolk.entities.User;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
public interface UserService extends UserDetailsService, AbstractService<User, Integer> {
}
