package com.andi.mycourses.repo;

import com.andi.mycourses.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author andi
 */
@Repository
public interface StudentRepo extends JpaRepository<Student, String> {
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update Student set sid=:sid where email=:email")
    void setSID(@Param("email") String email, @Param("sid") Long sid);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update Student set is_written_off=true where email=:email")
    void logout(@Param("email")String email);

    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update Student set sid=:sid,name=:name,password=:password,is_written_off=false where email=:email")
    void registerAgain(@Param("email") String email,
                       @Param("sid") Long sid,
                       @Param("name")String name,
                       @Param("password")String password
                           );
}
