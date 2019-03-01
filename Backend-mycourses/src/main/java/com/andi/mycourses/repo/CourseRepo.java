package com.andi.mycourses.repo;

import com.andi.mycourses.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author andi
 */
public interface CourseRepo extends JpaRepository<Course, Long> {
//    List<Course> findAllByTeacher(Teacher teacher);
    @Modifying
    @Transactional
    @Query("update Course set approved = :approved where id = :id")
    void approve(@Param("id") long id, @Param("approved") int approved);

    List<Course> findByApproved(int approved);

    long countByApproved(int approved);
}
