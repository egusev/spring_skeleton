package ru.erfolk.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import javax.persistence.Column;

@Data
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity<Integer> {
    @Column(nullable = false)
    private String code;

    @Setter
    @Column(nullable = false)
    private String name;
}
