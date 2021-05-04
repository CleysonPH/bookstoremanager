package com.cleysonph.bookstoremanager.user.repository;

import java.util.Optional;

import com.cleysonph.bookstoremanager.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailOrUsername(String email, String username);

    Optional<User> findByUsername(String username);

}
