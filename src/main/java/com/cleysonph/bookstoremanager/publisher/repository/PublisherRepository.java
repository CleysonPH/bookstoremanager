package com.cleysonph.bookstoremanager.publisher.repository;

import com.cleysonph.bookstoremanager.publisher.entity.Publisher;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

}
