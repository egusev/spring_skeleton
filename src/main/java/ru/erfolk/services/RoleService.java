package ru.erfolk.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.erfolk.entities.User;

public interface RoleService extends UserDetailsService, AbstractService<User, Integer> {
}
