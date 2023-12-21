package com.example.pocredis.controller;

import com.example.pocredis.controller.request.AnyObjectRequest;
import com.example.pocredis.model.AnyObject;
import com.example.pocredis.service.AnyObjectService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

import static com.example.pocredis.Path.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = PATH_ANY_OBJECT, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Any Object", description = "Management of Any Objects")
public class AnyObjectController {
	
	private final AnyObjectService service;
	
	@GetMapping(produces="application/json")
	public ResponseEntity<Page<AnyObject>> getAll(Pageable pageableRequest) {
		final Page anyObjectPage = service.findAll(pageableRequest);
		return ResponseEntity.ok(anyObjectPage);
	}
	
	@GetMapping(value = PATH_ID, produces="application/json")
	public ResponseEntity<AnyObject> getById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}

	@GetMapping(value = PATH_RESTRICT + PATH_ID, produces="application/json")
	public ResponseEntity<AnyObject> getByIdRestrict(@PathVariable Long id) {
		return ResponseEntity.ok(service.findByIdRestrict(id));
	}
	
	@PostMapping(value = PATH_ID, produces="application/json")
	public ResponseEntity<AnyObject> create(@Valid AnyObjectRequest objRequest) {
		AnyObject obj = service.create(objRequest.getDescription());
		return ResponseEntity.created(
				                     ServletUriComponentsBuilder.fromCurrentRequest()
				                                                .path("/{id}").buildAndExpand(obj).toUri())
		                     .build();
	}
	
	@PutMapping(value = PATH_ID, produces="application/json")
	public ResponseEntity<AnyObject> update(@PathVariable Long id, @Valid AnyObjectRequest objRequest) {
		return ResponseEntity.ok(service.update(id, objRequest.getDescription()));
	}
	
	@DeleteMapping(value = PATH_ID)
	public ResponseEntity<?> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.ok("Excluído com sucesso");
	}
	
}
