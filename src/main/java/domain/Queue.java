package domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Table(name = "queues")
@Entity
public class Queue {

    public enum QueueStatus {
        OPEN, PAUSED, CLOSED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_point_id", nullable = false)
    private ServicePoint servicePoint;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private QueueStatus status = QueueStatus.CLOSED;

    @Column(name = "current_number", nullable = false)
    private int currentNumber = 0;

    @Column(length = 5)
    private String prefix;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdated() {
        updatedAt = LocalDateTime.now();
    }

    public Queue() {
    }


    public Queue(ServicePoint servicePoint, String name, String prefix) {
        this.servicePoint = servicePoint;
        this.name = name;
        this.prefix = prefix;
    }

    public UUID getId() {
        return id;
    }

    public ServicePoint getServicePoint() {
        return servicePoint;
    }

    public void setServicePoint(ServicePoint servicePoint) {
        this.servicePoint = servicePoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public QueueStatus getStatus() {
        return status;
    }

    public void setStatus(QueueStatus status) {
        this.status = status;
    }

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
