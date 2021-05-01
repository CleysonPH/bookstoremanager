package com.cleysonph.bookstoremanager.publisher.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.cleysonph.bookstoremanager.publisher.builder.PublisherDTOBuilder;
import com.cleysonph.bookstoremanager.publisher.dto.PublisherDTO;
import com.cleysonph.bookstoremanager.publisher.entity.Publisher;
import com.cleysonph.bookstoremanager.publisher.exception.PublisherAlreadyExistsException;
import com.cleysonph.bookstoremanager.publisher.exception.PublisherNotFoundExcepton;
import com.cleysonph.bookstoremanager.publisher.mapper.PublisherMapper;
import com.cleysonph.bookstoremanager.publisher.repository.PublisherRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @Test
    void whenNewPublisherIsInformedTheItShouldBeCreated() {
        PublisherDTO expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherCreated = PUBLISHER_MAPPER.toModel(expectedPublisherToCreateDTO);

        when(publisherRepository.findByNameOrCode(expectedPublisherToCreateDTO.getName(), expectedPublisherToCreateDTO.getCode()))
            .thenReturn(Optional.empty());
        when(publisherRepository.save(expectedPublisherCreated)).thenReturn(expectedPublisherCreated);

        PublisherDTO createdPublisherDTO = publisherService.create(expectedPublisherToCreateDTO);

        assertThat(createdPublisherDTO, is(equalTo(expectedPublisherToCreateDTO)));
    }

    @Test
    void whenExistingPublisherIsInformedThenAnExceptonShouldBeThrown() {
        PublisherDTO expectedPublisherToCreateDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherDuplicated = PUBLISHER_MAPPER.toModel(expectedPublisherToCreateDTO);

        when(publisherRepository.findByNameOrCode(expectedPublisherToCreateDTO.getName(), expectedPublisherToCreateDTO.getCode()))
            .thenReturn(Optional.of(expectedPublisherDuplicated));

        try {
            publisherService.create(expectedPublisherToCreateDTO);
            fail("Expected an PublisherAlreadyExistsException to be thrown");
        } catch (PublisherAlreadyExistsException e) {
            String name = expectedPublisherToCreateDTO.getName();
            String code = expectedPublisherToCreateDTO.getCode();
            assertThat(e.getMessage(), is(String.format("Publisher with name %s or code %s already exists!", name, code)));
        }
    }

    @Test
    void whenValidIdIsGivenThenAPublisherShoudBeReturned() {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherFound = PUBLISHER_MAPPER.toModel(expectedPublisherFoundDTO);
        Long expectedPublisherFoundId = expectedPublisherFound.getId();

        when(publisherRepository.findById(expectedPublisherFoundId)).thenReturn(Optional.of(expectedPublisherFound));

        PublisherDTO foundPublisherDTO = publisherService.findById(expectedPublisherFoundId);

        assertThat(foundPublisherDTO, is(equalTo(expectedPublisherFoundDTO)));
    }

    @Test
    void whenInvalidIdIsGivenThenAnExceptionShouldBeThrown() {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
        Long expectedPublisherFoundId = expectedPublisherFoundDTO.getId();

        when(publisherRepository.findById(expectedPublisherFoundId)).thenReturn(Optional.empty());

        try {
            publisherService.findById(expectedPublisherFoundId);
            fail("Expected an PublisherNotFoundException to be thrown");
        } catch (PublisherNotFoundExcepton e) {
            assertThat(e.getMessage(), is(String.format("Publisher with id %s not exists!", expectedPublisherFoundId)));
        }
    }

}
