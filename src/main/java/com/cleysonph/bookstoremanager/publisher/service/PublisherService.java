package com.cleysonph.bookstoremanager.publisher.service;

import com.cleysonph.bookstoremanager.publisher.mapper.PublisherMapper;
import com.cleysonph.bookstoremanager.publisher.repository.PublisherRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PublisherService {

    private final static PublisherMapper PUBLISHER_MAPPER = PublisherMapper.INSTANCE;

    private PublisherRepository publisherRepository;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository) {
        this.publisherRepository = publisherRepository;
    }
}
