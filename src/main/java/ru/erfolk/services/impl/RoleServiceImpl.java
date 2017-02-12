package ru.erfolk.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.erfolk.entities.Role;
import ru.erfolk.repositories.RoleRepository;
import ru.erfolk.services.RoleService;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class RoleServiceImpl extends AbstractServiceImpl<Role, Integer>  implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    protected CrudRepository<Role, Integer> getRepository() {
        return roleRepository;
    }

}
