package com.andi.mycourses.repo;

import com.andi.mycourses.entity.Course;
import com.andi.mycourses.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author andi
 */
public interface CourseRepo extends JpaRepository<Course, Long> {
//    List<Course> findAllByTeacher(Teacher teacher);
}
