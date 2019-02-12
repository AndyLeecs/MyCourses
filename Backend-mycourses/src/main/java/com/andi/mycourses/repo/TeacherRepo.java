package com.andi.mycourses.repo;

import com.andi.mycourses.entity.Course;
import com.andi.mycourses.entity.Student;
import com.andi.mycourses.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author andi
 */
@Repository
public interface TeacherRepo extends JpaRepository<Teacher, String> {
}
