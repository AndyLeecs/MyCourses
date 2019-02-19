package com.andi.mycourses.repo;

import com.andi.mycourses.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author andi
 */
public interface TopicRepo extends JpaRepository<Topic, Long> {
}
