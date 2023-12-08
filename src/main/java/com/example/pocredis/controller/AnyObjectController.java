package com.example.pocredis.controller;

import com.example.pocredis.model.AnyObject;
import com.example.pocredis.service.AnyObjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/any-objects", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class AnyObjectController {
	
	private final AnyObjectService service;
	
	@GetMapping(value = "/", produces="application/json")
	public ResponseEntity<Page<AnyObject>> getAll(Pageable pageableRequest) {
		final Page anyObjectPage = service.findAll(pageableRequest);
		return ResponseEntity.ok(anyObjectPage);
	}
	
	@GetMapping(value = "/{id}", produces="application/json")
	public ResponseEntity<AnyObject> getById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PostMapping(value = "/{id}", produces="application/json")
	public ResponseEntity<AnyObject> create(String description) {
		AnyObject obj = service.create(description);
		return ResponseEntity.created(
				                     ServletUriComponentsBuilder.fromCurrentRequest()
				                                                .path("/{id}").buildAndExpand(obj).toUri())
		                     .build();
	}
	
	@PutMapping(value = "/{id}", produces="application/json")
	public ResponseEntity<AnyObject> update(@PathVariable Long id, String descriptionAtt) {
		return ResponseEntity.ok(service.update(id, descriptionAtt));
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.ok("Excluído com sucesso");
	}
	
}
