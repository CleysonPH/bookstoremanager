package com.cleysonph.bookstoremanager.user.repository;

import com.cleysonph.bookstoremanager.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
