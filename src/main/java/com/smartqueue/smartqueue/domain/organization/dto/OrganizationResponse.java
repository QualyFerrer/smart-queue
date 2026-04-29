package com.smartqueue.smartqueue.domain.organization.dto;

import com.smartqueue.smartqueue.domain.organization.Organization;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrganizationResponse {

    private UUID id;
    private String name;
    private String cnpj;
    private Boolean active;
    private LocalDateTime createdAt;

    private OrganizationResponse() {
    }

    ;

    public static OrganizationResponse from(Organization org) {
        OrganizationResponse response = new OrganizationResponse();
        response.id         = org.getId();
        response.name       = org.getName();
        response.cnpj       = org.getCnpj();
        response.active     = org.getActive();
        response.createdAt  = org.getCreatedAt();
        return response;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCnpj() {
        return cnpj;
    }

    public Boolean getActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
