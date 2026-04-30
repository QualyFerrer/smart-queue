package com.smartqueue.smartqueue.domain.ticket.dto;

import com.smartqueue.smartqueue.domain.ticket.Ticket;
import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public class TicketRequest {

    @NotBlank(message = "ID da fila é obrigatório")
    private UUID queueId;

    private Ticket.TicketPriority priority = Ticket.TicketPriority.NORMAL;

    public TicketRequest() {
    }

    ;

    public UUID getQueueId() {
        return queueId;
    }

    public void setQueueId(UUID queueId) {
        this.queueId = queueId;
    }

    public Ticket.TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(Ticket.TicketPriority priority) {
        this.priority = priority;
    }
}
