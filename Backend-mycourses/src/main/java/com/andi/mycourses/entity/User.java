package com.andi.mycourses.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author andi
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@ToString
//@Inheritance(strategy = InheritanceType.JOINED)
//@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.INTEGER)
//未激活的用户
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String code;
}
