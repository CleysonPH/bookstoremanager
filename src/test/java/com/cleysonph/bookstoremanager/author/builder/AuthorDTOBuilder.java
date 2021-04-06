package com.cleysonph.bookstoremanager.author.builder;

import com.cleysonph.bookstoremanager.author.dto.AuthorDTO;

import lombok.Builder;

@Builder
public class AuthorDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    @Builder.Default
    private final String name = "Cleyson Lima";

    @Builder.Default
    private final Integer age = 24;

    public AuthorDTO builAuthorDTO() {
        return new AuthorDTO(id, name, age);
    }

}
