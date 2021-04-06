package com.cleysonph.bookstoremanager.author.repository;

import com.cleysonph.bookstoremanager.author.entity.Author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

}
