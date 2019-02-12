package com.andi.mycourses.service;

import com.andi.mycourses.entity.Course;
import com.andi.mycourses.entity.LessonWhole;
import com.andi.mycourses.entity.LessonPubVo;
import com.andi.mycourses.entity.Teacher;
import com.andi.mycourses.repo.CourseRepo;
import com.andi.mycourses.repo.LessonRepo;
import com.andi.mycourses.repo.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author andi
 */
@Service
public class LessonService {
    @Autowired
    LessonRepo lessonRepo;
    @Autowired
    CourseRepo courseRepo;

    public List<LessonWhole> cur(String email)
    {
        List<LessonWhole> lessonWholes = new ArrayList<>();
        try {
            lessonWholes = lessonRepo.getCurrentlessons(email);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return lessonWholes;
    }

    public boolean pub(LessonPubVo vo) {
        try {
            long course_id = vo.getCourse_id();
            Course course = courseRepo.findById(course_id).get();
            LessonWhole lesson = new LessonWhole(course,vo);
            lessonRepo.save(lesson);
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }return true;
    }
}
