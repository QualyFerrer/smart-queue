package com.smartqueue.smartqueue.domain.queue.dto;

import com.smartqueue.smartqueue.domain.queue.Queue;

import java.time.LocalDateTime;
import java.util.UUID;

public class QueueResponse {

    public UUID id;
    public UUID servicePointId;
    public String servicePointName;
    private String name;
    private String prefix;
    private String status;
    private int currentNumber;
    private LocalDateTime createdAt;

    private QueueResponse(){};

    public static QueueResponse from(Queue queue){
        QueueResponse response = new QueueResponse();
        response.id = queue.getId();
        response.servicePointId = queue.getServicePoint().getId();
        response.servicePointName = queue.getServicePoint().getName();
        response.name = queue.getName();
        response.prefix = queue.getPrefix();
        response.status = queue.getStatus().name();
        response.currentNumber = queue.getCurrentNumber();
        response.createdAt = queue.getCreatedAt();

        return response;

    }

    public UUID getId() {
        return id;
    }

    public UUID getServicePointId() {
        return servicePointId;
    }

    public String getServicePointName() {
        return servicePointName;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getStatus() {
        return status;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
