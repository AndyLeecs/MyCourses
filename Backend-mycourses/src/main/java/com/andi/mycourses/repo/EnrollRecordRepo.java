package com.andi.mycourses.repo;

import com.andi.mycourses.entity.EnrollRecord;
import com.andi.mycourses.entity.LessonWhole;
import com.andi.mycourses.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author andi
 */
public interface EnrollRecordRepo extends JpaRepository<EnrollRecord, Long> {
    @Transactional
    @Modifying
    @Query("update EnrollRecord set dropOut = true where student = :student and lesson = :lesson")
    void deleteDistinctByStudentAndLesson(@Param("student") Student student, @Param("lesson") LessonWhole lesson);
    @Transactional
    EnrollRecord findDistinctFirstByStudentAndLesson(Student student, LessonWhole lesson);
    @Transactional
    int countByLessonAndWhichClass(LessonWhole lesson, int whichClass);
    @Transactional
    @Query(nativeQuery = true,
            value = "select * from enroll_record natural join student" +
                    " where lesson_id in (" +
                    "select id from lesson_whole where course_id in " +
                    "(select id from course where teacher_email = :email)" +
                    ");")
    List<EnrollRecord> getEnrollment(String email);
}
