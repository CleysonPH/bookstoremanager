package com.cleysonph.bookstoremanager.user.service;

import java.util.Optional;

import com.cleysonph.bookstoremanager.user.dto.MessageDTO;
import com.cleysonph.bookstoremanager.user.dto.UserDTO;
import com.cleysonph.bookstoremanager.user.entity.User;
import com.cleysonph.bookstoremanager.user.exception.UserAlreadyExistsException;
import com.cleysonph.bookstoremanager.user.mapper.UserMapper;
import com.cleysonph.bookstoremanager.user.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final UserMapper USER_MAPPER = UserMapper.INSTANCE;

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MessageDTO create(UserDTO userToCreateDTO) {
        verifyIfExists(userToCreateDTO.getEmail(), userToCreateDTO.getUsername());

        User userToCreate = USER_MAPPER.toModel(userToCreateDTO);

        User createdUser = userRepository.save(userToCreate);

        return creationMessage(createdUser);
    }

    private void verifyIfExists(String email, String username) {
        Optional<User> foundUser = userRepository.findByEmailOrUsername(email, username);

        if (foundUser.isPresent()) {
            throw new UserAlreadyExistsException(email, username);
        }
    }

    private MessageDTO creationMessage(User createdUser) {
        String createdUsername = createdUser.getUsername();
        Long createdId = createdUser.getId();
        String createdUserMessage = String.format("User %s with ID %s successfully created", createdUsername, createdId);
        return MessageDTO.builder()
            .message(createdUserMessage)
            .build();
    }

}
