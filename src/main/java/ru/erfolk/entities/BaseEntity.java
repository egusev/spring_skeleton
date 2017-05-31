package ru.erfolk.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity<K extends Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected K id;

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
