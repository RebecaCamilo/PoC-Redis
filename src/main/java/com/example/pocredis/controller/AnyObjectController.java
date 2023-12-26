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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static com.example.pocredis.Path.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping(value = PATH_ANY_OBJECT, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Any Object", description = "Management of Any Objects")
public class AnyObjectController {

    private final AnyObjectService service;

    @GetMapping(produces = "application/json")
    public ResponseEntity<Page<AnyObject>> getAll(Pageable pageableRequest) {
        final Page anyObjectPage = service.findAll(pageableRequest);
        return ResponseEntity.ok(anyObjectPage);
    }

    @GetMapping(value = PATH_ID, produces = "application/json")
    public ResponseEntity<AnyObject> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(value = PATH_RESTRICT + PATH_ID, produces = "application/json")
    public ResponseEntity<AnyObject> getByIdRestrict(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByIdRestrict(id));
    }

    @PostMapping(produces = "application/json")
    public ResponseEntity<AnyObject> create(@Valid @RequestBody AnyObjectRequest objRequest) {

        AnyObject obj = service.create(new AnyObject(objRequest.getDescription(), objRequest.getQuantity()));

        final URI location = fromCurrentRequest()
                                                .path("/{id}")
                                                .buildAndExpand(obj.getId())
                                                .toUri();

        return ResponseEntity.created(location).body(obj);
    }

    @PutMapping(value = PATH_ID, produces = "application/json")
    public ResponseEntity<AnyObject> update(@PathVariable Long id, @Valid @RequestBody AnyObjectRequest objRequest) {
        return ResponseEntity.ok(service.update(id,
                                                new AnyObject(objRequest.getDescription(), objRequest.getQuantity())));
    }

    @DeleteMapping(value = PATH_ID)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok("Successfully deleted");
    }

}
