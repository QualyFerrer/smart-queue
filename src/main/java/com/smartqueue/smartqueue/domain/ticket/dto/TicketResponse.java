package com.smartqueue.smartqueue.domain.ticket.dto;

import com.smartqueue.smartqueue.domain.ticket.Ticket;

import java.time.LocalDateTime;
import java.util.UUID;

public class TicketResponse {

    private UUID id;
    private UUID queueId;
    private String queueName;
    private int ticketNumber;
    private String displayNumber;
    private String priority;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime calledAt;
    private LocalDateTime completedAt;


    private TicketResponse() {
    }

    ;

    public static TicketResponse from(Ticket ticket) {
        TicketResponse response = new TicketResponse();
        response.id = ticket.getId();
        response.queueId = ticket.getQueue().getId();
        response.queueName = ticket.getQueue().getName();
        response.ticketNumber = ticket.getTicketNumber();


        String prefix = ticket.getQueue().getPrefix();
        response.displayNumber = (prefix != null ? prefix : " ")
                + String.format("%03d", ticket.getTicketNumber());


        response.priority = ticket.getPriority().name();
        response.status = ticket.getStatus().name();
        response.createdAt = ticket.getCreatedAt();
        response.calledAt = ticket.getCalledAt();
        response.completedAt = ticket.getCompletedAt();
        return response;
    }

    public UUID getId() {
        return id;
    }

    public UUID getQueueId() {
        return queueId;
    }

    public String getQueueName() {
        return queueName;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public String getDisplayNumber() {
        return displayNumber;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getCalledAt() {
        return calledAt;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
}
