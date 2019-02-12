package com.andi.mycourses.service;

import com.andi.mycourses.entity.Student;
import com.andi.mycourses.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author andi
 */
@Service
public class StudentService {
    @Autowired
    StudentRepo studentRepo;
    public Student getInfo(String email)
    {
       return studentRepo.findById(email).get();
    }
    public boolean register(Student student)
    {
        //todo 学号重复,报新的错
        try {
            Optional<Student> oldStudent = studentRepo.findById(student.getEmail());
            if (oldStudent.isPresent())
            {
                studentRepo.registerAgain(student.getEmail(), student.getSid(),
                        student.getName(),student.getPassword());
            }else
            studentRepo.save(student);
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
