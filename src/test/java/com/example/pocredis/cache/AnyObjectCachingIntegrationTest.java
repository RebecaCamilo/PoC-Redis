package com.example.pocredis.cache;

import com.example.pocredis.config.RedisCacheConfig;
import com.example.pocredis.model.AnyObject;
import com.example.pocredis.repository.AnyObjectRepository;
import com.example.pocredis.service.AnyObjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.example.pocredis.model.AnyObjectFactoryTest.createValidAnyObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@Import({ RedisCacheConfig.class, AnyObjectService.class })
@ExtendWith(SpringExtension.class)
@EnableCaching
@ImportAutoConfiguration(classes = {
        CacheAutoConfiguration.class,
        RedisAutoConfiguration.class
})
public class AnyObjectCachingIntegrationTest {

    @MockBean
    private AnyObjectRepository repository;

    @Autowired
    private AnyObjectService service;

    @Autowired
    private CacheManager cacheManager;

    @Test
    @DisplayName("When findByIdRestrict save AnyObject in cache")
    void when_findByIdRestrict_save_AnyObject_in_cache() {
        AnyObject obj = createValidAnyObject();

        when(repository.findById(obj.getId())).thenReturn(Optional.of(obj));

        service.findByIdRestrict(obj.getId());
        service.findByIdRestrict(obj.getId());

        verify(repository, times(1)).findById(obj.getId());
    }

    @Test
    @DisplayName("When FindByIdRestrict then AnyObject returned from cache")
    void when_FindByIdRestrict_then_AnyObject_returned_from_cache() {
        AnyObject obj = createValidAnyObject();
        var id = obj.getId();
        var description = obj.getDescription();
        var quant = obj.getQuantity();

        when(repository.findById(obj.getId())).thenReturn(Optional.of(obj));

        AnyObject itemCacheMiss = service.findById(obj.getId());
        AnyObject itemCacheHit = service.findById(obj.getId());

        System.out.println(itemCacheHit.getDescription());

        assertThat(itemCacheMiss.getId()).isEqualTo(id);
        assertThat(itemCacheMiss.getDescription()).isEqualTo(description);
        assertThat(itemCacheMiss.getQuantity()).isEqualTo(quant);

        assertThat(itemCacheHit.getId()).isEqualTo(id);
        assertThat(itemCacheHit.getDescription()).isEqualTo(description);
        assertThat(itemCacheHit.getQuantity()).isEqualTo(quant);

        System.out.println(cacheManager.getCache("anyObjects" + obj.getId().toString()));
        verify(repository, times(1)).findById(obj.getId());
    }

}
