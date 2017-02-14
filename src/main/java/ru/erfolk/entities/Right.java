package ru.erfolk.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Entity
@Table(name = "Permissions", uniqueConstraints = @UniqueConstraint(name = "unique_url_method", columnNames = {"path", "method"}))
@Getter
@Setter
@ToString
public class Right extends BaseEntity<Integer> {
    @Column
    private String path;

    @Column
    private String method;

    @Column
    private int permission;
}
