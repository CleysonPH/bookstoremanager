package com.cleysonph.bookstoremanager.author.service;

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

}
