package ru.erfolk.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.erfolk.entities.Role;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
    Role findByCode(String code);
}
