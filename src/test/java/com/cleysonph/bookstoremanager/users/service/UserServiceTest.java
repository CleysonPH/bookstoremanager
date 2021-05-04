package com.cleysonph.bookstoremanager.users.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import com.cleysonph.bookstoremanager.user.dto.MessageDTO;
import com.cleysonph.bookstoremanager.user.dto.UserDTO;
import com.cleysonph.bookstoremanager.user.entity.User;
import com.cleysonph.bookstoremanager.user.exception.UserAlreadyExistsException;
import com.cleysonph.bookstoremanager.user.exception.UserNotFoundException;
import com.cleysonph.bookstoremanager.user.mapper.UserMapper;
import com.cleysonph.bookstoremanager.user.repository.UserRepository;
import com.cleysonph.bookstoremanager.user.service.UserService;
import com.cleysonph.bookstoremanager.users.builder.UserDTOBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private final UserMapper userMapper = UserMapper.INSTANCE;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp() {
        userDTOBuilder = UserDTOBuilder.builder().build();
    }

    @Test
    void whenNewUserIsInformedThenItShouldBeCreated() {
        UserDTO expectedCreatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedCreatedUser = userMapper.toModel(expectedCreatedUserDTO);
        String expectedCreationMessage = "User cleysonph with ID 1 successfully created";
        String expectedEmail = expectedCreatedUserDTO.getEmail();
        String expectedUsername = expectedCreatedUserDTO.getUsername();

        when(userRepository.findByEmailOrUsername(expectedEmail, expectedUsername)).thenReturn(Optional.empty());
        when(userRepository.save(expectedCreatedUser)).thenReturn(expectedCreatedUser);

        MessageDTO creationMessage = userService.create(expectedCreatedUserDTO);

        assertThat(expectedCreationMessage, is(equalTo(creationMessage.getMessage())));
    }

    @Test
    void whenExistingUserIsInformedThenAnExceptionShloudBeThrown() {
        UserDTO expectedCreatedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedCreatedUser = userMapper.toModel(expectedCreatedUserDTO);
        String expectedEmail = expectedCreatedUserDTO.getEmail();
        String expectedUsername = expectedCreatedUserDTO.getUsername();
        String expectedExceptionMessage = String.format(
            "User with email %s or username %s already exists", expectedEmail, expectedUsername);

        when(userRepository.findByEmailOrUsername(expectedEmail, expectedUsername))
            .thenReturn(Optional.of(expectedCreatedUser));

        try {
            userService.create(expectedCreatedUserDTO);
            fail("Expected an UserAlreadyExistsException to be thrown");
        } catch (UserAlreadyExistsException e) {
            assertThat(e.getMessage(), is(equalTo(expectedExceptionMessage)));
        }

    }

    @Test
    void whenValidUserIdIsInformedThenItShouldBeDeleted() {
        UserDTO expectedDeletedUserDTO = userDTOBuilder.buildUserDTO();
        User expectedDeletedUser = userMapper.toModel(expectedDeletedUserDTO);
        Long expectedDeletedUserId = expectedDeletedUserDTO.getId();

        when(userRepository.findById(expectedDeletedUserId)).thenReturn(Optional.of(expectedDeletedUser));
        doNothing().when(userRepository).deleteById(expectedDeletedUserId);

        userService.delete(expectedDeletedUserId);

        verify(userRepository, times(1)).deleteById(expectedDeletedUserId);
    }

    @Test
    void whenInvalidUserIdIsInformedThenAnExceptionShouldBeThrown() {
        UserDTO expectedDeletedUserDTO = userDTOBuilder.buildUserDTO();
        Long expectedDeletedUserId = expectedDeletedUserDTO.getId();
        String expectedExceptionMessage = String.format("User with ID %s not exists", expectedDeletedUserId);

        when(userRepository.findById(expectedDeletedUserId)).thenReturn(Optional.empty());

        try {
            userService.delete(expectedDeletedUserId);
            fail("Expected an UserNotFoundException to be thrown");
        } catch (UserNotFoundException e) {
            assertThat(e.getMessage(), is(equalTo(expectedExceptionMessage)));
        }
    }

    @Test
    void whenExistingUserIsInformedThenItShouldBeUpdated() {
        UserDTO expectedUpdatedUserDTO = userDTOBuilder.buildUserDTO();
        expectedUpdatedUserDTO.setUsername("cleysonph_update");
        User expectedUpdatedUser = userMapper.toModel(expectedUpdatedUserDTO);
        String expectedUpdatedMessage = "User cleysonph_update with ID 1 successfully updated";

        when(userRepository.findById(expectedUpdatedUserDTO.getId())).thenReturn(Optional.of(expectedUpdatedUser));
        when(userRepository.save(expectedUpdatedUser)).thenReturn(expectedUpdatedUser);

        MessageDTO successUpdatedMessage = userService.update(expectedUpdatedUserDTO.getId(), expectedUpdatedUserDTO);

        assertThat(successUpdatedMessage.getMessage(), is(equalTo(expectedUpdatedMessage)));
    }

    @Test
    void whenNotExistingUserIsInformedThenAnExceptionShouldBeThrown() {
        UserDTO expectedUpdatedUserDTO = userDTOBuilder.buildUserDTO();
        String expectedExceptionMessage = String.format("User with ID %s not exists", expectedUpdatedUserDTO.getId());

        when(userRepository.findById(expectedUpdatedUserDTO.getId())).thenReturn(Optional.empty());

        try {
            userService.update(expectedUpdatedUserDTO.getId(), expectedUpdatedUserDTO);
            fail("An UserNotFoundException should be thrown");
        } catch (UserNotFoundException e) {
            assertThat(e.getMessage(), is(equalTo(expectedExceptionMessage)));
        }
    }

}
