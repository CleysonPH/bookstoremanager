package com.cleysonph.bookstoremanager.publisher.controller;

import static com.cleysonph.bookstoremanager.author.utils.JsonConversionUtils.asJsonString;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import com.cleysonph.bookstoremanager.publisher.builder.PublisherDTOBuilder;
import com.cleysonph.bookstoremanager.publisher.dto.PublisherDTO;
import com.cleysonph.bookstoremanager.publisher.service.PublisherService;

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
public class PublisherControllerTest {

    private static final String PUBLISHER_API_URL_PATH = "/api/v1/publishers";

    private MockMvc mockMvc;

    @Mock
    private PublisherService publisherService;

    @InjectMocks
    private PublisherController publisherController;

    private PublisherDTOBuilder publisherDTOBuilder;

    @BeforeEach
    void setUp() {
        publisherDTOBuilder = PublisherDTOBuilder.builder().build();
        mockMvc = MockMvcBuilders.standaloneSetup(publisherController)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
            .build();
    }

    @Test
    void whenPostIsCalledThenCreatedStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

        when(publisherService.create(expectedCreatedPublisherDTO)).thenReturn(expectedCreatedPublisherDTO);

        mockMvc.perform(post(PUBLISHER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedCreatedPublisherDTO)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id", is(expectedCreatedPublisherDTO.getId().intValue())))
            .andExpect(jsonPath("$.name", is(expectedCreatedPublisherDTO.getName())))
            .andExpect(jsonPath("$.code", is(expectedCreatedPublisherDTO.getCode())));
    }

    @Test
    void whenPostIsCalledWithouRequiredFieldsThenBadRequestStatusShouldBeInformed() throws Exception {
        PublisherDTO expectedCreatedPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
        expectedCreatedPublisherDTO.setName(null);

        mockMvc.perform(post(PUBLISHER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedCreatedPublisherDTO)))
            .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETWithValidIdIsCalledThenStatusOkShouldBeThrown() throws Exception {
        PublisherDTO expectedFoundPublisherDTO = publisherDTOBuilder.buildPublisherDTO();
        Long expectedFoundPublisherDTOId = expectedFoundPublisherDTO.getId();

        when(publisherService.findById(expectedFoundPublisherDTOId)).thenReturn(expectedFoundPublisherDTO);

        mockMvc.perform(get(PUBLISHER_API_URL_PATH + "/" + expectedFoundPublisherDTOId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(expectedFoundPublisherDTOId.intValue())))
            .andExpect(jsonPath("$.name", is(expectedFoundPublisherDTO.getName())))
            .andExpect(jsonPath("$.code", is(expectedFoundPublisherDTO.getCode())));
    }

    @Test
    void whenGETListIsCalledThenStatusOkShouldBeInformed() throws Exception {
        PublisherDTO expectedFoundPublisherDTO = publisherDTOBuilder.buildPublisherDTO();

        when(publisherService.findAll()).thenReturn(Collections.singletonList(expectedFoundPublisherDTO));

        mockMvc.perform(get(PUBLISHER_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(expectedFoundPublisherDTO.getId().intValue())))
            .andExpect(jsonPath("$[0].name", is(expectedFoundPublisherDTO.getName())))
            .andExpect(jsonPath("$[0].code", is(expectedFoundPublisherDTO.getCode())));
    }

    @Test
    void whenDELETEIsCalledThenStatusNoContentShouldBeInformed() throws Exception {
        PublisherDTO expectedPublisherToDeleteDTO = publisherDTOBuilder.buildPublisherDTO();
        Long expectedPublisherToDeletedId = expectedPublisherToDeleteDTO.getId();

        doNothing().when(publisherService).delete(expectedPublisherToDeletedId);

        mockMvc.perform(delete(PUBLISHER_API_URL_PATH + "/" + expectedPublisherToDeletedId)
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

}
