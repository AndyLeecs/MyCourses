package com.andi.mycourses.repo;

import com.andi.mycourses.entity.LessonWhole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author andi
 */
public interface LessonRepo extends JpaRepository<LessonWhole, Long> {
    @Transactional(rollbackOn = Exception.class)
    @Query(nativeQuery = true,
            value = "select * from lesson_whole where course_id in " +
                    "(select id from course where teacher_email=:email) " +
                    "and now() >= start_time " +
                    "and now() <= end_time " +
                    "and approved=1")
    List<LessonWhole> getCurrentlessons(@Param("email") String email);
}
