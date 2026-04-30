package com.smartqueue.smartqueue.domain.organization;

import com.smartqueue.smartqueue.exception.ConflictedException;
import com.smartqueue.smartqueue.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class OrganizationService {

    private final OrganizationRepository repository;

    public OrganizationService(OrganizationRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Organization create(String name, String cnpj) {
        repository.findByCnpj(cnpj).ifPresent(existing -> {
            throw new ConflictedException("Organização já cadastrada com o cnpj " + cnpj);
        });

        Organization organization = new Organization(name, cnpj);
        return repository.save(organization);
    }


    @Transactional(readOnly = true)
    public Organization findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Organização com o id " + id + " não foi encontrada"));
    }

    @Transactional(readOnly = true)
    public List<Organization> findAll() {
        return repository.findByActive(true);
    }

    @Transactional
    public Organization update(UUID id, String name) {
        Organization organization = findById(id);
        organization.setName(name);
        return repository.save(organization);
    }

    public Organization deactivate(UUID id) {
        Organization organization = findById(id);
        organization.setActive(false);
        return repository.save(organization);
    }

}
