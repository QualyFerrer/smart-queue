package com.smartqueue.smartqueue.domain.servicepoint.dto;

import com.smartqueue.smartqueue.domain.servicepoint.ServicePoint;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

public class ServicePointResponse {

    private UUID id;
    private UUID organizationId;
    private String organizationName;
    private String name;
    private Boolean active;
    private LocalDateTime createdAt;

    private ServicePointResponse() {
    }

    ;


    public static ServicePointResponse from(ServicePoint sp) {
        ServicePointResponse response = new ServicePointResponse();
        response.id = sp.getId();
        response.organizationId = sp.getOrganization().getId();
        response.organizationName = sp.getOrganization().getName();
        response.name = sp.getName();
        response.active = sp.isActive();
        response.createdAt = sp.getCreatedAt();

        return response;
    }

    public UUID getId() {
        return id;
    }

    public UUID getOrganizationId() {
        return organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getName() {
        return name;
    }

    public Boolean isActive() {
        return active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
