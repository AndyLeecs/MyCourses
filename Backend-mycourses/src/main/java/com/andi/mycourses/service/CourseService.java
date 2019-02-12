package com.andi.mycourses.service;

import com.andi.mycourses.entity.Course;
import com.andi.mycourses.entity.Courseware;
import com.andi.mycourses.entity.DBFile;
import com.andi.mycourses.entity.Teacher;
import com.andi.mycourses.repo.CourseRepo;
import com.andi.mycourses.repo.CoursewareRepo;
import com.andi.mycourses.repo.TeacherRepo;
import com.andi.mycourses.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author andi
 */
@Service
public class CourseService {
    @Autowired
    CourseRepo courseRepo;
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    CoursewareRepo coursewareRepo;

    public Courseware getCourseware(String id)
    {
        return coursewareRepo.findById(id).get();
    }
    public List<Courseware> getAllCoursewares(long course_id)
    {
        Course course = courseRepo.findById(course_id).get();
        return course.getCoursewares();
    }

    public boolean uploadCourseware(MultipartFile file, long course_id)
    {
        DBFile dbFile = FileUtil.getDBFile(file);
        if (dbFile==null)return false;
        try {
            Course course = courseRepo.findById(course_id).get();
            Courseware courseware = new Courseware(dbFile,course );
            coursewareRepo.save(courseware);
//            coursewareRepo.upload(dbFile.getFileName(), dbFile.getFileType(), dbFile.getData(), course_id);
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean apply(String teacher_email, String name)
    {
        try {
            //todo 写在dao层
            Course course = new Course();
            course.setName(name);
            Teacher teacher = teacherRepo.findById(teacher_email).get();
            System.out.println(teacher);
            course.setTeacher(teacher);
            courseRepo.save(course);
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
