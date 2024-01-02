package com.example.pocredis.repository;

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
    void injected_components_are_NotNull() {
        assertThat(repository).isNotNull();
    }

    @Test
    void should_return_false_if_not_exists_AnyObject_by_description() {
        // When
        var result = repository.existsByDescription(MESA);

        // Then
        assertThat(result).isFalse();
        assertThat(result).isExactlyInstanceOf(Boolean.class);
    }

    @Test
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
}
