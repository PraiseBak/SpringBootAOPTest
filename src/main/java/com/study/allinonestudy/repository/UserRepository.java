package com.study.allinonestudy.repository;

import com.study.allinonestudy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.config.core.userdetails.ReactiveUserDetailsServiceResourceFactoryBean;

import org.springframework.stereotype.Repository;

import java.util.Optional;

//import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
}
