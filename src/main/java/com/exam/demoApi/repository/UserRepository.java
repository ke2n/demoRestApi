package com.exam.demoApi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.demoApi.domain.User;

/**
 * @author yunsung Kim
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
