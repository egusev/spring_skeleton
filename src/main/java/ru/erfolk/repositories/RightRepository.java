package ru.erfolk.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.erfolk.entities.Right;

@Repository
public interface RightRepository extends CrudRepository<Right, Integer> {
}
