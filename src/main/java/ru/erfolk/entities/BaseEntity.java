package ru.erfolk.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.erfolk.audit.CustomAuditListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

import static javax.persistence.FetchType.LAZY;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@MappedSuperclass
@Getter
@Setter
@ToString(exclude = {"createdBy", "lastModifiedBy"})
@EntityListeners({CustomAuditListener.class})
public abstract class BaseEntity<K extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected K id;

    @Version
    private long version;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "created_by")
    @JsonIgnore
    private User createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "creation_time", nullable = false)
    private Date creationTime;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "last_modified_by")
    @JsonIgnore
    private User lastModifiedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_modification_time", nullable = false)
    private Date lastModificationTime;

    public K getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseEntity<?> that = (BaseEntity<?>) o;
        return !(id == null || that.id == null) && id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
