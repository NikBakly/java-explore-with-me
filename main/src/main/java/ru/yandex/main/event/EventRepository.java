package ru.yandex.main.event;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>, QuerydslPredicateExecutor<Event> {

    Page<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(Long eventId, Long initiatorId);

    @Query("select e from Event e " +
            "where e.id = :event_id and e.state = 'PUBLISHED' ")
    Optional<Event> findEventByIdAndStateIsPublished(@Param("event_id") Long eventId);

    @Query("select e from Event e " +
            "where e.id in(:ids)")
    List<Event> findAllByEventIds(@Param("ids") List<Long> eventIds);
}
