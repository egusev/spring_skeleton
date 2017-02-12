package ru.erfolk.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Entity
@Table(name = "Roles", uniqueConstraints = {
        @UniqueConstraint(name = "unique_code", columnNames = {"code"}),
        @UniqueConstraint(name = "unique_name", columnNames = {"name"})
})
@Getter
@Setter
@ToString(exclude = "rights")
public class Role extends BaseEntity<Integer> {
    @Column(nullable = false)
    private String code;

    @Setter
    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(name = "role_rights",
            joinColumns = @JoinColumn(name = "role", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "permission", nullable = false, updatable = false)
    )
    private List<Right> rights;
}
