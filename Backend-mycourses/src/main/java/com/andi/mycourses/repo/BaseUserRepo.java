package com.andi.mycourses.repo;

import com.andi.mycourses.entity.BaseUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * @author andi
 */
public interface BaseUserRepo extends JpaRepository<BaseUser, String> {
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query("update BaseUser set name=:name where email=:email")
    void setName(@Param("email") String email, @Param("name") String name);
}
