package com.study.allinonestudy.repository;

import com.study.allinonestudy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.config.core.userdetails.ReactiveUserDetailsServiceResourceFactoryBean;

import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
