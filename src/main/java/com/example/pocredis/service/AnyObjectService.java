package com.example.pocredis.service;

import com.example.pocredis.model.AnyObject;
import com.example.pocredis.repository.AnyObjectRepository;
import lombok.RequiredArgsConstructor;
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
	
	public AnyObject findById(Long id) {
		return repository.findById(id).orElseThrow();
	}
	
	public AnyObject create(final String description) {
		return repository.save(new AnyObject(description));
	}
	
	public AnyObject update(Long id, String description) {
		findById(id);
		return repository.save(new AnyObject(id, description));
	}
	
	public void delete(Long id) {
		findById(id);
		repository.deleteById(id);
	}
	
}
