package com.example.pocredis.controller;

import com.example.pocredis.exception.AnyObjectNotFoundException;
import com.example.pocredis.model.AnyObject;
import com.example.pocredis.service.AnyObjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static com.example.pocredis.Path.*;
import static com.example.pocredis.model.AnyObjectFactoryTest.createValidAnyObject;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AnyObjectController.class)
class AnyObjectControllerTest {

    @MockBean
    private AnyObjectService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Should return 200 when getAll is Success")
    void shouldReturn200whenGetAllIsSuccess() throws Exception {
        // When
        when(service.findAll(any(Pageable.class))).thenReturn(any(Page.class));

        // Then
        this.mockMvc.perform(get(PATH_ANY_OBJECT))
                .andDo(print())
                .andExpect(status().isOk());

        verify(this.service, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Should return 200 when getById is Success")
    void shouldReturn200whenGetByIdIsSuccess() throws Exception {
        // Given
        AnyObject obj = createValidAnyObject();

        // When
        when(service.findById(obj.getId())).thenReturn(obj);

        // Then
        this.mockMvc.perform(get(PATH_ANY_OBJECT + PATH_ID, obj.getId())
                        .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(obj.getId()))
                .andExpect(jsonPath("$.description").value(obj.getDescription()));

        verify(this.service, times(1)).findById(obj.getId());
    }

    @Test
    @DisplayName("Should return 404 when getById is Not Found")
    void shouldReturn404whenGetByIdIsNotFound() throws Exception {
        // Given
        AnyObject obj = createValidAnyObject();

        // When
        when(service.findById(obj.getId())).thenThrow(createObjectNotFoundException(obj.getId()));

        // Then
        this.mockMvc.perform(get(PATH_ANY_OBJECT + PATH_ID, obj.getId())
                        .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(org.hamcrest.Matchers
                        .containsString("Object with id " + obj.getId().toString() + " not found.")));

        verify(this.service, times(1)).findById(obj.getId());
    }

    @Test
    @DisplayName("Should return 200 when getById Restrict is Success")
    void shouldReturn200whenGetByIdRestrictIsSuccess() throws Exception {
        // Given
        AnyObject obj = createValidAnyObject();

        // When
        when(service.findByIdRestrict(obj.getId())).thenReturn(obj);

        // Then
        this.mockMvc.perform(get(PATH_ANY_OBJECT + PATH_RESTRICT + PATH_ID, obj.getId())
                        .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(obj.getId()))
                .andExpect(jsonPath("$.description").value(obj.getDescription()));

        verify(this.service, times(1)).findByIdRestrict(obj.getId());
    }

    @Test
    @DisplayName("Should return 404 when getById Restrict is Not Found")
    void shouldReturn404whenGetByIdRestrictIsNotFound() throws Exception {
        // Given
        AnyObject obj = createValidAnyObject();

        // When
        when(service.findByIdRestrict(obj.getId())).thenThrow(createObjectNotFoundException(obj.getId()));

        // Then
        this.mockMvc.perform(get(PATH_ANY_OBJECT + PATH_RESTRICT + PATH_ID, obj.getId())
                        .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(org.hamcrest.Matchers
                        .containsString("Object with id " + obj.getId().toString() + " not found.")));

        verify(this.service, times(1)).findByIdRestrict(obj.getId());
    }

    @Test
    @DisplayName("Should return 201 when create is Success")
    void shouldReturn201whenCreateIsSuccess() throws Exception {
        // Given
        AnyObject obj = createValidAnyObject();

        // When
        when(service.findById(obj.getId())).thenReturn(obj);

        // Then
        this.mockMvc.perform(get(PATH_ANY_OBJECT + PATH_ID, obj.getId())
                        .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(obj.getId()))
                .andExpect(jsonPath("$.description").value(obj.getDescription()));

        verify(this.service, times(1)).findById(obj.getId());
    }

    @Test
    @DisplayName("Should return 300X when create is Success")
    void shouldReturn300XwhenCreateIsSuccess() throws Exception {
        // Given
        AnyObject obj = createValidAnyObject();

        // When
        when(service.findById(obj.getId())).thenReturn(obj);

        // Then
        this.mockMvc.perform(get(PATH_ANY_OBJECT + PATH_ID, obj.getId())
                        .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(obj.getId()))
                .andExpect(jsonPath("$.description").value(obj.getDescription()));

        verify(this.service, times(1)).findById(obj.getId());
    }

    private static AnyObjectNotFoundException createObjectNotFoundException(Long id) {
        return new AnyObjectNotFoundException(id);
    }



}