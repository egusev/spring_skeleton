package ru.erfolk.audit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {
    Audit findByClazzAndEntityIdAndVersion(String clazz, Serializable id, long version);
}
