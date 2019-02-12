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
@ToString
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "sid"))
public class Student extends BaseUser{
    private Long sid;
    private boolean is_written_off = false;
    public Student(StuRegisterInfoVo stuRegisterInfoVo, String email)
    {
        this.email = email;
        this.sid = stuRegisterInfoVo.getSid();
        this.name = stuRegisterInfoVo.getName();
        this.password = stuRegisterInfoVo.getPassword();
    }
}
