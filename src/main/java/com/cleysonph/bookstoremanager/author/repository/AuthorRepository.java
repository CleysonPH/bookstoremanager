package com.cleysonph.bookstoremanager.author.repository;

import java.util.Optional;

import com.cleysonph.bookstoremanager.author.entity.Author;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {

  Optional<Author> findByName(String name);

}
