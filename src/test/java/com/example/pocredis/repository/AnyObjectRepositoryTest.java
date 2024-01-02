package com.example.pocredis.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.example.pocredis.model.AnyObjectFactoryTest.createValidAnyObject;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AnyObjectRepositoryTest {

    final String MESA = "mesa";
    final Long ID = 2L;
    final private AnyObjectRepository repository;

    AnyObjectRepositoryTest(@Autowired AnyObjectRepository repository) {
        this.repository = repository;
    }

    @Test
    @DisplayName("injected components are NotNull")
    void injected_components_are_NotNull() {
        assertThat(repository).isNotNull();
    }

    @Test
    @DisplayName("Should return false if not exists AnyObject by description")
    void should_return_false_if_not_exists_AnyObject_by_description() {
        // When
        var result = repository.existsByDescription(MESA);

        // Then
        assertThat(result).isFalse();
        assertThat(result).isExactlyInstanceOf(Boolean.class);
    }

    @Test
    @DisplayName("Should return true if already exists AnyObject by description")
    void should_return_true_if_already_exists_AnyObject_by_description() {
        // Given
        var obj = createValidAnyObject();
        repository.save(obj);

        // When
        var result = repository.existsByDescription(obj.getDescription());

        // Then
        assertThat(result).isTrue();
        assertThat(result).isExactlyInstanceOf(Boolean.class);
    }

    @Test
    @DisplayName("Should return true if not exists AnyObject by description and id")
    void should_return_true_if_not_exists_AnyObject_by_description_and_id() {
        // When
        var result = repository.existsByDescriptionNotId(MESA, ID);

        // Then
        assertThat(result).isFalse();
        assertThat(result).isExactlyInstanceOf(Boolean.class);
    }

    @Test
    @DisplayName("Should return true if already exists AnyObject by description and id")
    void should_return_true_if_already_exists_AnyObject_by_description_and_id() {
        // Given
        var obj = createValidAnyObject();
        repository.save(obj);

        // When
        var result = repository.existsByDescriptionNotId(obj.getDescription(), obj.getId());

        // Then
        assertThat(result).isFalse();
        assertThat(result).isExactlyInstanceOf(Boolean.class);
    }

    @Test
    @DisplayName("Should return true if already exists AnyObject by description and not id")
    void should_return_true_if_already_exists_AnyObject_by_description_and_not_id() {
        // Given
        var obj = createValidAnyObject();
        repository.save(obj);

        // When
        var result = repository.existsByDescriptionNotId(obj.getDescription(), ID);

        // Then
        assertThat(result).isTrue();
        assertThat(result).isExactlyInstanceOf(Boolean.class);
    }
}
