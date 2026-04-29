package com.smartqueue.smartqueue.domain.queue.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class QueueRequest {

    @NotNull(message = "ID do guichê é obrigatório")
    public UUID servicePointId;

    @NotBlank(message = "Nome é obrigatrório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caractéres")
    private String name;


    @Size(max = 5, message = "Prefixo deve conter no máximo 5 caractéres")
    private String prefix;

    public UUID getServicePointId() {
        return servicePointId;
    }

    public void setServicePointId(UUID servicePointId) {
        this.servicePointId = servicePointId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
