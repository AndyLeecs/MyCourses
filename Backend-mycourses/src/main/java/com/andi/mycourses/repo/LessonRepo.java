package com.andi.mycourses.repo;

import com.andi.mycourses.entity.LessonWhole;
import com.sun.tracing.dtrace.ProviderAttributes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author andi
 */
public interface LessonRepo extends JpaRepository<LessonWhole, Long> {

    List<LessonWhole> findByApproved(int approved);

    @Modifying
    @Transactional
    @Query("update LessonWhole set approved = :approved where id = :id")
    void approve(@Param("id") long id, @Param("approved") int approved);

    @Transactional(rollbackOn = Exception.class)
    @Query(nativeQuery = true,
            value = "select * from lesson_whole where course_id in " +
                    "(select id from course where teacher_email=:email) " +
//                    "and now() >= start_time " +
                    "and now() <= end_time " +
                    "and approved=1")
    List<LessonWhole> getCurrentlessons(@Param("email") String email);

    @Transactional(rollbackOn = Exception.class)
    @Query(nativeQuery = true,
            value = "select * from lesson_whole where course_id in " +
                    "(select id from course where teacher_email=:email) " +
                    "and approved=1")
    List<LessonWhole> getAlllessons(@Param("email") String email);

    @Transactional(rollbackOn = Exception.class)
    @Query(nativeQuery = true,
            value = "select * from lesson_whole where id not in " +
                    "(select lesson_id from enroll_record " +
                    "where student_email = :email) " +
                    "and now() <= end_time " +
                    "and approved=1")
    List<LessonWhole> getLessonsToEnroll(@Param("email")String email);


    @Transactional(rollbackOn = Exception.class)
    @Query(nativeQuery = true,
            value = "select * from lesson_whole where id in " +
                    "(select lesson_id from enroll_record " +
                    "where student_email = :email and drop_out = 0) ")
    List<LessonWhole> getAttended(@Param("email")String email);

    @Transactional(rollbackOn = Exception.class)
    @Query(nativeQuery = true,
            value = "select * from lesson_whole where id in " +
                    "(select lesson_id from enroll_record " +
                    "where student_email = :email and drop_out = 1) ")
    List<LessonWhole> getDropout(@Param("email")String email);
}
