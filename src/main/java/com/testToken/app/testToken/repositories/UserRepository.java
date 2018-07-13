package com.testToken.app.testToken.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.testToken.app.testToken.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
