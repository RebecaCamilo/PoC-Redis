package com.example.pocredis.service;

import com.example.pocredis.exception.AnyObjectNotFoundException;
import com.example.pocredis.model.AnyObject;
import com.example.pocredis.repository.AnyObjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.example.pocredis.model.AnyObjectFactoryTest.createValidAnyObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AnyObjectServiceTest {

    private final AnyObjectService service;
    private final AnyObjectRepository repository;

    AnyObjectServiceTest() {
        this.repository = mock(AnyObjectRepository.class);
        this.service = new AnyObjectService(this.repository);
    }

    @Test
    @DisplayName("Should findAll return Page of AnyObjects")
    void should_findAll_return_Page_of_AnyObjects() {
        // Given
        var obj = createValidAnyObject();
        final Page<AnyObject> page = new PageImpl<>(List.of(obj));

        // When
        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        final var result = this.service.findAll(Pageable.unpaged());

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isNotEmpty().contains(obj);
    }

    @Test
    @DisplayName("Should findAll return empty Page of AnyObjects")
    void should_findAll_return_empty_Page_of_AnyObject() {
        // Given
        final Page<AnyObject> page = new PageImpl<>(List.of());

        // When
        when(repository.findAll(any(Pageable.class))).thenReturn(page);
        final var result = this.service.findAll(Pageable.unpaged());

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEmpty();
    }

    @Test
    @DisplayName("Verify interaction with repository")
    void verify_interaction_with_repository() {
        // When
        when(repository.findAll(any(Pageable.class))).thenReturn(any(Page.class));
        final var result = this.service.findAll(Pageable.unpaged());

        // Then
        verify(repository, times(1)).findAll(Pageable.unpaged());
    }

    @Test
    @DisplayName("Should findById return same object inside repositorys optinal")
    void should_findById_return_same_object_inside_repositorys_optinal() {
        // Given
        var obj = createValidAnyObject();
        var id = obj.getId();

        // When
        when(repository.findById(id)).thenReturn(Optional.of(obj));
        final var result = this.service.findById(id);

        // Then
        assertThat(result).isEqualTo(Optional.of(obj).get());
    }

    @Test
    @DisplayName("Should throw exception when AnyObject NotFound")
    void should_findById_throw_exception_when_AnyObject_NotFound() {
        // Given
        var id = 1L;

        // When
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> service.findById(id))
                .isExactlyInstanceOf(AnyObjectNotFoundException.class);
    }

    @Test
    @DisplayName("Should findByIdRestrict return same object inside repositorys optinal")
    void should_findByIdRestrict_return_same_object_inside_repositorys_optinal() {
        // Given
        var obj = createValidAnyObject();
        var id = obj.getId();

        // When
        when(repository.findById(id)).thenReturn(Optional.of(obj));
        final var result = this.service.findByIdRestrict(id);

        // Then
        assertThat(result).isEqualTo(Optional.of(obj).get());
    }

    @Test
    @DisplayName("Should findByIdRestrict throw exception when AnyObject NotFound")
    void should_findByIdRestrict_throw_exception_when_AnyObject_NotFound() {
        // Given
        var id = 1L;

        // When
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> service.findByIdRestrict(id))
                .isExactlyInstanceOf(AnyObjectNotFoundException.class);
    }


}