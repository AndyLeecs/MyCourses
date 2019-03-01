package com.andi.mycourses.repo;

import com.andi.mycourses.entity.Homework;
import com.andi.mycourses.entity.StuHomework;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author andi
 */
public interface StuHomeworkRepo extends JpaRepository<StuHomework, String> {
    @Transactional(rollbackOn = Exception.class)
    @Query(nativeQuery = true,
            value = "select * from stu_homework natural join files where student_email = :email " +
                    "and homework_id = :homework_id")
    List<StuHomework> hasSubmitted(@Param("email")String email, @Param("homework_id")long homework_id);

    @Transactional(rollbackOn = Exception.class)
    @Query(nativeQuery = true,
    value = "select * from stu_homework natural join files where student_email = :email " +
            "and homework_id = :homework_id")
    StuHomework findSelf(@Param("email")String email, @Param("homework_id")long homework_id);

    @Transactional(rollbackOn = Exception.class)
    @Query(nativeQuery = true,
            value = "select * from stu_homework natural join files where homework_id in " +
                    "(select id from homework where lesson_id in " +
                    "(select id from lesson_whole where course_id in " +
                    "(select id from course where teacher_email = :email))) ")
    List<StuHomework> findByTeacher(@Param("email") String email);

//    @Transactional(rollbackOn = Exception.class)
//    @Query(nativeQuery = true,
//            value = "select title title, co.name course, l.semester semester, s.name student" +
//                    "from homework natural join lesson_whole l natural join student s natural join course co")
//    List<StuHomeworkDto> findUnhandled(@Param("email") String email);
//    @Transactional
//    StuHomework findDistinctFirstByStudentAndAndHomework(Student student, Homework homework);

    List<StuHomework> findByHomework(Homework homework);
}
