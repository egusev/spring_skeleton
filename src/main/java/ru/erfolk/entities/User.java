package ru.erfolk.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "Users", uniqueConstraints = @UniqueConstraint(name = "unique_email", columnNames = {"username", "organization"}))
@Data
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity<Integer> {

    @Column
    private String username;

    @Column
    private String organization;

    @Column(nullable = false, length = 60)
    private String password;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Role role;
}
