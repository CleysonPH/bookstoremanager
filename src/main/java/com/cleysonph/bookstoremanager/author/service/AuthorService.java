package com.cleysonph.bookstoremanager.author.service;

import com.cleysonph.bookstoremanager.author.dto.AuthorDTO;
import com.cleysonph.bookstoremanager.author.entity.Author;
import com.cleysonph.bookstoremanager.author.exception.AuthorAlreadyExistsException;
import com.cleysonph.bookstoremanager.author.exception.AuthorNotFoundException;
import com.cleysonph.bookstoremanager.author.mapper.AuthorMapper;
import com.cleysonph.bookstoremanager.author.repository.AuthorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final static AuthorMapper AUTHOR_MAPPER = AuthorMapper.INSTANCE;

    private AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public AuthorDTO create(AuthorDTO authorDTO) {
        verifiIfExists(authorDTO.getName());

        Author authorToCreate = AUTHOR_MAPPER.toModel(authorDTO);

        Author createdAuthor = authorRepository.save(authorToCreate);

        return AUTHOR_MAPPER.toDTO(createdAuthor);
    }

    public AuthorDTO findById(Long id) {
        Author foundAuthor = authorRepository.findById(id)
            .orElseThrow(() -> new AuthorNotFoundException(id));

        return AUTHOR_MAPPER.toDTO(foundAuthor);
    }

    private void verifiIfExists(String authorName) {
        authorRepository.findByName(authorName)
            .ifPresent(author -> {
                throw new AuthorAlreadyExistsException(authorName);
            });
    }

}
