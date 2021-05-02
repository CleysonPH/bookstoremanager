package com.cleysonph.bookstoremanager.user.service;

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

}
