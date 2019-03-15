package com.andi.mycourses.entity;

import com.andi.mycourses.vo.StuRegisterInfoVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

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
    @Column(name = "isWrittenOff")
    private boolean isWrittenOff = false;

    @OneToMany(mappedBy = "student", targetEntity = EnrollRecord.class, cascade = CascadeType.ALL)
    private List<EnrollRecord> records;

    public Student(StuRegisterInfoVo stuRegisterInfoVo, String email)
    {
        this.email = email;
        this.sid = stuRegisterInfoVo.getSid();
        this.name = stuRegisterInfoVo.getName();
        this.password = stuRegisterInfoVo.getPassword();
    }

    @Override
    public boolean equals(Object obj) {
        Student s = (Student)obj;
        return sid.equals(s.getSid());
    }
}
