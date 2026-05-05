package com.smartqueue.smartqueue.domain.ticket;

import com.smartqueue.smartqueue.domain.queue.Queue;
import com.smartqueue.smartqueue.domain.queue.QueueRepository;
import com.smartqueue.smartqueue.exception.BusinessException;
import com.smartqueue.smartqueue.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final QueueRepository queueRepository;

    public TicketService(TicketRepository ticketRepository, QueueRepository queueRepository) {
        this.ticketRepository = ticketRepository;
        this.queueRepository = queueRepository;
    }

    @Transactional
    public Ticket ticketIssue(UUID queueId, Ticket.TicketPriority priority) {
        Queue queue = queueRepository.findById(queueId)
                .orElseThrow(() -> new ResourceNotFoundException("Queue", queueId));


        if (queue.getStatus() != Queue.QueueStatus.OPEN) {
            throw new BusinessException(
                    "Não é possível emitir ticket. A fila já está com status: " + queue.getStatus()
            );
        }

        int nextNumber = ticketRepository.findMaxTicketNumber(queueId) + 1;
        Ticket ticket = new Ticket(queue, nextNumber, priority);
        return ticketRepository.save(ticket);
    }

    @Transactional(readOnly = true)
    public Ticket findById(UUID id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket ", id));
    }

    @Transactional(readOnly = true)
    public int getPosition(UUID ticketId) {
        Ticket ticket = findById(ticketId);

        if (ticket.getStatus() != Ticket.TicketStatus.WAITING) {
            throw new BusinessException("Ticket não está na fila. Status atual: " + ticket.getStatus());
        }

        UUID queueId = ticket.getQueue().getId();

        List<Ticket> waitingTickets = ticketRepository
                .findByQueueIdAndStatus(queueId, Ticket.TicketStatus.WAITING);

        long ahead = waitingTickets.stream()
                .filter(t -> {
                    if (ticket.getPriority() == Ticket.TicketPriority.NORMAL
                            && t.getPriority() == Ticket.TicketPriority.PRIORITY) {
                        return true;
                    }
                    if (t.getPriority() == ticket.getPriority()) {
                        return t.getTicketNumber() < ticket.getTicketNumber();
                    }
                    return false;
                })
                .count();

        return (int) ahead + 1;
    }

    @Transactional
    public Ticket cancel(UUID ticketId){
        Ticket ticket = findById(ticketId);
        ticket.cancel();

        return ticketRepository.save(ticket);
    }

    public List<Ticket> findByWaitingQueue(UUID queueId){
        return ticketRepository.findByQueueIdAndStatus(queueId, Ticket.TicketStatus.WAITING);
    }
}
