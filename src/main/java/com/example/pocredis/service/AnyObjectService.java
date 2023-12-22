package com.example.pocredis.service;

import com.example.pocredis.exception.AnyObjectNotFoundException;
import com.example.pocredis.model.AnyObject;
import com.example.pocredis.repository.AnyObjectRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnyObjectService {

	private final AnyObjectRepository repository;

	public Page<AnyObject> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	@Cacheable(value = "anyObjects", key = "#id")
	public AnyObject findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new AnyObjectNotFoundException(id));
	}

	@Cacheable(value = "anyObjects", keyGenerator = "hashKey")
	public AnyObject findByIdRestrict(Long id) {
		return repository.findById(id).orElseThrow(() -> new AnyObjectNotFoundException(id));
	}
	
	public AnyObject create(final String description, final int quantity) {
		return repository.save(new AnyObject(description, quantity));
	}

	@CachePut(value = "anyObjects", key = "#id")
	public AnyObject update(Long id, String description, int quantity) {
		findById(id);
		return repository.save(new AnyObject(id, description, quantity));
	}

	@CacheEvict(value = "anyObjects", key = "#id")
	public void delete(Long id) {
		findById(id);
		repository.deleteById(id);
	}
	
}
