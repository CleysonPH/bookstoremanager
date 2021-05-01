package com.cleysonph.bookstoremanager.publisher.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.cleysonph.bookstoremanager.publisher.dto.PublisherDTO;
import com.cleysonph.bookstoremanager.publisher.entity.Publisher;
import com.cleysonph.bookstoremanager.publisher.exception.PublisherAlreadyExistsException;
import com.cleysonph.bookstoremanager.publisher.exception.PublisherNotFoundExcepton;
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

    public PublisherDTO findById(Long id) {
        return publisherRepository.findById(id)
            .map(PUBLISHER_MAPPER::toDTO)
            .orElseThrow(() -> new PublisherNotFoundExcepton(id));
    }

    public List<PublisherDTO> findAll() {
        return publisherRepository.findAll()
            .stream()
            .map(PUBLISHER_MAPPER::toDTO)
            .collect(Collectors.toList());
    }

    private void verifyIfExists(String name, String code) {
        Optional<Publisher> duplicatedPublisher = publisherRepository.findByNameOrCode(name, code);

        if (duplicatedPublisher.isPresent()) {
            throw new PublisherAlreadyExistsException(name, code);
        }
    }
}
