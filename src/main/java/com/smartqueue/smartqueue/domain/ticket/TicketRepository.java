package com.smartqueue.smartqueue.domain.ticket;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.UUID;

public interface TicketRepository extends JpaRepository<Ticket, UUID> {

    List<Ticket> findByQueueIdAndStatus(Queue queueId, Ticket.TicketStatus status);

    // Próximo ticket a ser chamado:
    // Prioridade primeiro (PRIORITY antes de NORMAL), depois por número
    // ORDER BY CASE: SQL condicional que transforma enum em número pra ordenação
    @Query("""
        SELECT t FROM Ticket t
        WHERE t.queue.id = :queueId
        AND t.status = 'WAITING'
        ORDER BY
            CASE t.priority WHEN 'PRIORITY' THEN 0 ELSE 1 END ASC,
            t.ticketNumber ASC
        LIMIT 1
        """)
    Optional<Ticket> findNextTicket(@Param("queueId") UUID queueId);

    long countByQueueIdAndStatus(UUID queueId, Ticket.TicketStatus status);

    // Buscar o maior número de ticket numa fila (pra gerar o próximo)
    @Query("SELECT COALESCE(MAX(t.ticketNumber), 0) FROM Ticket t WHERE t.queue.id = :queueId")
    int findMaxTicketNumber(@Param("queueId") UUID queueId);
}
