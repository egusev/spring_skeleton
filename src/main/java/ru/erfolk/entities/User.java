package ru.erfolk.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(name = "unique_user", columnNames = {"username", "org"}))
@Getter
@Setter
@ToString(callSuper = true)
public class User extends BaseEntity<Integer> {

    @Column
    private String username;

    @Column
    private String org;

    @Column(nullable = false, length = 60)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role", nullable = true)
    private Role role;
}
