package ru.erfolk.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.erfolk.actors.Actor;
import ru.erfolk.entities.User;
import ru.erfolk.repositories.UserRepository;
import ru.erfolk.services.UserService;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class UserServiceImpl extends AbstractServiceImpl<User, Integer>  implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected CrudRepository<User, Integer> getRepository() {
        return userRepository;
    }

    @Override
    public Actor loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("AUTH {}", username);
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Password is wrong or user doesn't exist");
        }

        return new Actor(user);
    }
}
