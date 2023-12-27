package com.example.pocredis.controller;

import com.example.pocredis.controller.request.AnyObjectRequest;
import com.example.pocredis.exception.AnyObjectAlreadyExistsException;
import com.example.pocredis.exception.AnyObjectNotFoundException;
import com.example.pocredis.model.AnyObject;
import com.example.pocredis.service.AnyObjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static com.example.pocredis.model.AnyObjectRequestFactoryTest.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
    void shouldReturn200WhenGetAllIsSuccess() throws Exception {
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
    void shouldReturn200WhenGetByIdIsSuccess() throws Exception {
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
                .andExpect(jsonPath("$.description").value(obj.getDescription()))
                .andExpect(jsonPath("$.quantity").value(obj.getQuantity()));

        verify(this.service, times(1)).findById(obj.getId());
    }

    @Test
    @DisplayName("Should return 404 when getById is Not Found")
    void shouldReturn404WhenGetByIdIsNotFound() throws Exception {
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
    void shouldReturn200WhenGetByIdRestrictIsSuccess() throws Exception {
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
                .andExpect(jsonPath("$.description").value(obj.getDescription()))
                .andExpect(jsonPath("$.quantity").value(obj.getQuantity()));

        verify(this.service, times(1)).findByIdRestrict(obj.getId());
    }

    @Test
    @DisplayName("Should return 404 when getById Restrict is Not Found")
    void shouldReturn404WhenGetByIdRestrictIsNotFound() throws Exception {
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
    void shouldReturn201WhenCreateIsSuccess() throws Exception {
        // Given
        AnyObjectRequest objReq = createValidAnyObjectRequest();
        AnyObject obj = createValidAnyObject();

        // When
        when(service.create(any(AnyObject.class))).thenReturn(obj);
        final var expectedBodyJson = this.objectMapper.writeValueAsString(obj);

        // Then
        this.mockMvc.perform(post(PATH_ANY_OBJECT)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(objReq)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("**/" + PATH_ANY_OBJECT + "/" + obj.getId()))
                .andExpect(content().json(expectedBodyJson))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(obj.getId()))
                .andExpect(jsonPath("$.description").value(obj.getDescription()))
                .andExpect(jsonPath("$.quantity").value(obj.getQuantity()));

        verify(this.service, times(1)).create(any(AnyObject.class));
    }

    @Test
    @DisplayName("Should return 400 when create is BadRequest NullDescription")
    void shouldReturn400WhenCreateIsBadRequestNullDescription() throws Exception {
        // Given
        AnyObjectRequest objReq = createInvalidAnyObjectNullDescription();

        // Then
        this.mockMvc.perform(post(PATH_ANY_OBJECT)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(objReq)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers
                        .containsString("Description is mandatory.")));
    }

    @Test
    @DisplayName("Should return 400 when create is BadRequest Max Characters Exceeded")
    void shouldReturn400WhenCreateIsBadRequestMaxCharactersExceeded() throws Exception {
        // Given
        AnyObjectRequest objReq = createInvalidAnyObjectMaxCharacters();

        // Then
        this.mockMvc.perform(post(PATH_ANY_OBJECT)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(objReq)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers
                        .containsString("Maximum number of characters must be 50")));
    }

    @Test
    @DisplayName("Should return 409 when create is Conflict")
    void shouldReturn409WhenCreateIsConflict() throws Exception {
        // Given
        AnyObjectRequest objReq = createValidAnyObjectRequest();
        AnyObject obj = createValidAnyObject();

        // When
        when(service.create(any(AnyObject.class)))
                .thenThrow(createAnyObjectAlreadyExistsException(obj.getDescription()));

        // Then
        this.mockMvc.perform(post(PATH_ANY_OBJECT)
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(objReq)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict())
                .andExpect(content().string(org.hamcrest.Matchers
                        .containsString("Object with description " + obj.getDescription() + " already exists.")));

        verify(this.service, times(1)).create(any(AnyObject.class));
    }

    @Test
    @DisplayName("Should return 201 when update is Success")
    void shouldReturn201WhenUpdateIsSuccess() throws Exception {
        // Given
        AnyObjectRequest objReq = createValidAnyObjectRequest();
        AnyObject obj = createValidAnyObject();

        // When
        when(service.update(any(Long.class), any(AnyObject.class))).thenReturn(obj);

        // Then
        this.mockMvc.perform(put(PATH_ANY_OBJECT + PATH_ID, obj.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(objReq)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.id").value(obj.getId()))
                .andExpect(jsonPath("$.description").value(obj.getDescription()))
                .andExpect(jsonPath("$.quantity").value(obj.getQuantity()));

        verify(this.service, times(1)).update(any(Long.class), any(AnyObject.class));
    }

    @Test
    @DisplayName("Should return 400 when update is BadRequest NullDescription")
    void shouldReturn400WhenUpdateIsBadRequestNullDescription() throws Exception {
        // Given
        AnyObjectRequest objReq = createInvalidAnyObjectNullDescription();
        AnyObject obj = createValidAnyObject();

        // Then
        this.mockMvc.perform(put(PATH_ANY_OBJECT + PATH_ID, obj.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(objReq)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers
                        .containsString("Description is mandatory.")));
    }

    @Test
    @DisplayName("Should return 400 when update is BadRequest Max Characters Exceeded")
    void shouldReturn400WhenUpdateIsBadRequestMaxCharactersExceeded() throws Exception {
        // Given
        AnyObjectRequest objReq = createInvalidAnyObjectMaxCharacters();
        AnyObject obj = createValidAnyObject();

        // Then
        this.mockMvc.perform(put(PATH_ANY_OBJECT + PATH_ID, obj.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(objReq)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(org.hamcrest.Matchers
                        .containsString("Maximum number of characters must be 50")));
    }

    @Test
    @DisplayName("Should return 409 when update is Conflict")
    void shouldReturn409WhenUpdateIsConflict() throws Exception {
        // Given
        AnyObjectRequest objReq = createValidAnyObjectRequest();
        AnyObject obj = createValidAnyObject();

        // When
        when(service.update(any(Long.class), any(AnyObject.class)))
                .thenThrow(createAnyObjectAlreadyExistsException(obj.getDescription()));

        // Then
        this.mockMvc.perform(put(PATH_ANY_OBJECT + PATH_ID, obj.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(objReq)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isConflict())
                .andExpect(content().string(org.hamcrest.Matchers
                        .containsString("Object with description " + obj.getDescription() + " already exists.")));

        verify(this.service, times(1)).update(any(Long.class), any(AnyObject.class));
    }

    @Test
    @DisplayName("Should return 200 when delete is Success")
    void shouldReturn200WhenDeleteIsSuccess() throws Exception {
        // Given
        AnyObject obj = createValidAnyObject();

        // When
        doNothing().when(service).delete(obj.getId());

        // Then
        this.mockMvc.perform(delete(PATH_ANY_OBJECT + PATH_ID, obj.getId())
                        .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers
                        .containsString("Successfully deleted.")));

        verify(this.service, times(1)).delete(obj.getId());
    }

    @Test
    @DisplayName("Should return 404 when delete is Not Found")
    void shouldReturn404WhenDeleteIsNotFound() throws Exception {
        // Given
        AnyObject obj = createValidAnyObject();

        // When
        doThrow(createObjectNotFoundException(obj.getId())).when(service).delete(obj.getId());

        // Then
        this.mockMvc.perform(delete(PATH_ANY_OBJECT + PATH_ID, obj.getId())
                        .contentType(APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(org.hamcrest.Matchers
                        .containsString("Object with id " + obj.getId().toString() + " not found.")));

        verify(this.service, times(1)).delete(obj.getId());
    }

    private static AnyObjectNotFoundException createObjectNotFoundException(Long id) {
        return new AnyObjectNotFoundException(id);
    }

    private static AnyObjectAlreadyExistsException createAnyObjectAlreadyExistsException(String description) {
        return new AnyObjectAlreadyExistsException(description);
    }

}
