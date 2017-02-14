package ru.erfolk.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.erfolk.entities.Permission;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Repository
public interface PermissionRepository extends CrudRepository<Permission, Integer> {
}
