package com.smartqueue.smartqueue.domain.queue;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface QueueRepository extends JpaRepository<Queue, UUID> {

    List<Queue> findByServicePointId(UUID ServicePointId);
    @Query("SELECT q FROM Queue q WHERE q.servicePoint.id = :servicePointId AND q.status = :status")
    List<Queue> findByServicePointIdAndStatus(
            @Param("servicePointId") UUID servicePointId,
            @Param("status") Queue.QueueStatus status);
}
