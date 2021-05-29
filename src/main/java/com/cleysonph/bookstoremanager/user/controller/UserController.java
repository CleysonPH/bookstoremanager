package com.cleysonph.bookstoremanager.user.controller;

import javax.validation.Valid;

import com.cleysonph.bookstoremanager.user.dto.JwtRequest;
import com.cleysonph.bookstoremanager.user.dto.JwtResponse;
import com.cleysonph.bookstoremanager.user.dto.MessageDTO;
import com.cleysonph.bookstoremanager.user.dto.UserDTO;
import com.cleysonph.bookstoremanager.user.service.AuthenticationService;
import com.cleysonph.bookstoremanager.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController implements UserControllerDocs {

    private UserService userService;

    private AuthenticationService authenticationService;

    @Autowired
    public UserController(UserService userService, AuthenticationService authenticationService) {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public MessageDTO create(@RequestBody @Valid UserDTO userToCreateDTO) {
        return userService.create(userToCreateDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

    @PutMapping("/{id}")
    public MessageDTO update(@PathVariable Long id, @RequestBody @Valid UserDTO userToUpdateDTO) {
        return userService.update(id, userToUpdateDTO);
    }

    @PostMapping("/authenticate")
    public JwtResponse createAuthenticationToken(@RequestBody @Valid JwtRequest jwtRequest) {
        return authenticationService.createAuthenticationToken(jwtRequest);
    }

}
