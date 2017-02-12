package ru.erfolk.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(name = "unique_user", columnNames = {"username", "org"}))
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity<Integer> {

    @Column
    private String username;

    @Column
    private String org;

    @Column(nullable = false, length = 60)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role", nullable = false)
    private Role role;
}
