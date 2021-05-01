package com.cleysonph.bookstoremanager.publisher.repository;

import java.util.Optional;

import com.cleysonph.bookstoremanager.publisher.entity.Publisher;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByNameOrCode(String nome, String code);

}
