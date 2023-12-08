package com.example.pocredis.controller;

import com.example.pocredis.model.AnyObject;
import com.example.pocredis.service.AnyObjectService;
//import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(value = "/hello", produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
//@Tag(name = "Hello", description = "Hello world")
public class TestController {
	
	private final AnyObjectService service;
	
	@GetMapping(value = "/")
	public ResponseEntity<String> hello() {
		return ResponseEntity.ok("Hello");
	}
	
}
