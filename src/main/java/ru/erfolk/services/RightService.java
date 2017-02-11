package ru.erfolk.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.erfolk.entities.User;

public interface RightService extends UserDetailsService, AbstractService<User, Integer> {
}
