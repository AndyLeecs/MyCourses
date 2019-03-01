package com.andi.mycourses.service;

import com.andi.mycourses.entity.Course;
import com.andi.mycourses.entity.Teacher;
import com.andi.mycourses.repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author andi
 */
@Service
public class TeacherService {
    @Autowired
    TeacherRepo teacherRepo;
    public List<Course> getCoursesForPub(String email)
    {
        List<Course> courses = new ArrayList<>();
        try {
            Teacher teacher = teacherRepo.findById(email).get();
            courses = teacher.getCourses().stream()
                    .filter((Course c)->c.getApproved() == 1)
                    .collect(Collectors.toList());
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return courses;
    }


    public Teacher getInfo(String email)
    {
        return teacherRepo.findById(email).get();
    }
    public boolean register(Teacher teacher)
    {
        try {
            teacherRepo.save(teacher);
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
