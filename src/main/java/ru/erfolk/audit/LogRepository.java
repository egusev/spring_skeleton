package ru.erfolk.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
}
