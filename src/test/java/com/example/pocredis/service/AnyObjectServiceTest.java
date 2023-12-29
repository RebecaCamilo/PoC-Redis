package com.example.pocredis.service;

import com.example.pocredis.exception.AnyObjectAlreadyExistsException;
import com.example.pocredis.exception.AnyObjectNotFoundException;
import com.example.pocredis.model.AnyObject;
import com.example.pocredis.repository.AnyObjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.example.pocredis.model.AnyObjectFactoryTest.createValidAnyObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AnyObjectServiceTest {

    private final Long ID = 1L;

    private final AnyObjectService service;
    private final AnyObjectRepository repository;

    AnyObjectServiceTest() {
        this.repository = mock(AnyObjectRepository.class);
        this.service = new AnyObjectService(this.repository);
    }

    @Test
    @DisplayName("Verify findAll interaction with repository")
    void verify_findAll_interaction_with_repository() {
        // When
        when(repository.findAll(any(Pageable.class))).thenReturn(any(Page.class));
        this.service.findAll(Pageable.unpaged());

        // Then
        verify(repository, times(1)).findAll(Pageable.unpaged());
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
    @DisplayName("Verify findById interaction with repository")
    void verify_findById_interaction_with_repository() {
        // Given
        var obj = createValidAnyObject();

        // When
        when(repository.findById(ID)).thenReturn(Optional.of(obj));
        this.service.findById(ID);

        // Then
        verify(repository, times(1)).findById(ID);
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
        // When
        when(repository.findById(ID)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> service.findById(ID))
                .isExactlyInstanceOf(AnyObjectNotFoundException.class);
    }

    @Test
    @DisplayName("Verify findByIdRestrict interaction with repository")
    void verify_findByIdRestrict_interaction_with_repository() {
        // Given
        var obj = createValidAnyObject();

        // When
        when(repository.findById(ID)).thenReturn(Optional.of(obj));
        this.service.findByIdRestrict(ID);

        // Then
        verify(repository, times(1)).findById(ID);
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
        // When
        when(repository.findById(ID)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> service.findByIdRestrict(ID))
                .isExactlyInstanceOf(AnyObjectNotFoundException.class);
    }

    @Test
    @DisplayName("Verify create interaction with repository")
    void verify_create_interaction_with_repository() {
        // Given
        var obj = createValidAnyObject();

        // When
        when(repository.existsByDescription(obj.getDescription())).thenReturn(false);
        when(repository.save(any(AnyObject.class))).thenReturn(obj);
        this.service.create(obj);

        // Then
        verify(repository, times(1)).existsByDescription(obj.getDescription());
        verify(repository, times(1)).save(any(AnyObject.class));
    }

    @Test
    @DisplayName("Should create return same object as repository")
    void should_create_return_same_object_as_repository() {
        // Given
        var obj = createValidAnyObject();

        // When
        when(repository.existsByDescription(obj.getDescription())).thenReturn(false);
        when(repository.save(any(AnyObject.class))).thenReturn(obj);
        final var result = this.service.create(obj);

        // Then
        assertThat(result).isEqualTo(Optional.of(obj).get());
    }

    @Test
    @DisplayName("Should create throw exception when AnyObject already exists")
    void should_create_throw_exception_when_AnyObject_already_exists() {
        // Given
        var obj = createValidAnyObject();

        // When
        when(repository.existsByDescriptionNotId(obj.getDescription(), obj.getId())).thenReturn(true);

        // Then
        try {
            service.create(obj);
        } catch (AnyObjectAlreadyExistsException e) {
            assertThat(e).isExactlyInstanceOf(AnyObjectAlreadyExistsException.class);
            assertThat(e.getMessage())
                    .isEqualTo("Object with description %s already exists.", obj.getDescription());
        }
    }

    @Test
    @DisplayName("Verify update interaction with repository")
    void verify_update_interaction_with_repository() {
        // Given
        var obj = createValidAnyObject();

        // When
        when(repository.findById(obj.getId())).thenReturn(Optional.of(obj));
        when(repository.existsByDescriptionNotId(obj.getDescription(), obj.getId())).thenReturn(false);
        when(repository.save(any(AnyObject.class))).thenReturn(obj);
        this.service.update(ID, obj);

        // Then
        verify(repository, times(1)).findById(obj.getId());
        verify(repository, times(1)).existsByDescriptionNotId(obj.getDescription(), obj.getId());
        verify(repository, times(1)).save(any(AnyObject.class));
    }

    @Test
    @DisplayName("Should update return same object as repository")
    void should_update_return_same_object_as_repository() {
        // Given
        var obj = createValidAnyObject();

        // When
        when(repository.findById(obj.getId())).thenReturn(Optional.of(obj));
        when(repository.existsByDescriptionNotId(obj.getDescription(), obj.getId())).thenReturn(false);
        when(repository.save(any(AnyObject.class))).thenReturn(obj);
        final var result = this.service.update(ID, obj);

        // Then
        assertThat(result).isEqualTo(Optional.of(obj).get());
    }

    @Test
    @DisplayName("Should update throw exception when AnyObject not exists by id")
    void should_update_throw_exception_when_AnyObject_not_exists_by_id() {
        // Given
        var obj = createValidAnyObject();

        // When
        when(repository.findById(ID)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> service.update(ID, obj))
                .isExactlyInstanceOf(AnyObjectNotFoundException.class);
    }

    @Test
    @DisplayName("Should update throw exception when AnyObject already exists")
    void should_update_throw_exception_when_AnyObject_already_exists() {
        // Given
        var obj = createValidAnyObject();

        // When
        when(repository.findById(obj.getId())).thenReturn(Optional.of(obj));
        when(repository.existsByDescriptionNotId(obj.getDescription(), obj.getId())).thenReturn(true);

        // Then
        try {
            service.update(ID, obj);
        } catch (AnyObjectAlreadyExistsException e) {
            assertThat(e).isExactlyInstanceOf(AnyObjectAlreadyExistsException.class);
            assertThat(e.getMessage())
                    .isEqualTo("Object with description %s already exists.", obj.getDescription());
        }
    }

    @Test
    @DisplayName("Verify delete interaction with repository")
    void verify_delete_interaction_with_repository() {
        // Given
        var obj = createValidAnyObject();

        // When
        when(repository.findById(obj.getId())).thenReturn(Optional.of(obj));
        doNothing().when(repository).deleteById(obj.getId());
        this.service.delete(obj.getId());

        // Then
        verify(repository, times(1)).findById(obj.getId());
        verify(repository, times(1)).deleteById(obj.getId());
    }

    @Test
    @DisplayName("Should delete throw exception when AnyObject not exists by id")
    void should_delete_throw_exception_when_AnyObject_not_exists_by_id() {
        // Given
        var obj = createValidAnyObject();

        // When
        when(repository.findById(ID)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> service.delete(obj.getId()))
                .isExactlyInstanceOf(AnyObjectNotFoundException.class);
    }

}
