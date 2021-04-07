package com.cleysonph.bookstoremanager.publisher.service;

import com.cleysonph.bookstoremanager.publisher.builder.PublisherDTOBuilder;
import com.cleysonph.bookstoremanager.publisher.mapper.PublisherMapper;
import com.cleysonph.bookstoremanager.publisher.repository.PublisherRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PublisherServiceTest {

    private static final PublisherMapper PUBLISHER_MAPPER = PublisherMapper.INSTANCE;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private PublisherService publisherService;

    private PublisherDTOBuilder publisherDTOBuilder;

    @BeforeEach
    void setUp() {
        publisherDTOBuilder = PublisherDTOBuilder.builder().build();
    }

}
