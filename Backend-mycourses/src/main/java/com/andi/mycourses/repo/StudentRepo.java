package com.andi.mycourses.repo;

import com.andi.mycourses.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author andi
 */
@Repository
public interface StudentRepo extends JpaRepository<Student, String> {
    Student findDistinctBySid(long sid);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update Student set sid=:sid where email=:email")
    void setSID(@Param("email") String email, @Param("sid") Long sid);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update Student set isWrittenOff=true where email=:email")
    void logout(@Param("email")String email);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update Student set sid=:sid,name=:name,password=:password,isWrittenOff=false where email=:email")
    void registerAgain(@Param("email") String email,
                       @Param("sid") Long sid,
                       @Param("name")String name,
                       @Param("password")String password
                           );
    @Transactional(rollbackOn = Exception.class)
    @Query(nativeQuery = true,
            value = "select * from base_user natural join student where email in " +
                    "(select distinct student_email from enroll_record where lesson_id = :lesson_id and drop_out = 0) and is_written_off = 0")
    List<Student> getStudentList(@Param("lesson_id") long lesson_id);

    long countByIsWrittenOff(boolean is_written_off);
}
