package com.cleysonph.bookstoremanager.author.exception;

import javax.persistence.EntityNotFoundException;

public class AuthorNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public AuthorNotFoundException(Long id) {
        super(String.format("Author with id %s not exists", id));
    }

}
