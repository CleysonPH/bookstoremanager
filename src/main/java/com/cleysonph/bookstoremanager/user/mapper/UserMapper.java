package com.cleysonph.bookstoremanager.user.mapper;

import com.cleysonph.bookstoremanager.user.dto.UserDTO;
import com.cleysonph.bookstoremanager.user.entity.User;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public static final UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    public abstract User toModel(UserDTO userDTO);

    public abstract UserDTO toDto(User user);

}
