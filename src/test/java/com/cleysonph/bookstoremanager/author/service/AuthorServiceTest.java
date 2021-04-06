package com.cleysonph.bookstoremanager.author.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.cleysonph.bookstoremanager.author.builder.AuthorDTOBuilder;
import com.cleysonph.bookstoremanager.author.dto.AuthorDTO;
import com.cleysonph.bookstoremanager.author.entity.Author;
import com.cleysonph.bookstoremanager.author.exception.AuthorAlreadyExistsException;
import com.cleysonph.bookstoremanager.author.mapper.AuthorMapper;
import com.cleysonph.bookstoremanager.author.repository.AuthorRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    private final AuthorMapper authorMapper = AuthorMapper.INSTANCE;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private AuthorDTOBuilder authorDTOBuilder;

    @BeforeEach
    void setUp() {
        authorDTOBuilder = AuthorDTOBuilder.builder().build();
    }

    @Test
    void whenNewAuthorIsInformedThenIthShouldBeCreated() {
        // given
        AuthorDTO expectedAuthorToCreateDTO = authorDTOBuilder.builAuthorDTO();
        Author expectedCreatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);

        // when
        when(authorRepository.save(expectedCreatedAuthor)).thenReturn(expectedCreatedAuthor);
        when(authorRepository.findByName(expectedAuthorToCreateDTO.getName())).thenReturn(Optional.empty());

        AuthorDTO createdAuthorDTO = authorService.create(expectedAuthorToCreateDTO);

        // then
        assertThat(createdAuthorDTO, is(equalTo(expectedAuthorToCreateDTO)));
    }

    @Test
    void whenExistingAuthorIsInformedThenAnExceptionShouldBeThrown() {
        // given
        AuthorDTO expectedAuthorToCreateDTO = authorDTOBuilder.builAuthorDTO();
        Author expectedCreatedAuthor = authorMapper.toModel(expectedAuthorToCreateDTO);

        // when
        when(authorRepository.findByName(expectedAuthorToCreateDTO.getName())).thenReturn(Optional.of(expectedCreatedAuthor));

        // then
        assertThrows(AuthorAlreadyExistsException.class,
            () -> authorService.create(expectedAuthorToCreateDTO));
    }

}
