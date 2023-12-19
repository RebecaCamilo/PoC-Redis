package com.example.pocredis;

import com.example.pocredis.config.HashKey;
import com.example.pocredis.config.RedisCacheConfig;
import com.example.pocredis.controller.AnyObjectController;
import com.example.pocredis.repository.AnyObjectRepository;
import com.example.pocredis.service.AnyObjectService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PocRedisApplicationTests {

	@Autowired
	private HashKey hashKey;

	@Autowired
	private RedisCacheConfig redisCacheConfig;

	@Autowired
	private AnyObjectController controller;

	@Autowired
	private AnyObjectService service;

	@Autowired
	private AnyObjectRepository repository;


	@Test
	void should_have_HashKey_bean_into_context() {
		Assertions.assertThat(hashKey).isNotNull();
	}

	@Test
	void should_have_RedisCacheConfig_bean_into_context() {
		Assertions.assertThat(redisCacheConfig).isNotNull();
	}

	@Test
	void should_have_AnyObjectController_bean_into_context() {
		Assertions.assertThat(controller).isNotNull();
	}

	@Test
	void should_have_AnyObjectService_bean_into_context() {
		Assertions.assertThat(service).isNotNull();
	}

	@Test
	void should_have_AnyObjectRepository_bean_into_context() {
		Assertions.assertThat(repository).isNotNull();
	}

}
