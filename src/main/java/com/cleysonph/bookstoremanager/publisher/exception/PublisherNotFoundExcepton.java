package com.cleysonph.bookstoremanager.publisher.exception;

import javax.persistence.EntityNotFoundException;

public class PublisherNotFoundExcepton extends EntityNotFoundException {

    public PublisherNotFoundExcepton(Long id) {
        super(String.format("Publisher with id %s not exists!", id));
    }

}
