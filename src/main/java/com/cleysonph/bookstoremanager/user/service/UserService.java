package com.cleysonph.bookstoremanager.user.service;

import java.util.Optional;

import com.cleysonph.bookstoremanager.user.dto.MessageDTO;
import com.cleysonph.bookstoremanager.user.dto.UserDTO;
import com.cleysonph.bookstoremanager.user.entity.User;
import com.cleysonph.bookstoremanager.user.exception.UserAlreadyExistsException;
import com.cleysonph.bookstoremanager.user.exception.UserNotFoundException;
import com.cleysonph.bookstoremanager.user.mapper.UserMapper;
import com.cleysonph.bookstoremanager.user.repository.UserRepository;
import com.cleysonph.bookstoremanager.user.utils.MessageDTOUtils;

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

        return MessageDTOUtils.creationMessage(createdUser);
    }

    public MessageDTO update(Long id, UserDTO userToUpdateDTO) {
        User foundUser = verifyAndGetIfExists(id);
        userToUpdateDTO.setId(foundUser.getId());

        User userToUpdate = USER_MAPPER.toModel(userToUpdateDTO);
        userToUpdate.setCreatedDate(foundUser.getCreatedDate());

        User updatedUser = userRepository.save(userToUpdate);
        return MessageDTOUtils.updatedMessage(updatedUser);
    }

    public void delete(Long id) {
        verifyAndGetIfExists(id);

        userRepository.deleteById(id);
    }

    private User verifyAndGetIfExists(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    private void verifyIfExists(String email, String username) {
        Optional<User> foundUser = userRepository.findByEmailOrUsername(email, username);

        if (foundUser.isPresent()) {
            throw new UserAlreadyExistsException(email, username);
        }
    }

}
