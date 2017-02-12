package ru.erfolk.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Entity
@Table(name = "Rights", uniqueConstraints = {
        @UniqueConstraint(name = "unique_url_method", columnNames = {"url", "method"})
})
@Data
@EqualsAndHashCode(callSuper = true)
public class Right extends BaseEntity<Integer> {
    @Column
    private String url;

    @Column
    private String method;

    @Column
    private int right;
}
