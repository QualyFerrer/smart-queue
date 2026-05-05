package com.smartqueue.smartqueue.domain.queue;

import com.smartqueue.smartqueue.domain.servicepoint.ServicePoint;
import com.smartqueue.smartqueue.domain.servicepoint.ServicePointService;
import com.smartqueue.smartqueue.domain.ticket.Ticket;
import com.smartqueue.smartqueue.domain.ticket.TicketRepository;
import com.smartqueue.smartqueue.exception.BusinessException;
import com.smartqueue.smartqueue.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.UUID;

@Service
public class QueueService {

    final QueueRepository queueRepository;
    final TicketRepository ticketRepository;
    final ServicePointService servicePointService;


    public QueueService(QueueRepository queueRepository, TicketRepository ticketRepository, ServicePointService servicePointRepository) {
        this.queueRepository = queueRepository;
        this.ticketRepository = ticketRepository;
        this.servicePointService = servicePointRepository;
    }

    @Transactional
    public Queue create(UUID servicePointId, String name, String prefix) {
        ServicePoint servicePoint = servicePointService.findById(servicePointId);

        if (!servicePoint.isActive()) {
            throw new BusinessException("Não é possível criar fila em  guichê inativo.");
        }

        Queue queue = new Queue(servicePoint, name, prefix);
        return queueRepository.save(queue);
    }

    @Transactional(readOnly = true)
    public Queue findById(UUID id) {
        return queueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Queue: ", id));
    }

    @Transactional
    public Queue open(UUID queueId) {
        Queue queue = findById(queueId);

        if (queue.getStatus() == Queue.QueueStatus.OPEN) {
            throw new BusinessException("Fila já está aberta.");
        }

        queue.setStatus(Queue.QueueStatus.OPEN);
        return queueRepository.save(queue);
    }

    @Transactional
    public Queue close(UUID queueId) {
        Queue queue = findById(queueId);

        if (queue.getStatus() == Queue.QueueStatus.CLOSED) {
            throw new BusinessException("Filá já está fechada.");
        }

        queue.setStatus(Queue.QueueStatus.CLOSED);
        return queueRepository.save(queue);
    }

    @Transactional
    public Ticket callNext(UUID queueId) {
        Queue queue = findById(queueId);

        if (queue.getStatus() != Queue.QueueStatus.OPEN) {
            throw new BusinessException("Não é possível chamar próximo, fila está: " + queue.getStatus());
        }

        Ticket next = ticketRepository.findNextTicket(queueId)
                .orElseThrow(() -> new BusinessException("Não há tickets aguardando na fila."));

        next.call();
        queue.setCurrentNumber(next.getTicketNumber());

        ticketRepository.save(next);
        queueRepository.save(queue);

        return next;
    }

    @Transactional(readOnly = true)
    public int estimateWaitTime(UUID queueId) {
        List<Ticket> completed = ticketRepository
                .findByQueueIdAndStatus(queueId, Ticket.TicketStatus.COMPLETED);

        if (completed.isEmpty()) {
            long waiting = ticketRepository
                    .countByQueueIdAndStatus(queueId, Ticket.TicketStatus.WAITING);
            return (int) waiting * 5;
        }

        double avgMinutes = completed.stream()
                .filter(t -> t.getServiceStartedAt() != null && t.getCompletedAt() != null)
                .mapToLong(t -> Duration.between(
                                t.getServiceStartedAt(),
                                t.getCompletedAt())
                        .toMinutes())
                .average()
                .orElse(5.0);

        long waiting = ticketRepository
                .countByQueueIdAndStatus(queueId, Ticket.TicketStatus.WAITING);

        return (int) Math.ceil(waiting * avgMinutes);
    }

}

