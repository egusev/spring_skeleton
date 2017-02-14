package ru.erfolk.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.erfolk.entities.User;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Override
    User findOne(Integer integer);

    User findByUsernameAndOrg(String username, String org);

    @Override
    <S extends User> S save(S entity);

    @Override
    void delete(Integer integer);

    @Override
    void delete(User entity);

    @Override
    void deleteAll();
}
