package com.andi.mycourses.service;

import com.andi.mycourses.entity.Course;
import com.andi.mycourses.entity.LessonWhole;
import com.andi.mycourses.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author andi
 */
@Service
public class AdminService {
    @Autowired
    CourseRepo courseRepo;
    @Autowired
    LessonRepo lessonRepo;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    HomeworkRepo homeworkRepo;

    public boolean login(String password){
        return password.equals("6677");
    }

    public List<Course> getNewCourses(){
        return courseRepo.findByApproved(0);
    }

    public List<LessonWhole> getNewLessons(){
        return lessonRepo.findByApproved(0);
    }

    public boolean voteCourse(long course_id, int approved){
       try {
           courseRepo.approve(course_id, approved);
       }catch (Exception e)
       {
           e.printStackTrace();
           return false;
       }
       return true;
    }
    public boolean voteLesson(long lesson_id, int approved){
        try {
            lessonRepo.approve(lesson_id, approved);
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public long countStudent(){
        return studentRepo.count();
    }

    public long countTeacher(){
        return teacherRepo.count();
    }

    public long countCourse(){
        return courseRepo.count();
    }
}
