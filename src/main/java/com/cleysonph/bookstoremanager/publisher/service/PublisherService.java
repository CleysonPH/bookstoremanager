package com.cleysonph.bookstoremanager.publisher.service;

import java.util.Optional;

import com.cleysonph.bookstoremanager.publisher.dto.PublisherDTO;
import com.cleysonph.bookstoremanager.publisher.entity.Publisher;
import com.cleysonph.bookstoremanager.publisher.exception.PublisherAlreadyExistsException;
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

    public PublisherDTO create(PublisherDTO publisherDTO) {
        verifyIfExists(publisherDTO.getName(), publisherDTO.getCode());

        Publisher publisherToCreate = PUBLISHER_MAPPER.toModel(publisherDTO);

        Publisher createdPublisher = publisherRepository.save(publisherToCreate);

        return PUBLISHER_MAPPER.toDTO(createdPublisher);
    }

    private void verifyIfExists(String name, String code) {
        Optional<Publisher> duplicatedPublisher = publisherRepository.findByNameOrCode(name, code);

        if (duplicatedPublisher.isPresent()) {
            throw new PublisherAlreadyExistsException(name, code);
        }
    }
}
