package com.andi.mycourses.repo;

import com.andi.mycourses.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author andi
 */
@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByCode(String code);
    User findByEmail(String email);
}
