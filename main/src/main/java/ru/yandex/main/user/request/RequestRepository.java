package ru.yandex.main.user.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RequestRepository extends JpaRepository<Request, Long> {

    @Query("select count(r) " +
            "from Request r " +
            "where r.status = 'CONFIRMED' and r.event.id = :event_id")
    Long countRequestByEventIdWhenStatusIsConfirmed(@Param("event_id") Long eventId);

    @Query("select r.id " +
            "from Request r " +
            "where r.status = 'PENDING' and r.event.id = :event_id")
    List<Long> findAllByEventIdWhenStatusIsPending(@Param("event_id") Long eventId);

    List<Request> findRequestByRequesterId(Long requesterId);

    Optional<Request> findRequestByRequesterIdAndEventId(Long requesterId, Long eventId);

    List<Request> findAllByEventId(Long eventId);
}
