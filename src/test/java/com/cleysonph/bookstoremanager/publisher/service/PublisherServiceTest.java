package com.cleysonph.bookstoremanager.publisher.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
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

    @Test
    void whenListPublisherIsCalledThenItShouldBeReturned() {
        PublisherDTO expectedPublisherFoundDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherFound = PUBLISHER_MAPPER.toModel(expectedPublisherFoundDTO);

        when(publisherRepository.findAll()).thenReturn(Collections.singletonList(expectedPublisherFound));

        List<PublisherDTO> foundPublishersDTO = publisherService.findAll();

        assertThat(foundPublishersDTO.size(), is(1));
        assertThat(foundPublishersDTO.get(0), is(equalTo(expectedPublisherFoundDTO)));
    }

    @Test
    void whenListPublisherIsCalledThenAnEmptyListShouldBeReturned() {
        when(publisherRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        List<PublisherDTO> foundPublishersDTO = publisherService.findAll();

        assertThat(foundPublishersDTO.size(), is(0));
    }

    @Test
    void whenValidPublisherIdIsGivenThenItShouldBeDeleted() {
        PublisherDTO expectedPublisherDeletedDTO = publisherDTOBuilder.buildPublisherDTO();
        Publisher expectedPublisherDeleted = PUBLISHER_MAPPER.toModel(expectedPublisherDeletedDTO);

        Long expectedDeletedPublisherId = expectedPublisherDeletedDTO.getId();
        doNothing().when(publisherRepository).deleteById(expectedDeletedPublisherId);
        when(publisherRepository.findById(expectedDeletedPublisherId)).thenReturn(Optional.of(expectedPublisherDeleted));

        publisherService.delete(expectedDeletedPublisherId);

        verify(publisherRepository, times(1)).deleteById(expectedDeletedPublisherId);
    }

    @Test
    void whenInvalidPublisherIdIsGivenThenAnExceptionShouldBeThrown() {
        Long expectedInvalidPublisherId = 2L;

        when(publisherRepository.findById(expectedInvalidPublisherId)).thenReturn(Optional.empty());

        try {
            publisherService.delete(expectedInvalidPublisherId);
            fail("Expected an PublisherNotFoundException to be thrown");
        } catch (PublisherNotFoundExcepton e) {
            assertThat(e.getMessage(), is(String.format("Publisher with id %s not exists!", expectedInvalidPublisherId)));
        }
    }

}
