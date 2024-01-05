package com.example.pocredis.cache;

import com.example.pocredis.config.RedisCacheConfig;
import com.example.pocredis.model.AnyObject;
import com.example.pocredis.repository.AnyObjectRepository;
import com.example.pocredis.service.AnyObjectService;
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
import static org.mockito.Mockito.*;

@Import({ RedisCacheConfig.class, AnyObjectService.class})
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
    void verify_when_findById_save_AnyObject_in_cache() {
        AnyObject obj = createValidAnyObject();

        when(repository.findById(obj.getId())).thenReturn(Optional.of(obj));

        service.findById(obj.getId());
        service.findById(obj.getId());

        verify(repository, times(1)).findById(obj.getId());
    }

    @Test
    void verify_when_findByIdRestrict_save_AnyObject_in_cache() {
        AnyObject obj = createValidAnyObject();

        when(repository.findById(obj.getId())).thenReturn(Optional.of(obj));

        service.findByIdRestrict(obj.getId());
        service.findByIdRestrict(obj.getId());

        verify(repository, times(1)).findById(obj.getId());
    }

}
