package com.andi.mycourses.repo;

import com.andi.mycourses.entity.Homework;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author andi
 */
public interface HomeworkRepo extends JpaRepository<Homework, Long> {
}
