package ru.erfolk.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.erfolk.entities.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Entity
@Table(name = "Audits", uniqueConstraints = @UniqueConstraint(name = "unique_entry", columnNames = {"clazz", "entityId", "version"}))
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Audit {

    public static final int MAX_CONTENT_LENGTH = 4000;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String clazz;

    @Column(nullable = false)
    private Serializable entityId;

    @Column(nullable = false)
    private long version;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column
    private Integer user;

    @Column(length = MAX_CONTENT_LENGTH)
    private String oldValue;

    @Column(length = MAX_CONTENT_LENGTH)
    private String newValue;

    public Audit(BaseEntity entity, boolean deleted) throws JsonProcessingException {
        setClazz(entity.getClass().getSimpleName());
        setEntityId(entity.getId());
        setVersion(entity.getVersion());
        setUser(entity.getLastModifiedBy() == null ? null : entity.getLastModifiedBy().getId());
        setDate(entity.getLastModificationTime());

        if (!deleted) {
            String str = new ObjectMapper().writeValueAsString(entity);
            if (str.length() > MAX_CONTENT_LENGTH) {
                str = str.substring(0, MAX_CONTENT_LENGTH);
            }
            setNewValue(str);
        }
    }
}
