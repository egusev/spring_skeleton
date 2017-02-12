package ru.erfolk.audit;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.domain.AuditorAware;
import ru.erfolk.config.AutowireHelper;
import ru.erfolk.entities.BaseEntity;
import ru.erfolk.entities.User;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
public class CustomAuditListener implements ApplicationContextAware {

    ApplicationContext context;

    @Autowired
    private AuditorAware<User> auditorAware;

    @Autowired
    private AuditService auditService;

    @PrePersist
    public void touchPreCreate(BaseEntity target) {
        update(target, true);
    }

    @PostPersist
    public void touchPostCreate(BaseEntity target) {
        audit(target, true, false);
    }

    @PreUpdate
    public void touchPreUpdate(BaseEntity target) {
        update(target, false);
    }

    @PostUpdate
    public void touchPostUpdate(BaseEntity target) {
        audit(target, false, false);
    }

    @PreRemove
    public void touchPreRemove(BaseEntity target) {
        update(target, false);
    }

    @PostRemove
    public void touchPostRemove(BaseEntity target) {
        target.setVersion(target.getVersion() + 1);
        audit(target, false, true);
    }

    private void update(BaseEntity entity, boolean create) {
        AutowireHelper.autowire(this, this.auditorAware, this.auditService);
        User user = auditorAware.getCurrentAuditor();
        Date now = new Date();

        entity.setLastModificationTime(now);
        entity.setLastModifiedBy(user);
        if (create) {
            entity.setCreatedBy(user);
            entity.setCreationTime(now);
        }
    }

    private void audit(BaseEntity entity, boolean create, boolean delete) {
        AutowireHelper.autowire(this, this.auditorAware, this.auditService);
        auditService.audit(entity, create, delete);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
