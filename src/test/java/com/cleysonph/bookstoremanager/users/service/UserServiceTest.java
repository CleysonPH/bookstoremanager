package com.cleysonph.bookstoremanager.users.service;

import com.cleysonph.bookstoremanager.user.mapper.UserMapper;
import com.cleysonph.bookstoremanager.user.repository.UserRepository;
import com.cleysonph.bookstoremanager.user.service.UserService;
import com.cleysonph.bookstoremanager.users.builder.UserDTOBuilder;

import org.junit.jupiter.api.BeforeEach;
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

}
