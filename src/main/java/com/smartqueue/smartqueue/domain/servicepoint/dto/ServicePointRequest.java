package com.smartqueue.smartqueue.domain.servicepoint.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class ServicePointRequest {

    @NotNull(message = "ID da organização é obrigatório")
    private UUID id;


    @NotBlank
    @Size(max = 100, message = "Nome deve ter no máximo 100 caractéres")
    private String name;

    public ServicePointRequest() {
    }

    ;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
