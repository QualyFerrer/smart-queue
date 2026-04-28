package domain.ticket;

import domain.queue.Queue;
import jakarta.persistence.*;


import java.security.InvalidKeyException;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tickets")
public class Ticket {

    public enum TicketPriority {
        NORMAL, PRIORITY
    }

    public enum TicketStatus {
        WAITTING, CALLED, IN_SERVICE, COMPLETED, CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "queue_id", nullable = false)
    private Queue queue;

    @Column(name = "ticket_number", nullable = false)
    private int ticketNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketPriority priority = TicketPriority.NORMAL;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TicketStatus status = TicketStatus.WAITTING;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "called_at", nullable = false)
    private LocalDateTime calledAt;

    @Column(name = "service_started_at")
    private LocalDateTime serviceStartedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Ticket() {
    }

    public Ticket(Queue queue, int ticketNumber, TicketPriority priority) {
        this.queue = queue;
        this.ticketNumber = ticketNumber;
        this.priority = priority;
    }

    public void call() {
        if (this.status != Ticket.TicketStatus.WAITTING) {
            throw new IllegalStateException(
                    "Ticket não pode ser chamado pois está com status " + this.status
            );
        }
        this.status = TicketStatus.CALLED;
        this.calledAt = LocalDateTime.now();
    }

    public void startService() {
        if (this.status != TicketStatus.CALLED) {
            throw new IllegalStateException("Atendimento só pode iniciar após o ticket ser chamado");
        }

        this.status = TicketStatus.IN_SERVICE;
        this.serviceStartedAt = LocalDateTime.now();

    }

    public void complete() {
        if (this.status != TicketStatus.IN_SERVICE) {
            throw new IllegalStateException("Ticket só pode ser completado se estiver em atendimento");
        }

        this.status = TicketStatus.COMPLETED;
        this.completedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (this.status == TicketStatus.COMPLETED) {
            throw new IllegalStateException("Ticket já finalizado não pode ser cancelado");
        }
        this.status = TicketStatus.CANCELLED;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public LocalDateTime getServiceStartedAt() {
        return serviceStartedAt;
    }

    public LocalDateTime getCalledAt() {
        return calledAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public Queue getQueue() {
        return queue;
    }

    public UUID getId() {
        return id;
    }
}