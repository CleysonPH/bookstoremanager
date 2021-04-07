package com.cleysonph.bookstoremanager.publisher.builder;

import java.time.LocalDate;

import com.cleysonph.bookstoremanager.publisher.dto.PublisherDTO;

import lombok.Builder;

@Builder
public class PublisherDTOBuilder {

    @Builder.Default
    private final Long id = 1L;

    private final String name = "Cleyson Editora";

    private final String code = "CLE1234";

    private final LocalDate foundationDate = LocalDate.of(2021, 1, 1);

    public PublisherDTO buildPublisherDTO() {
        return new PublisherDTO(id, name, code, foundationDate);
    }

}
