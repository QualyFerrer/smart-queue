package com.smartqueue.smartqueue.domain.organization;

import com.smartqueue.smartqueue.domain.organization.dto.OrganizationRequest;
import com.smartqueue.smartqueue.domain.organization.dto.OrganizationResponse;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping
    public ResponseEntity<OrganizationResponse> create(@RequestBody @Valid OrganizationRequest request) {
        Organization created = organizationService.create(request.getName(), request.getCnpj());
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(OrganizationResponse.from(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(OrganizationResponse.from(organizationService.findById(id)));
    }

    @GetMapping
    public ResponseEntity<List<OrganizationResponse>> findAll() {
        List<OrganizationResponse> response = organizationService.findAll()
                .stream()
                .map(OrganizationResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<OrganizationResponse> update(
            @PathVariable UUID id, @RequestBody @Valid OrganizationResponse request) {
        Organization updated = organizationService.update(id, request.getName());
        return ResponseEntity.ok(OrganizationResponse.from(updated));
    }

    public ResponseEntity<Void> deactivate(@PathVariable UUID id){
        organizationService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}
