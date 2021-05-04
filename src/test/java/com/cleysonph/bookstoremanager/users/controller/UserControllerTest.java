package com.cleysonph.bookstoremanager.users.controller;

import static com.cleysonph.bookstoremanager.author.utils.JsonConversionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cleysonph.bookstoremanager.user.controller.UserController;
import com.cleysonph.bookstoremanager.user.dto.MessageDTO;
import com.cleysonph.bookstoremanager.user.dto.UserDTO;
import com.cleysonph.bookstoremanager.user.service.UserService;
import com.cleysonph.bookstoremanager.users.builder.UserDTOBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private static final String USER_API_URL_PATH = "/api/v1/users";

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDTOBuilder userDTOBuilder;

    @BeforeEach
    void setUp() {
        userDTOBuilder = UserDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
            .build();
    }

    @Test
    void whenPOSTIsCalledThenCreatedStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToCreateDTO = userDTOBuilder.buildUserDTO();
        String expectedCreationMessage = "User cleysonph with ID 1 successfully created";
        MessageDTO expectedCreationMessageDTO = MessageDTO.builder().message(expectedCreationMessage).build();

        when(userService.create(expectedUserToCreateDTO)).thenReturn(expectedCreationMessageDTO);

        mockMvc.perform(post(USER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedUserToCreateDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.message", is(expectedCreationMessage)));
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldsThenBadRequestStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToCreateDTO = userDTOBuilder.buildUserDTO();
        expectedUserToCreateDTO.setUsername(null);

        mockMvc.perform(post(USER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedUserToCreateDTO)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenDELETEIsCalledThenNoContentStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToDeleteDTO = userDTOBuilder.buildUserDTO();
        Long expectedUserIdToDelete = expectedUserToDeleteDTO.getId();

        doNothing().when(userService).delete(expectedUserIdToDelete);

        mockMvc.perform(delete(USER_API_URL_PATH + "/" + expectedUserIdToDelete)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    void whenPUTIsCalledThenOkStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToUpdateDTO = userDTOBuilder.buildUserDTO();
        expectedUserToUpdateDTO.setUsername("cleysonph_updated");
        Long expectedUserIdToUpdate = expectedUserToUpdateDTO.getId();
        String expectedUpdateMessage = "User cleysonph_updated with ID 1 successfully updated";
        MessageDTO expectedUpdateMessageDTO = MessageDTO.builder().message(expectedUpdateMessage).build();

        when(userService.update(expectedUserIdToUpdate, expectedUserToUpdateDTO)).thenReturn(expectedUpdateMessageDTO);

        mockMvc.perform(put(USER_API_URL_PATH + "/" + expectedUserIdToUpdate)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedUserToUpdateDTO)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.message", is(expectedUpdateMessage)));
    }

    @Test
    void whenPUTIsCalledWithoutRequiredFieldsThenBadRequestStatusShouldBeReturned() throws Exception {
        UserDTO expectedUserToUpdateDTO = userDTOBuilder.buildUserDTO();
        expectedUserToUpdateDTO.setUsername(null);

        mockMvc.perform(put(USER_API_URL_PATH + "/" + expectedUserToUpdateDTO.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedUserToUpdateDTO)))
            .andExpect(status().isBadRequest());
    }

}
