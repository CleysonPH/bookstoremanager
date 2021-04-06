package com.cleysonph.bookstoremanager.book.repository;

import com.cleysonph.bookstoremanager.book.entity.Book;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
