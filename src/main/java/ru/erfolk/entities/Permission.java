package ru.erfolk.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Entity
@Table(name = "Permissions", uniqueConstraints = @UniqueConstraint(name = "unique_url_method", columnNames = {"path", "method"}))
@Getter
@Setter
@ToString
public class Permission extends BaseEntity<Integer> {
    @NotNull
    @Column
    private String path;

    @NotNull
    @Column
    private String method;
}
