package com.cleysonph.bookstoremanager.publisher.mapper;

import com.cleysonph.bookstoremanager.publisher.dto.PublisherDTO;
import com.cleysonph.bookstoremanager.publisher.entity.Publisher;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class PublisherMapper {

    public static final PublisherMapper INSTANCE = Mappers.getMapper(PublisherMapper.class);

    public abstract Publisher toModel(PublisherDTO publisherDTO);

    public abstract PublisherDTO toDTO(Publisher publisher);

}
