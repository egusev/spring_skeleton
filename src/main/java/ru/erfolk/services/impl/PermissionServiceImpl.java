package ru.erfolk.services.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.erfolk.entities.Permission;
import ru.erfolk.repositories.PermissionRepository;
import ru.erfolk.services.PermissionService;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Service
@Transactional(readOnly = true)
@Slf4j
public class PermissionServiceImpl extends AbstractServiceImpl<Permission, Integer>  implements PermissionService {

    @Autowired
    private PermissionRepository rightRepository;

    @Override
    protected CrudRepository<Permission, Integer> getRepository() {
        return rightRepository;
    }
}
