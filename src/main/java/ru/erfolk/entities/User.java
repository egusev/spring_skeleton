package ru.erfolk.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * @author Eugene Gusev (egusev@gmail.com)
 */
@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(name = "unique_user", columnNames = {"username", "org"}))
@Getter
@Setter
@ToString(callSuper = true)
public class User extends BaseEntity<Integer> {

    @NotNull
    @Column
    private String username;

    @NotNull
    @Column
    private String org;

    @NotNull
    @Column(nullable = false, length = 60)
    private String password;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "role", nullable = true)
    private Role role;
}
