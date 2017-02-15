package ru.erfolk.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.erfolk.entities.BaseEntity;

import java.util.List;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Component
public class AuditService {

    @Autowired
    private AuditRepository auditRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public void audit(BaseEntity entity, boolean created, boolean deleted) {
        try {
            Audit audit = new Audit(entity, deleted);

            if (!created) {
                Audit prev = auditRepository.findFirstByClazzAndEntityIdAndVersion(entity.getClass().getSimpleName(), entity.getId(), entity.getVersion() - 1);
                if (prev != null) {
                    audit.setOldValue(prev.getNewValue());
                }
            }
            auditRepository.save(audit);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public List<Audit> findAll() {
        return auditRepository.findAll();
    }
}
